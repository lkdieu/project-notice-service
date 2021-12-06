package com.example.noticemanagementservice.service.impl;

import com.example.noticemanagementservice.dto.request.NoticeRequest;
import com.example.noticemanagementservice.dto.NoticeDto;
import com.example.noticemanagementservice.entity.AttachedFile;
import com.example.noticemanagementservice.entity.Notice;
import com.example.noticemanagementservice.entity.User;
import com.example.noticemanagementservice.exception.NoticeServiceException;
import com.example.noticemanagementservice.exception.ResourceNotFoundException;
import com.example.noticemanagementservice.exception.ServiceException;
import com.example.noticemanagementservice.repository.AttachedFileRepository;
import com.example.noticemanagementservice.repository.NoticeRepository;
import com.example.noticemanagementservice.repository.UserRepository;
import com.example.noticemanagementservice.service.NoticeManageService;
import com.example.noticemanagementservice.util.DateTimeUtil;
import com.example.noticemanagementservice.util.MessageConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * NoticeServiceImpl
 *
 * @author FPT Software
 */
@Service
@Slf4j
public class NoticeManageServiceImpl implements NoticeManageService {

    private final Path root = Paths.get(MessageConstants.FOLDER);

    @Autowired
    private CustomAccountServiceImpl customAccountServiceImpl;

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AttachedFileRepository attachedFileRepository;

    @Override
    public Notice createNotice(NoticeRequest noticeRequest) throws ServiceException, IOException {
        //Get current username information
        String userName = customAccountServiceImpl.getUserName();
        //Format date
        Date startDate = DateTimeUtil.convertStringToDate(noticeRequest.getStartDate(), MessageConstants.DATE_FORMAT);
        Date endDate = DateTimeUtil.convertStringToDate(noticeRequest.getEndDate(), MessageConstants.DATE_FORMAT);
        // find user
        User user = userRepository.findByUserName(userName).orElse(null);
        log.info("Start findByUserName, result {}", user);
        //Set uop data for Noctice
        Notice notice = new Notice();
        notice.setContent(noticeRequest.getContent());
        notice.setTitle(noticeRequest.getTitle());
        notice.setIsEnable(true);
        notice.setEndDate(endDate);
        notice.setStartDate(startDate);
        notice.setViewNumber(0);
        notice.setUser(user);
        //save file
        List<AttachedFile> attachedFileList = handleMultipleFiles(noticeRequest.getMultipartFile());
        notice.setAttachFiles(attachedFileList);
        return noticeRepository.save(notice);

    }

    /**
     * Get All Notice
     *
     * @param offset size
     * @param limit  page
     * @return Page<Notice>
     */
    @Override
    @Cacheable("notice")
    public Page<Notice> getAllNotice(int offset, int limit) {
        Date date = new Date();
        Pageable pageable = PageRequest.of(offset, limit);
        //Find All By Start Date Less Than Equal And End Date Greater Than Equal And Is Enable Is True
        Page<Notice> noticeDtoPage = noticeRepository.findAllByEndDateGreaterThanEqualAndIsEnableIsTrue(date, pageable);
        log.info("Start findAllWithEnd result {}", noticeDtoPage);
        return noticeDtoPage;
    }


    @Override
    @Cacheable(cacheNames = "notice", key = "#id")
    public NoticeDto getDetails(Long id) {
        //Find All By Start Date Less Than Equal And Is Enable Is True
        Notice notice = noticeRepository.findByIdAndEndDateGreaterThanEqualAndIsEnableTrue(id, new Date())
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.INVALID_NOTICE_ID + id));
        //View plus 1 and save
        notice.setViewNumber(notice.getViewNumber() + 1);
        noticeRepository.saveAndFlush(notice);
        log.info("Start saveAndFlush result: {}", notice);
        NoticeDto noticeDto = new NoticeDto();
        BeanUtils.copyProperties(Objects.requireNonNull(notice), noticeDto);
        noticeDto.setUser(notice.getUser().getUserName());
        List<AttachedFile> image = new ArrayList<>();
        if (notice.getAttachFiles() != null) {
            image.addAll(notice.getAttachFiles());
        }
        noticeDto.setImage(image);

        return noticeDto;
    }


    @Override
    @CacheEvict(cacheNames = "notice", key = "#id")
    public void deleteNotice(Long id) throws NoticeServiceException {
        Notice notice = noticeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(MessageConstants.INVALID_NOTICE_ID + id));
        validateUser(notice.getUser().getUserName());
        notice.setIsEnable(false);
        noticeRepository.save(notice);
    }


    @Override
    @CachePut(cacheNames = "notice", key = "#id")
    public Notice updateNotice(NoticeRequest noticeRequest, Long id) throws ServiceException, IOException {
        //find by notice id
        Notice notice = noticeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(MessageConstants.INVALID_NOTICE_ID + id));
        // validate user name
        validateUser(notice.getUser().getUserName());
        //Set up
        BeanUtils.copyProperties(noticeRequest, notice);
        if (noticeRequest.getMultipartFile() != null) {
            List<AttachedFile> attachedFileList = handleMultipleFiles(noticeRequest.getMultipartFile());
            notice.setAttachFiles(attachedFileList);
        }
        return noticeRepository.saveAndFlush(notice);

    }

    @Override
    public Page<Notice> getNoticesByUser(int offset, int limit, String userName) throws NoticeServiceException {
        Pageable pageable = PageRequest.of(offset, limit);
        //validate User
        validateUser(userName);
        //find user name
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new ResourceNotFoundException(MessageConstants.INVALID_USERNAME + userName));
        log.info("Start findByUserName result: {}", user);
        return noticeRepository.findByUserId(user.getId(), pageable);
    }

    private void validateUser(String userName) throws NoticeServiceException {
        String name = customAccountServiceImpl.getUserName();
        log.info("Start validateUser result: {}", name);
        if (!name.equals(userName)) {
            throw new NoticeServiceException(HttpStatus.FORBIDDEN.name(), HttpStatus.FORBIDDEN.getReasonPhrase());
        }
    }

    private List<AttachedFile> handleMultipleFiles(MultipartFile[] files) throws ServiceException, IOException {
        // check Paths If it doesn't exist, create a new one
        if (!Files.exists(root)) {
            Files.createDirectory(root);
        }
        List<AttachedFile> attachedFileList = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                Files.copy(file.getInputStream(), this.root.resolve(Objects.requireNonNull(file.getOriginalFilename())));
            } catch (IOException e) {
                log.error("run save file {}", e.getMessage());
                throw new ServiceException(HttpStatus.INTERNAL_SERVER_ERROR.name(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            }
            // get all name files
            AttachedFile attachedFile = new AttachedFile();
            Path pathFile = root.resolve(Objects.requireNonNull(file.getOriginalFilename()));
            Resource resource = new UrlResource(pathFile.toUri());
            attachedFile.setName(resource.getURL().getFile());
            attachedFileList.add(attachedFile);

        }
        return attachedFileList;
    }
}

