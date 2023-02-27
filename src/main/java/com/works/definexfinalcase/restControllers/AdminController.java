package com.works.definexfinalcase.restControllers;

import com.works.definexfinalcase.entities.Admin;
import com.works.definexfinalcase.services.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin")
@Validated
@CrossOrigin
public class AdminController {

    final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody Admin admin){
        return adminService.register(admin);

    }
}
