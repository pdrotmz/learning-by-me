package dev.pdrotmz.LBM.repository;

import dev.pdrotmz.LBM.domain.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
    Optional<Student> findByStudentEmail(String studentEmail);
}