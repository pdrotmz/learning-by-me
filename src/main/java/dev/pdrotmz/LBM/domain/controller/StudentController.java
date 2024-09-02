package dev.pdrotmz.LBM.domain.controller;

import dev.pdrotmz.LBM.domain.model.StudentModel;
import dev.pdrotmz.LBM.service.StudentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("student-area")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("register-student")
    public ResponseEntity<StudentModel> registerStudent(@RequestBody StudentModel student) {
        StudentModel registeredStudent = studentService.registerStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredStudent);
    }

    @GetMapping
    public ResponseEntity<List<StudentModel>> getAllStudent(){
        List<StudentModel> students = studentService.getAllStudent();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentModel> getStudentById(@PathVariable UUID id) {
        Optional<StudentModel> student = studentService.getStudentById(id);
        return student.map(ResponseEntity::ok).orElseGet(()
                ->ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentModel> updateStudent(@PathVariable UUID id,
                                                                @RequestBody StudentModel student) {
        try {
            StudentModel updateStudent = studentService.updateStudent(id, student);
            return ResponseEntity.ok().body(updateStudent);
        } catch(EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable UUID id){
        try {
            studentService.deleteStudent(id);
            return ResponseEntity.noContent().build();
        } catch(EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}