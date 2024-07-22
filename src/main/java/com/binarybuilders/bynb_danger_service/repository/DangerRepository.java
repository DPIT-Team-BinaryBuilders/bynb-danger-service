package com.binarybuilders.bynb_danger_service.repository;

import com.binarybuilders.bynb_danger_service.persistence.DangerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DangerRepository extends JpaRepository<DangerEntity, Long> {
    Optional<DangerEntity> findByName(String name);
    List<DangerEntity> findByUserId(long userId);
}
