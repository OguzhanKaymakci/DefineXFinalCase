package com.works.definexfinalcase.restControllers;

import com.works.definexfinalcase.entities.Customer;
import com.works.definexfinalcase.services.CreditScoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/credit")
@Validated
@CrossOrigin
public class CreditScoreController {

    final CreditScoreService creditScoreService;

    public CreditScoreController(CreditScoreService creditScoreService) {
        this.creditScoreService = creditScoreService;
    }

    @GetMapping("/apply")
    public ResponseEntity apply(HttpServletRequest request){
        HttpSession session = request.getSession();
        return creditScoreService.results(session);

    }

    @GetMapping("listByIdAndBirthdate")
    public ResponseEntity listByIdAndBirthdate(@Valid @RequestParam String id, @RequestParam Date date){
        return creditScoreService.listByIdAndBirthdate(id,date);
    }

}
