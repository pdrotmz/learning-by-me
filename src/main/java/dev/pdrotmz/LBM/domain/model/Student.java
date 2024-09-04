package dev.pdrotmz.LBM.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "tb_student")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student extends BaseUser{

    private String studentUsername;
    private String studentEmail;
    private String studentPassword;
}