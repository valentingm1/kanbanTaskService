package com.example.task_service.repository;

import com.example.task_service.entity.Swimlane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SwimlaneRepository extends JpaRepository<Swimlane,Long> {
    boolean existsByName(String name);
}
