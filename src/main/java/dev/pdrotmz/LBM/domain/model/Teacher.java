package dev.pdrotmz.LBM.domain.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "tb_teachers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Teacher extends BaseUser {

    @NotBlank
    private String teacherName;

    @NotBlank
    private String teacherEmail;

    @NotBlank
    private String teacherPassword;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Video> videos;
}
