package com.works.definexfinalcase.services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.works.definexfinalcase.entities.CreditScore;
import com.works.definexfinalcase.repositories.CreditScoreRepository;
import com.works.definexfinalcase.utils.REnum;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
public class CreditScoreService {

    private static final Logger logger = LogManager.getLogger(CreditScoreService.class);

    final CreditScoreRepository creditScoreRepository;
    final HttpServletRequest request;


    public CreditScoreService(CreditScoreRepository creditScoreRepository, HttpServletRequest request) {
        this.creditScoreRepository = creditScoreRepository;
        this.request = request;
    }

    public ResponseEntity<Map<REnum,Object>> results(CreditScore creditScore){
        logger.debug("Debug message");
        logger.info("save to database");
        logger.warn("Warn message");
        logger.error("Error message");
        logger.fatal("Fatal message");
        Map<REnum,Object> hm = new LinkedHashMap<>();
      /*  //sistemde olan kullanıcının datalarını almak için
        session = request.getSession();
        // HttpSession session = request.getSession();
        //String username = (String) session.getAttribute("username");
        creditScore.setName((String) session.getAttribute("firstName"));
        creditScore.setSurName((String) session.getAttribute("lastName"));
        creditScore.setCreditScoreResult((String) session.getAttribute("creditScore"));
        creditScore.setSalary((Long) session.getAttribute("salary"));
        creditScore.setGuarantee((Long) session.getAttribute("guarantee"));
        creditScore.setPhone((String) session.getAttribute("phone"));
        creditScore.setBirthDate((Date) session.getAttribute("birthday"));
        creditScore.setIdNo((String) session.getAttribute("idNo"));
*/

       // int creditScoreNum= Integer.parseInt(creditScore.getCreditScoreResult());
        int creditScoreNum= Integer.parseInt(creditScore.getCreditScoreResult());
        int salary= Integer.parseInt(creditScore.getSalary());
        /*Long salary = creditScore.getSalary();*/
        try {
            //Kredi skoru 500’ün altında ise kullanıcı reddedilir. (Kredi sonucu: Red)
        if (creditScoreNum<500){
            creditScore.setCreditScoreResult("Credit Result: declined");
            logger.info("save to database");
            CreditScore cdS= creditScoreRepository.save(creditScore);
            hm.put(REnum.STATUS,true);
            hm.put(REnum.RESULT,creditScore);
            /*Kredi skoru 500 puan ile 1000 puan arasında ise ve aylık geliri 5000 TL’nin altında ise
Kullanıcının kredi başvurusu onaylanır ve kullanıcıya 10.000 TL limit atanır. (Kredi Sonucu: Onay). Eğer teminat vermişse teminat bedelinin yüzde 10 u kadar tutar kredi limitine eklenir.*/
        }else if (creditScoreNum>=500 && creditScoreNum<1000 && salary<5000){
           // Optional<Customer> optionalCustomerSalary = (Optional<Customer>) session.getAttribute("guarantee");
            Optional<CreditScore> creditScoreOptionalGuarantee = creditScoreRepository.findByGuarantee(creditScore.getGuarantee());


            if (creditScoreOptionalGuarantee.isPresent()){
                Long guarantee=creditScore.getGuarantee();
                creditScore.setCreditLimit("Credit Result Accepted: "+ (10000 + guarantee*0.1+"Turkish Liras"));
                logger.info("save to database");
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
           /* Optional<Customer> optionalCustomerSalary = (Optional<Customer>) session.getAttribute("guarantee");*/
            Optional<CreditScore> creditScoreOptionalGuarantee = creditScoreRepository.findByGuarantee(creditScore.getGuarantee());
            if (creditScoreOptionalGuarantee.isPresent()){
                Long guarantee=creditScore.getGuarantee();
                creditScore.setCreditLimit("Credit Result Accepted: "+ (20000 + guarantee*0.2) +" Turkish Liras");
                logger.info("save to database");
                CreditScore cdS= creditScoreRepository.save(creditScore);
                hm.put(REnum.STATUS,true);
                hm.put(REnum.RESULT,creditScore);
            }else {
                creditScore.setCreditLimit("Credit Result Accepted: "+ 20000 +" Turkish Liras");
                logger.info("save to database");
                CreditScore cdS= creditScoreRepository.save(creditScore);
                hm.put(REnum.STATUS,true);
                hm.put(REnum.RESULT,creditScore);


            }
           /* Kredi skoru 500 puan ile 1000 puan arasında ise ve aylık geliri 10.000 TL’nin üstünde ise kullanıcının kredi başvurusu onaylanır ve kullanıcıya AYLIK GELİR BİLGİSİ *
            KREDİ LİMİT ÇARPANI/2 kadar limit atanır. (Kredi Sonucu:Onay) Eğer teminat vermişse teminat bedelinin yüzde 25 i kadar tutar kredi limitine eklenir.*/
        }else if (creditScoreNum>=500 && creditScoreNum<1000 && salary>10000){
            /*Optional<Customer> optionalCustomerSalary = (Optional<Customer>) session.getAttribute("guarantee");*/
            Optional<CreditScore> creditScoreOptionalGuarantee = creditScoreRepository.findByGuarantee(creditScore.getGuarantee());
            if (creditScoreOptionalGuarantee.isPresent()){
                Long guarantee=creditScore.getGuarantee();
                creditScore.setCreditLimit("Credit Result Accepted: "+ (((salary*4)/2)+guarantee*0.25) +"Turkish Liras");
                logger.info("save to database");
                CreditScore cdS= creditScoreRepository.save(creditScore);
                hm.put(REnum.STATUS,true);
                hm.put(REnum.RESULT,creditScore);
            }else {
                creditScore.setCreditLimit("Credit Result Accepted: "+ ((salary*4)/2) +"Turkish Liras");
                logger.info("save to database");
                CreditScore cdS= creditScoreRepository.save(creditScore);
                hm.put(REnum.STATUS,true);
                hm.put(REnum.RESULT,creditScore);
            }
        }

        /*Kredi skoru 1000 puana eşit veya üzerinde ise kullanıcıya AYLIK GELİR BİLGİSİ * KREDİ LİMİT ÇARPANI kadar
        limit atanır. (Kredi Sonucu: Onay) Eğer teminat vermişse teminat bedelinin yüzde 50 si kadar tutar kredi limitine eklenir.*/

        else if (creditScoreNum>=1000 ){
            /*Optional<Customer> optionalCustomerSalary = (Optional<Customer>) session.getAttribute("guarantee");*/
            Optional<CreditScore> creditScoreOptionalGuarantee = creditScoreRepository.findByGuarantee(creditScore.getGuarantee());
            if (creditScoreOptionalGuarantee.isPresent()){
                Long guarantee=creditScore.getGuarantee();
                creditScore.setCreditLimit("Credit Result Accepted: "+ (((salary*4))+guarantee*0.5) +"Turkish Liras");
                logger.info("save to database");
                CreditScore cdS= creditScoreRepository.save(creditScore);
                hm.put(REnum.STATUS,true);
                hm.put(REnum.RESULT,creditScore);
            }else {
                creditScore.setCreditLimit("Credit Result Accepted: "+ ((salary*4)) +"Turkish Liras");
                logger.info("save to database");
                CreditScore cdS= creditScoreRepository.save(creditScore);
                hm.put(REnum.STATUS,true);
                hm.put(REnum.RESULT,creditScore);
            }
        }

        //Daha sonrasında ise ilgili telefon numarasına bilgilendirme SMS’i gönderilir
            final String ACCOUNT_SID = "ACf6487795366c938237bd918adc87681d";
            final String AUTH_TOKEN = "8018a68aa47c09d1df99256d63972fc4";
            final String FROM_PHONE_NUMBER = "+12762658677";
            /*final String TO_PHONE_NUMBER = String.valueOf(creditScore.getPhone());*/
            final String TO_PHONE_NUMBER = "+905366584771";



            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

            Message message = Message.creator(
                            new com.twilio.type.PhoneNumber(TO_PHONE_NUMBER),  // To number
                            new PhoneNumber(FROM_PHONE_NUMBER),  // From number
                            "Your loan application has been received and approved.")           // SMS body
                    .create();

            System.out.println(message.getSid());

/*            String message= creditScore.getName() + creditScore.getSurName() + creditScore.getCreditScoreResult()+ creditScore.getCreditLimit().toString() + creditScore.getGuarantee().toString();
            Message smsMessage = new Message.Builder()
                    .body(new Body(message))
                    .from(new com.twilio.type.PhoneNumber("YOUR_TWILIO_PHONE_NUMBER"))
                    .to(new com.twilio.type.PhoneNumber(toNumber))
                    .build();

            try {
                Message response = twilioRestClient.messages().create(smsMessage);
                System.out.println("SMS sent to " + response.getTo() + " with SID " + response.getSid());
            } catch (TwilioRestException e) {
                System.out.println("Failed to send SMS: " + e.getMessage());
            }*/

        }catch (Exception e){
            hm.put(REnum.STATUS,false);
            hm.put(REnum.MESSAGE,e.getMessage());
            return new ResponseEntity<>(hm, HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<>(hm, HttpStatus.OK);
    }


    public ResponseEntity<Map<REnum,Object>> listByIdAndBirthdate(@Lazy Long id,@Lazy LocalDate date){
        HashMap<REnum,Object> hm= new LinkedHashMap<>();

        try {
            Optional<CreditScore> creditScoreOptional= creditScoreRepository.findByBirthDateAndIdNo(id,date);
            if (creditScoreOptional.isPresent()){
                hm.put(REnum.STATUS,true);
                logger.info("list by Id No");
                hm.put(REnum.RESULT,creditScoreRepository.findByIdNo(id));
            }

        }catch (Exception e){
            hm.put(REnum.STATUS,false);
            hm.put(REnum.RESULT,e.getMessage());
            logger.error("exception case");
            return new ResponseEntity<>(hm,HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<>(hm,HttpStatus.OK);



    }



}

