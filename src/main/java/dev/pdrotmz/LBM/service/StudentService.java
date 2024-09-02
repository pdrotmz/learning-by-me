package dev.pdrotmz.LBM.service;

import dev.pdrotmz.LBM.domain.model.Student;
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

    public Student registerStudent(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> getAllStudent() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(UUID idStudent) {
        return studentRepository.findById(idStudent);
    }

    public Student updateStudent(UUID idStudent, Student updateStudentInfo) {
        return studentRepository.findById(idStudent).map(Student -> {
            Student.setStudentEmail(updateStudentInfo.getStudentEmail());
            Student.setStudentUsername(updateStudentInfo.getStudentUsername());
            Student.setStudentPassword(updateStudentInfo.getStudentPassword());
            return studentRepository.save(Student);
        }).orElseThrow(() -> new EntityNotFoundException("Student Not Found"));
    }

    public void deleteStudent(UUID idStudent) {
        studentRepository.deleteById(idStudent);
    }
}
