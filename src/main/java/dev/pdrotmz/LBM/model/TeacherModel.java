package dev.pdrotmz.LBM.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tb_teachers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeacherModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idTeacher;
    private String teacherName;
    private String teacherEmail;
    private String teacherPassword;
}
