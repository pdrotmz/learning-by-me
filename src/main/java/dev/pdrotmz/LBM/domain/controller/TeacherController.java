package dev.pdrotmz.LBM.domain.controller;

import dev.pdrotmz.LBM.domain.model.TeacherModel;
import dev.pdrotmz.LBM.service.TeacherService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("teacher-area")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @PostMapping("/register-teacher")
    public ResponseEntity<TeacherModel> registerTeacher(@RequestBody TeacherModel teacher) {
        TeacherModel registeredTeacher = teacherService.registerTeacher(teacher);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredTeacher);
    }

    @GetMapping
    public ResponseEntity<List<TeacherModel>> getAllTeachers() {
        List<TeacherModel> teachers = teacherService.getAllTeachers();
        return ResponseEntity.ok(teachers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherModel> getTeacherById(@PathVariable UUID id) {
        Optional<TeacherModel> teacher = teacherService.getTeacherById(id);
        return teacher.map(ResponseEntity::ok).orElseGet(()
                -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherModel> updateTeacher(@PathVariable UUID id,
                                                      @RequestBody TeacherModel teacher) {
        try {
            TeacherModel updateTeacher = teacherService.updateTeacherInfo(id, teacher);
            return ResponseEntity.ok(updateTeacher);

        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable UUID id) {
        try {
            teacherService.deleteTeacher(id);
            return ResponseEntity.noContent().build();

        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
