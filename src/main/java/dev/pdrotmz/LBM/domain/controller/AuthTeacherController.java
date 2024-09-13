package dev.pdrotmz.LBM.domain.controller;

import dev.pdrotmz.LBM.domain.dto.LoginTeacherRequestDTO;
import dev.pdrotmz.LBM.domain.dto.RegisterTeacherRequestDTO;
import dev.pdrotmz.LBM.domain.dto.ResponseTeacherDTO;
import dev.pdrotmz.LBM.domain.model.Teacher;
import dev.pdrotmz.LBM.infra.security.TokenService;
import dev.pdrotmz.LBM.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth-teacher")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AuthTeacherController {

    private final TeacherRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<ResponseTeacherDTO> login(@RequestBody LoginTeacherRequestDTO loginRequestDTO) {
        Teacher teacher = this.teacherRepository.findByTeacherEmail(loginRequestDTO.email()).
                orElseThrow(() -> new RuntimeException("User Not Found"));
        if(passwordEncoder.matches(loginRequestDTO.password(), teacher.getTeacherPassword())) {
            String token = this.tokenService.generateTokenForTeacher(teacher);
            String redirectURL = "/teacher-area/" + teacher.getId();
            return ResponseEntity.ok(new ResponseTeacherDTO(teacher.getTeacherName(), token, redirectURL));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterTeacherRequestDTO registerRequestDTO) {
        Optional<Teacher> teacher = this.teacherRepository.findByTeacherEmail(registerRequestDTO.email());

        if(teacher.isEmpty()) {
            Teacher newTeacher = new Teacher();
            newTeacher.setTeacherName(registerRequestDTO.name());
            newTeacher.setTeacherEmail(registerRequestDTO.email());
            newTeacher.setTeacherPassword(passwordEncoder.encode(registerRequestDTO.password()));
            this.teacherRepository.save(newTeacher);

            String token = this.tokenService.generateTokenForTeacher(newTeacher);
            String redirectUrl = "/teacher-area/" + newTeacher.getId();
            return ResponseEntity.ok(new ResponseTeacherDTO(newTeacher.getTeacherName(), token, redirectUrl));
        }
        return ResponseEntity.badRequest().build();
    }
}
