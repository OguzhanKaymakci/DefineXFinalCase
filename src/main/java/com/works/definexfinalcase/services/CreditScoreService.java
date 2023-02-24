package com.works.definexfinalcase.services;

import com.works.definexfinalcase.entities.CreditScore;
import com.works.definexfinalcase.entities.Customer;
import com.works.definexfinalcase.repositories.CreditScoreRepository;
import com.works.definexfinalcase.utils.REnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class CreditScoreService {

    final CreditScoreRepository creditScoreRepository;
    final HttpServletRequest request;


    public CreditScoreService(CreditScoreRepository creditScoreRepository, HttpServletRequest request) {
        this.creditScoreRepository = creditScoreRepository;
        this.request = request;
    }

    public ResponseEntity<Map<REnum,Object>> results(CreditScore creditScore){
        Map<REnum,Object> hm = new LinkedHashMap<>();
        //sistemde olan kullanıcının datalarını almak için
        HttpSession session = request.getSession();
        //String username = (String) session.getAttribute("username");
        creditScore.setName((String) session.getAttribute("firstName"));
        creditScore.setSurName((String) session.getAttribute("lastName"));
        creditScore.setCreditScoreResult((String) session.getAttribute("creditScore"));
        creditScore.setSalary((Long) session.getAttribute("salary"));
        creditScore.setGuarantee((Long) session.getAttribute("guarantee"));

        Long guarantee=creditScore.getGuarantee();
        int creditScoreNum= Integer.parseInt(creditScore.getCreditScoreResult());
        Long salary = creditScore.getSalary();
        try {
            //Kredi skoru 500’ün altında ise kullanıcı reddedilir. (Kredi sonucu: Red)
        if (creditScoreNum<500){
            creditScore.setCreditScoreResult("Credit Result: declined");
            CreditScore cdS= creditScoreRepository.save(creditScore);
            hm.put(REnum.STATUS,true);
            hm.put(REnum.RESULT,creditScore);
            /*Kredi skoru 500 puan ile 1000 puan arasında ise ve aylık geliri 5000 TL’nin altında ise
Kullanıcının kredi başvurusu onaylanır ve kullanıcıya 10.000 TL limit atanır. (Kredi Sonucu: Onay). Eğer teminat vermişse teminat bedelinin yüzde 10 u kadar tutar kredi limitine eklenir.*/
        }else if (creditScoreNum>=500 && creditScoreNum<1000 && salary<5000){
            Optional<Customer> optionalCustomerSalary = (Optional<Customer>) session.getAttribute("guarantee");

            if (optionalCustomerSalary.isPresent()){
                creditScore.setCreditLimit("Credit Result Accepted: "+ (10000 + guarantee*0.1+"Turkish Liras"));
                CreditScore cdS= creditScoreRepository.save(creditScore);
                hm.put(REnum.STATUS,true);
                hm.put(REnum.RESULT,creditScore);
            }else {
                creditScore.setCreditLimit("Credit Result Accepted: "+ 10000 +"Turkish Liras");
                CreditScore cdS= creditScoreRepository.save(creditScore);
                hm.put(REnum.STATUS,true);
                hm.put(REnum.RESULT,creditScore);
            }
/*Kredi skoru 500 puan ile 1000 puan arasında ise ve aylık geliri 5000 TL ile 10.000TL arasında ise kullanıcının kredi başvurusu onaylanır ve kullanıcıya 20.000 TL limit
atanır. (Kredi Sonucu:Onay) Eğer teminat vermişse teminat bedelinin yüzde 20 si kadar tutar kredi limitine eklenir.*/
        }else if (creditScoreNum>=500 && creditScoreNum<1000 && salary>=5000 && salary<10000){
            Optional<Customer> optionalCustomerSalary = (Optional<Customer>) session.getAttribute("guarantee");

            if (optionalCustomerSalary.isPresent()){
                creditScore.setCreditLimit("Credit Result Accepted: "+ (20000 + guarantee*0.2+"Turkish Liras"));
                CreditScore cdS= creditScoreRepository.save(creditScore);
                hm.put(REnum.STATUS,true);
                hm.put(REnum.RESULT,creditScore);
            }else {
                creditScore.setCreditLimit("Credit Result Accepted: "+ 20000 +"Turkish Liras");
                CreditScore cdS= creditScoreRepository.save(creditScore);
                hm.put(REnum.STATUS,true);
                hm.put(REnum.RESULT,creditScore);
            }

















        }
        }catch (Exception e){
            hm.put(REnum.RESULT,false);
            hm.put(REnum.MESSAGE,e.getMessage());
            return new ResponseEntity<>(hm, HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<>(hm, HttpStatus.OK);
    }
}
