package dev.pdrotmz.LBM.repository;

import dev.pdrotmz.LBM.model.StudentModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudentRepository extends JpaRepository<StudentModel, UUID> {
}
