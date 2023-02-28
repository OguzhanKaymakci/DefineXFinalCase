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
    public ResponseEntity apply(@Valid @RequestBody CreditScore creditScore){
        /*HttpSession session = request.getSession();*/
        return creditScoreService.results(creditScore);

    }

    @PostMapping("listByIdAndBirthdate")
    public ResponseEntity listByIdAndBirthdate(@RequestParam Long id, @RequestParam LocalDate date){
        Long uptId= Long.valueOf(id);
        //LocalDate lclDate= LocalDate.parse(date);
        return creditScoreService.listByIdAndBirthdate(id,date);
    }

}
