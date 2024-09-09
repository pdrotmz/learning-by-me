package dev.pdrotmz.LBM.infra.security;

import dev.pdrotmz.LBM.domain.model.Teacher;
import dev.pdrotmz.LBM.domain.model.Student;
import dev.pdrotmz.LBM.repository.TeacherRepository;
import dev.pdrotmz.LBM.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Teacher teacher = teacherRepository.findByTeacherEmail(username).orElse(null);
        if (teacher != null) {
            return new org.springframework.security.core.userdetails.User(teacher.getTeacherEmail(), teacher.getTeacherPassword(), new ArrayList<>());
        }

        Student student = studentRepository.findByStudentEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(student.getStudentEmail(), student.getStudentPassword(), new ArrayList<>());
    }
}
