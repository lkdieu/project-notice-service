package com.example.noticemanagementservice.repository;

import com.example.noticemanagementservice.entity.AttachedFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachedFileRepository extends JpaRepository<AttachedFile, Long> {
}
