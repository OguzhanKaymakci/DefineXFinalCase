package com.works.definexfinalcase.restControllers;

import com.works.definexfinalcase.entities.Login;
import com.works.definexfinalcase.services.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/definexSystem")
@Validated
@CrossOrigin
public class LoginController {
    final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/auth")
    public ResponseEntity auth(@Valid @RequestBody Login login){
        return loginService.auth(login);

    }

}
