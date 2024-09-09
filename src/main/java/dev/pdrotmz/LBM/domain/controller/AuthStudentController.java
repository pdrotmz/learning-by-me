package dev.pdrotmz.LBM.domain.controller;

import dev.pdrotmz.LBM.domain.dto.LoginStudentRequestDTO;
import dev.pdrotmz.LBM.domain.dto.RegisterStudentRequestDTO;
import dev.pdrotmz.LBM.domain.dto.ResponseStudentDTO;
import dev.pdrotmz.LBM.domain.model.Student;
import dev.pdrotmz.LBM.infra.security.TokenService;
import dev.pdrotmz.LBM.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth-student")
@RequiredArgsConstructor
public class AuthStudentController {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<ResponseStudentDTO> login(@RequestBody LoginStudentRequestDTO loginRequestDTO) {
        Student student = this.studentRepository.findByStudentEmail(loginRequestDTO.email())
                .orElseThrow(() -> new RuntimeException("User Not Found"));
        if(passwordEncoder.matches(loginRequestDTO.password(), student.getStudentPassword())) {
            String token = this.tokenService.generateTokenForStudent(student);
            String redirectURL = "/student-area/" + student.getId();
            return ResponseEntity.ok(new ResponseStudentDTO(student.getStudentUsername(), token, redirectURL));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterStudentRequestDTO registerRequestDTO) {
        Optional<Student> student = this.studentRepository.findByStudentEmail(registerRequestDTO.email());

        if(student.isEmpty()) {
            Student newStudent = new Student();
            newStudent.setStudentUsername(registerRequestDTO.name());
            newStudent.setStudentEmail(registerRequestDTO.email());
            newStudent.setStudentPassword(passwordEncoder.encode(registerRequestDTO.password()));
            this.studentRepository.save(newStudent);

            String token = this.tokenService.generateTokenForStudent(newStudent);
            String redirectUrl = "/student-area/" + newStudent.getId();
            return ResponseEntity.ok(new ResponseStudentDTO(newStudent.getStudentUsername(), token, redirectUrl));
        }
        return ResponseEntity.badRequest().build();
    }
}
