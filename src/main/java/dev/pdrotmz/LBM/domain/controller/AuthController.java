package dev.pdrotmz.LBM.domain.controller;

import dev.pdrotmz.LBM.domain.dto.LoginRequestDTO;
import dev.pdrotmz.LBM.domain.dto.RegisterRequestDTO;
import dev.pdrotmz.LBM.domain.dto.ResponseDTO;
import dev.pdrotmz.LBM.domain.model.Teacher;
import dev.pdrotmz.LBM.infra.security.TokenService;
import dev.pdrotmz.LBM.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final TeacherRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO loginRequestDTO) {
        Teacher teacher = this.teacherRepository.findByTeacherEmail(loginRequestDTO.email()).orElseThrow(() -> new RuntimeException("User Not Found"));
        if(passwordEncoder.matches(loginRequestDTO.password(), teacher.getTeacherPassword())) {
            String token = this.tokenService.generateToken(teacher);
            return ResponseEntity.ok(new ResponseDTO(teacher.getName(), token));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO registerRequestDTO) {
        Optional<Teacher> teacher = this.teacherRepository.findByTeacherEmail(registerRequestDTO.email());

        if(teacher.isEmpty()) {
            Teacher newTeacher = new Teacher();
            newTeacher.setTeacherName(registerRequestDTO.name());
            newTeacher.setTeacherEmail(registerRequestDTO.email());
            newTeacher.setTeacherPassword(passwordEncoder.encode(registerRequestDTO.password()));
            this.teacherRepository.save(newTeacher);

            String token = this.tokenService.generateToken(newTeacher);
            return ResponseEntity.ok(new ResponseDTO(newTeacher.getTeacherName(), token));
        }
        return ResponseEntity.badRequest().build();
    }
}
