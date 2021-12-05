package com.example.notificationmanagementservice.repository;

import com.example.notificationmanagementservice.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {

    //Find All By Start Date Less Than Equal And End Date Greater Than Equal And Is Enable Is True
    Page<Notice> findAllByStartDateLessThanEqualAndEndDateGreaterThanEqualAndIsEnableIsTrue(Date startDate, Date endDate, Pageable pageable);

    Optional<Notice> findByIdAndEndDateGreaterThanEqualAndIsEnableIsTrue(Date date, Long id);

    Page<Notice> findByUserId(Long id, Pageable pageable);
}
