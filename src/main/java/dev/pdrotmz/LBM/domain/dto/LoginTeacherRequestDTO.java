package dev.pdrotmz.LBM.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginTeacherRequestDTO(@NotBlank String email, @NotBlank String password) {
}
