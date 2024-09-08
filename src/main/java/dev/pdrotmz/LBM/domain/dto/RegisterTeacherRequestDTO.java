package dev.pdrotmz.LBM.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record RegisterTeacherRequestDTO(@NotBlank String name, @NotBlank String email, @NotBlank String password) {
}
