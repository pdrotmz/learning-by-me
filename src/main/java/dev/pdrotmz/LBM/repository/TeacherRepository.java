package dev.pdrotmz.LBM.repository;

import dev.pdrotmz.LBM.domain.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TeacherRepository extends JpaRepository<Teacher, UUID> {
}