package com.works.definexfinalcase.restControllers;

import com.works.definexfinalcase.entities.CreditScore;
import com.works.definexfinalcase.entities.Customer;
import com.works.definexfinalcase.services.CreditScoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;
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

    @PostMapping("/apply")
    public ResponseEntity apply(CreditScore creditScore){
        /*HttpSession session = request.getSession();*/
        return creditScoreService.results(creditScore);

    }

    @GetMapping("listByIdAndBirthdate")
    public ResponseEntity listByIdAndBirthdate(@Valid @RequestParam String id, @RequestParam LocalDate date){
        return creditScoreService.listByIdAndBirthdate(id,date);
    }

}
