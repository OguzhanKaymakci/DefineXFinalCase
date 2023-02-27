package com.works.definexfinalcase.restControllers;

import com.works.definexfinalcase.entities.Customer;
import com.works.definexfinalcase.services.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/customer")
@Validated
@CrossOrigin
public class CustomerController {

        final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody Customer customer){
        return customerService.register(customer);
    }

    @GetMapping("/list")
    public ResponseEntity list(){
        return customerService.list();
    }

    @PutMapping("/update")
    public ResponseEntity update(@Valid @RequestBody Customer customer){
        return customerService.update(customer);
    }

    @PutMapping("/delete")
    public ResponseEntity delete(@Valid @RequestParam Long id){
        return customerService.delete(id);
    }
}
