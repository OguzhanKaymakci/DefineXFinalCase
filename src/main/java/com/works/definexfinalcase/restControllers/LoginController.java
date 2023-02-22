package com.works.definexfinalcase.restControllers;

import com.works.definexfinalcase.entities.Customer;
import com.works.definexfinalcase.entities.Login;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/definexSystem")
@Validated
@CrossOrigin
public class LoginController {
    final LoginController loginController;

    public LoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    @PostMapping("/auth")
    public ResponseEntity auth(@Valid @RequestBody Login login){
        return loginController.auth(login);

    }

}
