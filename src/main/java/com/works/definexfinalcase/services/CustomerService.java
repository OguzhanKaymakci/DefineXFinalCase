package com.works.definexfinalcase.services;

import com.works.definexfinalcase.entities.Customer;
import com.works.definexfinalcase.entities.Role;
import com.works.definexfinalcase.repositories.CustomerRepository;
import com.works.definexfinalcase.utils.REnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class CustomerService {
    final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public ResponseEntity register(Customer jwtCustomer){
        Map<REnum,Object> hm= new LinkedHashMap<>();
        Optional<Customer> optionalJwtCustomer= customerRepository.findByEmailEqualsIgnoreCase(jwtCustomer.getEmail());
        if (!optionalJwtCustomer.isPresent()){
            jwtCustomer.setPassword(encoder().encode(jwtCustomer.getPassword()));
            Customer customer= customerRepository.save(jwtCustomer);
            hm.put(REnum.MESSAGE,"login successful");
            hm.put(REnum.STATUS,true);
            hm.put(REnum.RESULT,customer);
            return new ResponseEntity<>(hm, HttpStatus.OK);
        }else {
            System.out.println("-333");
            hm.put(REnum.STATUS,false);
            hm.put(REnum.MESSAGE,"customer has already registered ");
            return new ResponseEntity<>(hm, HttpStatus.BAD_REQUEST);
        }
    }


    public ResponseEntity<Map<REnum,Object>> delete(Long id){
        Map<REnum,Object> hm= new LinkedHashMap<>();
        Optional<Customer> optionalJwtCustomer= customerRepository.findById(id);
        if (optionalJwtCustomer.isPresent()){
            customerRepository.deleteById(id);
            hm.put(REnum.STATUS,true);
            return new ResponseEntity<>(hm,HttpStatus.OK);
        }else {
            hm.put(REnum.STATUS,false);
            return new ResponseEntity<>(hm,HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Map<REnum,Object>> list(){
        Map<REnum,Object> hm= new LinkedHashMap<>();
        hm.put(REnum.RESULT,customerRepository.findAll());
        hm.put(REnum.STATUS,true);
        return new ResponseEntity<>(hm,HttpStatus.OK);
    }

    public ResponseEntity<Map<REnum,Object>> update(Customer customer){
        Map<REnum,Object> hm = new LinkedHashMap<>();
        Optional<Customer> optionalCustomer= customerRepository.findById(customer.getId());
        try {
            if (optionalCustomer.isPresent()){
                Customer cus= customerRepository.saveAndFlush(customer);
                hm.put(REnum.STATUS,true);
                hm.put(REnum.RESULT,customer);
                return new ResponseEntity<>(hm,HttpStatus.ACCEPTED);
            }else {
                hm.put(REnum.STATUS,false);
                return new ResponseEntity<>(hm,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception ex){
            hm.put(REnum.STATUS,false);
            hm.put(REnum.MESSAGE,ex.getMessage());
            return new ResponseEntity<>(hm,HttpStatus.BAD_REQUEST);
        }
    }

    public Collection roles(Role rolex ) {
        List<GrantedAuthority> ls = new ArrayList<>();
        ls.add( new SimpleGrantedAuthority( rolex.getName() ));
        return ls;
    }



    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

}
