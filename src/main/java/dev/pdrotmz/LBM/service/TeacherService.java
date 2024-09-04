package dev.pdrotmz.LBM.service;

import dev.pdrotmz.LBM.domain.model.Teacher;
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
    public Teacher registerTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    // List Teacher function
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    // List ID Teacher function
    public Optional<Teacher> getTeacherById(UUID idTeacher) {
        return teacherRepository.findById(idTeacher);
    }

    // Update Info Teacher function
    public Teacher updateTeacherInfo(UUID idTeacher, Teacher updateTeacherInfo) {
        return teacherRepository.findById(idTeacher).map(Teacher -> {
            Teacher.setTeacherName(updateTeacherInfo.getTeacherName());
            Teacher.setTeacherEmail(updateTeacherInfo.getTeacherEmail());
            Teacher.setTeacherPassword(updateTeacherInfo.getTeacherPassword());
            return teacherRepository.save(Teacher);
        }).orElseThrow(() -> new EntityNotFoundException("Teacher was not found"));
    }

    // Delete Teacher function
    public void deleteTeacher(UUID idTeacher) {
        teacherRepository.deleteById(idTeacher);
    }
}