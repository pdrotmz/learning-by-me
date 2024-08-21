package dev.pdrotmz.LBM.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("teacher-area")
public class TeacherController {

    @GetMapping
    public String getMessage() {
        return "Hello teacher";
    }
}
