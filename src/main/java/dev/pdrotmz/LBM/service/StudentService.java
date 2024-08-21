package dev.pdrotmz.LBM.service;

import dev.pdrotmz.LBM.model.StudentModel;
import dev.pdrotmz.LBM.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public StudentModel registerStudent(StudentModel student) {
        return studentRepository.save(student);
    }

    public List<StudentModel> getAllStudent() {
        return studentRepository.findAll();
    }

    public Optional<StudentModel> getStudentById(UUID idStudent) {
        return studentRepository.findById(idStudent);
    }

    public StudentModel updateStudent(UUID idStudent, StudentModel updateStudentInfo) {
        return studentRepository.findById(idStudent).map(StudentModel -> {
            StudentModel.setStudentEmail(updateStudentInfo.getStudentEmail());
            StudentModel.setStudentUsername(updateStudentInfo.getStudentUsername());
            StudentModel.setStudentPassword(updateStudentInfo.getStudentPassword());
            return studentRepository.save(StudentModel);
        }).orElseThrow(() -> new EntityNotFoundException("Student Not Found"));
    }

    public void deleteStudent(UUID idStudent) {
        studentRepository.deleteById(idStudent);
    }
}
