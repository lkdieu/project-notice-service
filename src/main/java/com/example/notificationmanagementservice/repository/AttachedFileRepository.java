package com.example.notificationmanagementservice.repository;

import com.example.notificationmanagementservice.entity.AttachedFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachedFileRepository extends JpaRepository<AttachedFile, Long> {
}
