package dev.pdrotmz.LBM.domain.controller;

import dev.pdrotmz.LBM.domain.model.Teacher;
import dev.pdrotmz.LBM.service.TeacherService;
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
    public ResponseEntity<Teacher> registerTeacher(@RequestBody Teacher teacher) {
        Teacher registeredTeacher = teacherService.registerTeacher(teacher);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredTeacher);
    }

    @GetMapping
    public ResponseEntity<List<Teacher>> getAllTeachers() {
        List<Teacher> teachers = teacherService.getAllTeachers();
        return ResponseEntity.ok(teachers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable UUID id) {
        Optional<Teacher> teacher = teacherService.getTeacherById(id);
        return teacher.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Teacher> updateTeacher(@PathVariable UUID id, @RequestBody Teacher teacher) {
        Teacher updateTeacher = teacherService.updateTeacherInfo(id, teacher);
        return ResponseEntity.ok(updateTeacher);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable UUID id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity.noContent().build();
    }
}