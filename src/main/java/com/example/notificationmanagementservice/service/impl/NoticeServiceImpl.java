package com.example.notificationmanagementservice.service.impl;

import com.example.notificationmanagementservice.dto.request.NoticeRequest;
import com.example.notificationmanagementservice.dto.NoticeDto;
import com.example.notificationmanagementservice.entity.AttachedFile;
import com.example.notificationmanagementservice.entity.Notice;
import com.example.notificationmanagementservice.entity.User;
import com.example.notificationmanagementservice.exception.NotificationServiceException;
import com.example.notificationmanagementservice.exception.ResourceNotFoundException;
import com.example.notificationmanagementservice.exception.ServiceException;
import com.example.notificationmanagementservice.repository.AttachedFileRepository;
import com.example.notificationmanagementservice.repository.NoticeRepository;
import com.example.notificationmanagementservice.repository.UserRepository;
import com.example.notificationmanagementservice.service.CustomAccountService;
import com.example.notificationmanagementservice.service.NoticeService;
import com.example.notificationmanagementservice.util.DateTimeUtil;
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
public class NoticeServiceImpl implements NoticeService {

    private final Path root = Paths.get("uploads");
    private final String DATE_FORMAT = "yyyy-MM-dd";

    @Autowired
    private CustomAccountService customAccountService;

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AttachedFileRepository attachedFileRepository;

    /**
     * Create new notice
     * @param noticeRequest noticeRequest
     * @return Notice
     * @throws ServiceException If string's byte cannot be obtained
     */
    @Override
    public Notice createNotice(NoticeRequest noticeRequest) throws ServiceException, IOException {
        String userName = customAccountService.getUserName();
        Date startDate = DateTimeUtil.convertStringToDate(noticeRequest.getStartDate(), DATE_FORMAT);
        Date endDate = DateTimeUtil.convertStringToDate(noticeRequest.getEndDate(), DATE_FORMAT);
        // find user
        User user = userRepository.findByUserName(userName).orElse(null);
        log.info("Start findByUserName, result {}", user);
        Notice notice = new Notice();
        notice.setContent(noticeRequest.getContent());
        notice.setTitle(noticeRequest.getTitle());
        notice.setIsEnable(true);
        notice.setEndDate(endDate);
        notice.setStartDate(startDate);
        notice.setViewNumber(0);
        notice.setUser(user);
        List<AttachedFile> attachedFileList = handleMultipleFiles(noticeRequest.getMultipartFile());
        notice.setAttachFiles(attachedFileList);
        return noticeRepository.save(notice);

    }

    /**
     * Get All Notice
     * @param size size
     * @param page page
     * @return Page<Notice>
     */
    @Override
    @Cacheable("notice")
    public Page<Notice> getAllNotice(Integer size, Integer page) {
        Date date = new Date();
        Pageable pageable = PageRequest.of(page, size, Sort.by("lastModifiedDate").descending());
        Page<Notice> noticeDtoPage = noticeRepository.findAllByStartDateLessThanEqualAndEndDateGreaterThanEqualAndIsEnableIsTrue(date, date, pageable);
        log.info("Start findAllWithEnd result {}", noticeDtoPage);
        return noticeDtoPage;
    }

    /**
     * Get Details
     * @param id notice id
     * @return json NoticeDto
     */
    @Override
    @Cacheable(cacheNames = "notice", key = "#id")
    public NoticeDto getDetails(Long id) {
        Notice notice = noticeRepository.findByIdAndEndDateGreaterThanEqualAndIsEnableIsTrue(new Date(), id).orElseThrow(() -> new ResourceNotFoundException("Invalid notice Id:" + id));
        notice.setViewNumber(notice.getViewNumber() + 1);
        noticeRepository.saveAndFlush(notice);
        NoticeDto noticeDto = new NoticeDto();
        BeanUtils.copyProperties(Objects.requireNonNull(notice), noticeDto);
        noticeDto.setUser(notice.getUser().getUserName());
        List<String> image = new ArrayList<>();
        if (notice.getAttachFiles() != null) {
            for (AttachedFile attachedFile : notice.getAttachFiles()) {
                image.add(attachedFile.getName());
            }
        }
        noticeDto.setImage(image);

        return noticeDto;
    }

    /**
     * Delete notice
     * @param id notice id
     * @throws NotificationServiceException If string's byte cannot be obtained
     */
    @Override
    @CacheEvict(cacheNames = "notice", key = "#id")
    public void deleteNotice(Long id) throws NotificationServiceException {
        Notice notice = noticeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invalid notice Id:" + id));
        validateUser(notice.getUser().getUserName());
        notice.setIsEnable(false);
        noticeRepository.save(notice);
    }

    /**
     * Update Notice
     * @param noticeRequest noticeRequest
     * @param id id
     * @return json noticeEntity
     * @throws ServiceException If string's byte cannot be obtained
     */
    @Override
    @CachePut(cacheNames = "notice", key = "#id")
    public Notice updateNotice(NoticeRequest noticeRequest, Long id) throws ServiceException, IOException {
        Notice notice = noticeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invalid notice Id:" + id));
        validateUser(notice.getUser().getUserName());
        BeanUtils.copyProperties(notice, noticeRequest);
        if (noticeRequest.getMultipartFile() !=null){
            List<AttachedFile> attachedFileList = handleMultipleFiles(noticeRequest.getMultipartFile());
            notice.setAttachFiles(attachedFileList);
        }
        return noticeRepository.saveAndFlush(notice);

    }

    private void validateUser(String userName) throws NotificationServiceException {
        String name = customAccountService.getUserName();
        if (!name.equals(userName)) {
            throw new NotificationServiceException(HttpStatus.FORBIDDEN.name(), HttpStatus.FORBIDDEN.getReasonPhrase());
        }
    }

    private List<AttachedFile> handleMultipleFiles(MultipartFile[] files) throws ServiceException, IOException {
        if (root == null) {
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
            AttachedFile attachedFile = new AttachedFile();

            Path pathFile = root.resolve(Objects.requireNonNull(file.getOriginalFilename()));
            Resource resource = new UrlResource(pathFile.toUri());
            attachedFile.setName(resource.getURL().getFile());
            attachedFileList.add(attachedFile);

        }
        return attachedFileList;
    }
}

