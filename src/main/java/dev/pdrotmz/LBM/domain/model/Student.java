package dev.pdrotmz.LBM.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    private String studentUsername;
    @NotBlank
    private String studentEmail;
    @NotBlank
    private String studentPassword;
}