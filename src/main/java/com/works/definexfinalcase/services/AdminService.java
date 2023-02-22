package com.works.definexfinalcase.services;

import com.works.definexfinalcase.entities.Admin;
import com.works.definexfinalcase.entities.Customer;
import com.works.definexfinalcase.entities.Role;
import com.works.definexfinalcase.repositories.AdminRepository;
import com.works.definexfinalcase.repositories.CustomerRepository;
import com.works.definexfinalcase.utils.REnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.*;

@Service
@Transactional
public class AdminService implements UserDetailsService {
    final AdminRepository adminRepository;
    final CustomerRepository customerRepository;
    final HttpSession httpSession;

    public AdminService(AdminRepository adminRepository, CustomerRepository customerRepository, HttpSession httpSession) {
        this.adminRepository = adminRepository;
        this.customerRepository = customerRepository;
        this.httpSession = httpSession;
    }

    public ResponseEntity<Map<REnum,Object>> register(Admin admin){
        Map<REnum,Object> hm= new LinkedHashMap<>();
        Optional<Admin> optionalAdmin= adminRepository.findByEmailEqualsIgnoreCase(admin.getEmail());
        if (!optionalAdmin.isPresent()){
            admin.setPassword(encoder().encode(admin.getPassword()));
            Admin admin1= adminRepository.save(admin);
            hm.put(REnum.STATUS,true);
            hm.put(REnum.RESULT,admin);

        }else {
            hm.put(REnum.STATUS,false);
            hm.put(REnum.MESSAGE,"customer has already registered ");
        }

        return new ResponseEntity<>(hm, HttpStatus.OK);
    }



    public Collection roles(Role rolex ) {
        List<GrantedAuthority> ls = new ArrayList<>();

        ls.add( new SimpleGrantedAuthority(rolex.getName()));

        return ls;
    }


    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Customer> optionalCustomer = customerRepository.findByEmailEqualsIgnoreCase(username);
        Optional<Admin> optionalAdmin = adminRepository.findByEmailEqualsIgnoreCase(username);
        if (optionalCustomer.isPresent() && !optionalAdmin.isPresent()) {
            Customer c = optionalCustomer.get();
            UserDetails userDetails = new User(
                    c.getEmail(),
                    c.getPassword(),
                    c.isEnabled(),
                    c.isTokenExpired(),
                    true,
                    true,
                    roles(c.getRoles())
            );
            httpSession.setAttribute("customer",c);
            return userDetails;
        }else if (!optionalCustomer.isPresent() && optionalAdmin.isPresent()){
            Admin a = optionalAdmin.get();
            UserDetails userDetails = new User(
                    a.getEmail(),
                    a.getPassword(),
                    a.isEnabled(),
                    a.isTokenExpired(),
                    true,
                    true,
                    roles(a.getRoles()));
            httpSession.setAttribute("admin",a);
            return userDetails;
        }


        else {
            throw new UsernameNotFoundException("User not found");
        }


    }
}
