package com.example.notificationmanagementservice.repository;

import com.example.notificationmanagementservice.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {

    @Query(value = "SELECT n FROM Notice n WHERE n.endDate <= :date")
    Optional<List<Notice>> findAllWithEndDate(@Param("date") Date date, Pageable pageable);

    Page<Notice> findAllByStartDateLessThanEqualAndEndDateGreaterThanEqualAndIsEnableIsTrue(Date startDate, Date endDate, Pageable pageable);

    Optional<Notice> findByIdAndEndDateGreaterThanEqualAndIsEnableIsTrue(Date date, Long id);
}
