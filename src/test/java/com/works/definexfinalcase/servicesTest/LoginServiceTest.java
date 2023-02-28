package com.works.definexfinalcase.servicesTest;

import com.works.definexfinalcase.configs.JwtUtil;
import com.works.definexfinalcase.entities.Admin;
import com.works.definexfinalcase.entities.Customer;
import com.works.definexfinalcase.entities.Login;
import com.works.definexfinalcase.restControllers.LoginController;
import com.works.definexfinalcase.services.AdminService;
import com.works.definexfinalcase.utils.REnum;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpSession;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoginServiceTest {

    Login login = new Login();
    private LoginController authControllers;
    private AuthenticationManager authenticationManager;
    private AdminService adminService;
    private JwtUtil jwtUtil;
    private HttpSession httpSession;

    @Test
    public void testAuth() {

        login.setUsername("alibilmem@mail.com");
        login.setPassword("asdasd_.*-/asd");

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
        when(authenticationManager.authenticate(authenticationToken)).thenReturn(authenticationToken);

        UserDetails userDetails = mock(UserDetails.class);
        when(adminService.loadUserByUsername(login.getUsername())).thenReturn(userDetails);

        String jwt = "testJwt";
        when(jwtUtil.generateToken(userDetails)).thenReturn(jwt);

        Customer customer = new Customer();
        customer.setId(1L);
        when(httpSession.getAttribute("customer")).thenReturn(customer);

        Admin admin = new Admin();
        admin.setId(1);
        when(httpSession.getAttribute("admin")).thenReturn(admin);

        Map<REnum,Object> expectedResponse = new LinkedHashMap<>();
        expectedResponse.put(REnum.STATUS, true);
        expectedResponse.put(REnum.RESULT, admin);
        expectedResponse.put(REnum.JWT, jwt);
        ResponseEntity expectedEntity = new ResponseEntity(expectedResponse, HttpStatus.OK);

        ResponseEntity responseEntity = authControllers.auth(login);

        assertEquals(expectedEntity, responseEntity);
    }
}
