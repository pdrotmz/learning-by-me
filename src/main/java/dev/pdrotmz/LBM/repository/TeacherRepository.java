package dev.pdrotmz.LBM.repository;

import dev.pdrotmz.LBM.domain.model.TeacherModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TeacherRepository extends JpaRepository<TeacherModel, UUID> {
}
