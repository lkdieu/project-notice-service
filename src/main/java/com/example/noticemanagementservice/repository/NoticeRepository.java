package com.example.noticemanagementservice.repository;

import com.example.noticemanagementservice.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {

    Optional<Notice> findByIdAndEndDateGreaterThanEqualAndIsEnableTrue(Long id, Date date);

    Page<Notice> findByUserId(Long id, Pageable pageable);

    Page<Notice> findAllByEndDateGreaterThanEqualAndIsEnableIsTrue(Date date, Pageable pageable);
}
