package com.works.definexfinalcase.servicesTest;

/*Bu test, Admin nesnesinin e-postası daha önce kaydedilmemişse beklenen bir cevap verip vermediğini ve aynı e-posta ile kaydedilmişse beklenen bir cevap
verip vermediğini doğrular. Testte Mockito kütüphanesi kullanılarak adminRepository örneği sahte bir nesne olarak oluşturulur ve findByEmailEqualsIgnoreCase
yöntemi için bir davranış belirtilir. Ardından, register yöntemi çağrılır ve yanıtın doğruluğu test edilir.*/


import com.works.definexfinalcase.entities.Admin;
import com.works.definexfinalcase.entities.Role;
import com.works.definexfinalcase.repositories.AdminRepository;
import com.works.definexfinalcase.restControllers.AdminController;
import com.works.definexfinalcase.utils.REnum;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AdminServiceTest {


    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminController adminController;

    private Admin admin;
    private Optional<Admin> optionalAdmin;

    @Before
    public void setUp() {
        admin = new Admin();
        admin.setAdminName("ali");
        admin.setAdminSurname("bilmem");
        admin.setEmail("ali@example.com");
        admin.setEnabled(true);
        admin.setTokenExpired(true);
        admin.setPassword("Test123._");
        Role role= new Role();
      //  admin.set
        role.getAdmins();
        admin.setId(1);

        optionalAdmin = Optional.ofNullable(admin);
    }

    @Test
    public void testRegisterWhenAdminDoesNotExist() {
        // Mock the AdminRepository findByEmailEqualsIgnoreCase method to return an empty optional
        when(adminRepository.findByEmailEqualsIgnoreCase(admin.getEmail())).thenReturn(optionalAdmin);

        // Call the register method of the adminController
        ResponseEntity<Map<REnum,Object>> response = adminController.register(admin);

        // Assert that the response status is OK (200)
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Assert that the response Map contains the expected keys and values
        Map<REnum,Object> expectedResponse = new LinkedHashMap<>();
        expectedResponse.put(REnum.STATUS, true);
        expectedResponse.put(REnum.RESULT, admin);
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void testRegisterWhenAdminExists() {
        // Mock the AdminRepository findByEmailEqualsIgnoreCase method to return a non-empty optional
        when(adminRepository.findByEmailEqualsIgnoreCase(admin.getEmail())).thenReturn(Optional.of(admin));

        // Call the register method of the adminController
        ResponseEntity<Map<REnum,Object>> response = adminController.register(admin);

        // Assert that the response status is OK (200)
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Assert that the response Map contains the expected keys and values
        Map<REnum,Object> expectedResponse = new LinkedHashMap<>();
        expectedResponse.put(REnum.STATUS, false);
        expectedResponse.put(REnum.MESSAGE, "customer has already registered ");
        assertEquals(expectedResponse, response.getBody());
    }
}