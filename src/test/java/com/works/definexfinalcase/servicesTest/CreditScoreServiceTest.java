package com.works.definexfinalcase.servicesTest;

import com.works.definexfinalcase.entities.CreditScore;
import com.works.definexfinalcase.repositories.CreditScoreRepository;
import com.works.definexfinalcase.restControllers.CreditScoreController;
import com.works.definexfinalcase.services.CreditScoreService;
import com.works.definexfinalcase.utils.REnum;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CreditScoreServiceTest {
    @Mock
    private CreditScoreRepository creditScoreRepository;

    @InjectMocks
    private CreditScoreController creditScoreController;
    @InjectMocks
    private CreditScoreService creditScoreService;

    private CreditScore creditScore;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        creditScore = new CreditScore();
        creditScore.setName("John");
        creditScore.setSurName("Doe");
        creditScore.setCreditScoreResult("700");
        creditScore.setSalary("8000");
        creditScore.setGuarantee(Long.valueOf("5000"));
        creditScore.setPhone(1215455545);
        creditScore.setBirthDate(LocalDate.parse("1985-05-10"));
        creditScore.setIdNo("12345678910");
    }

    @Test
    public void testResultsWhenCreditScoreBelow500() {
        creditScore.setCreditScoreResult("400");

        ResponseEntity<Map<REnum, Object>> response = creditScoreService.results(creditScore);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Credit Result: declined", ((CreditScore) response.getBody().get(REnum.RESULT)).getCreditLimit());
    }

    @Test
    public void testResultsWhenCreditScoreBetween500And1000AndSalaryLessThan5000() {
        creditScore.setCreditScoreResult("700");
        creditScore.setSalary("4000");

        CreditScore creditScoreWithGuarantee = new CreditScore();
        creditScoreWithGuarantee.setGuarantee(Long.valueOf("2000"));

        ResponseEntity<Map<REnum, Object>> response = creditScoreController.apply(creditScore);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Credit Result Accepted: 12000.0 Turkish Liras", ((CreditScore) response.getBody().get(REnum.RESULT)).getCreditLimit());
    }

    @Test
    public void testResultsWhenCreditScoreBetween500And1000AndSalaryBetween5000And10000() {
        creditScore.setCreditScoreResult("700");
        creditScore.setSalary("8000");

        CreditScore creditScoreWithGuarantee = new CreditScore();
        creditScoreWithGuarantee.setGuarantee(Long.valueOf("2000"));

        ResponseEntity<Map<REnum, Object>> response = creditScoreController.apply(creditScore);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Credit Result Accepted: 24000.0 Turkish Liras", ((CreditScore) response.getBody().get(REnum.RESULT)).getCreditLimit());
    }

    @Test
    public void testResultsWhenCreditScoreBetween500And1000AndSalaryGreaterThan10000() {
        creditScore.setCreditScoreResult("700");
        creditScore.setSalary("12000");

        CreditScore creditScoreWithGuarantee = new CreditScore();
        creditScoreWithGuarantee.setGuarantee(Long.valueOf("2000"));

        ResponseEntity<Map<REnum, Object>> response = creditScoreController.apply(creditScore);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Credit Result Accepted: 60000.0 Turkish Liras", ((CreditScore) response.getBody().get(REnum.RESULT)).getCreditLimit());
    }

    @Test
    public void testResultsWhenCreditScoreGreaterThan1000(){
        creditScore.setCreditScoreResult("1200");
        creditScore.setSalary("2500");

        CreditScore creditScore1= new CreditScore();
        creditScore1.setGuarantee(Long.valueOf("2500"));

        ResponseEntity<Map<REnum, Object>> response = creditScoreController.apply(creditScore);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Credit Result Accepted: 60000.0 Turkish Liras", ((CreditScore) response.getBody().get(REnum.RESULT)).getCreditLimit());

    }
}
