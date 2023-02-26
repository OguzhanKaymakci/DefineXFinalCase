package com.works.definexfinalcase.services;

import com.works.definexfinalcase.configs.JwtUtil;
import com.works.definexfinalcase.entities.Admin;
import com.works.definexfinalcase.entities.Customer;
import com.works.definexfinalcase.entities.Login;
import com.works.definexfinalcase.utils.REnum;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@Transactional
public class LoginService {

    final AuthenticationManager authenticationManager;
    final AdminService adminService;
    final JwtUtil jwtUtil;
    final HttpSession httpSession;

    public LoginService(AuthenticationManager authenticationManager, AdminService adminService, JwtUtil jwtUtil, HttpSession httpSession) {
        this.authenticationManager = authenticationManager;
        this.adminService = adminService;
        this.jwtUtil = jwtUtil;

        this.httpSession = httpSession;
    }

    public ResponseEntity auth (@Lazy Login login){
        Map<REnum,Object> hm = new LinkedHashMap<>();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    login.getUsername(),login.getPassword()));
            UserDetails userDetails=adminService.loadUserByUsername(login.getUsername());
            String jwt= jwtUtil.generateToken(userDetails);
            Customer customer= (Customer) httpSession.getAttribute("customer");
            Admin admin= (Admin) httpSession.getAttribute("admin");

            if (admin==null && customer!=null){
                hm.put(REnum.STATUS,true);
                hm.put(REnum.MESSAGE,"login successful");
                hm.put(REnum.RESULT,customer);
                hm.put(REnum.JWT,jwt);
                return new ResponseEntity(hm, HttpStatus.OK);
            }else {
                hm.put(REnum.STATUS,true);
                hm.put(REnum.MESSAGE,"login successful");
                hm.put(REnum.RESULT,admin);
                hm.put(REnum.JWT,jwt);
                return new ResponseEntity(hm,HttpStatus.OK);
            }

        }catch (Exception ex){
            hm.put(REnum.STATUS,false);
            hm.put(REnum.ERROR,ex.getMessage());
            return new ResponseEntity(hm,HttpStatus.NOT_ACCEPTABLE);
        }


    }

}
