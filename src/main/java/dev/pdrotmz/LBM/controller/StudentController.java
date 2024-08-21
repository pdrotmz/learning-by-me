package dev.pdrotmz.LBM.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("student-area")
public class StudentController {

    @GetMapping
    public String getMessage() {
        return "Hello Student";
    }
}
