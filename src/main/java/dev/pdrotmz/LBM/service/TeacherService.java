package dev.pdrotmz.LBM.service;

import dev.pdrotmz.LBM.domain.model.TeacherModel;
import dev.pdrotmz.LBM.repository.TeacherRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    // Teacher register function
    public TeacherModel registerTeacher(TeacherModel teacherModel) {
        return teacherRepository.save(teacherModel);
    }

    // List Teacher function
    public List<TeacherModel> getAllTeachers() {
        return teacherRepository.findAll();
    }

    // List ID Teacher function
    public Optional<TeacherModel> getTeacherById(UUID idTeacher) {
        return teacherRepository.findById(idTeacher);
    }

    // Update Info Teacher function
    public TeacherModel updateTeacherInfo(UUID idTeacher, TeacherModel updateTeacherInfo) {
        return teacherRepository.findById(idTeacher).map(TeacherModel -> {
            TeacherModel.setTeacherName(updateTeacherInfo.getTeacherName());
            TeacherModel.setTeacherEmail(updateTeacherInfo.getTeacherEmail());
            TeacherModel.setTeacherPassword(updateTeacherInfo.getTeacherPassword());
            return teacherRepository.save(TeacherModel);
        }).orElseThrow(() -> new EntityNotFoundException("Teacher was not found"));
    }

    // Delete Teacher function
    public void deleteTeacher(UUID idTeacher) {
        teacherRepository.deleteById(idTeacher);
    }
}
