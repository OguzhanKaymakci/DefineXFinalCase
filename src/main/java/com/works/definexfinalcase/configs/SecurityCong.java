package com.works.definexfinalcase.configs;

import com.works.definexfinalcase.services.AdminService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityCong extends WebSecurityConfigurerAdapter {
    final JwtFilter jwtFilter;
    final AdminService adminService;

    public SecurityCong(JwtFilter jwtFilter, AdminService adminService) {
        this.jwtFilter = jwtFilter;
        this.adminService = adminService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(adminService).passwordEncoder(adminService.encoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests() //giriş rolleri ile çalış
                .antMatchers("/customer/register").permitAll() //hangi servis hangi rolle çalışcak emrini veriyoruz.
                .antMatchers("/admin/register").permitAll() //hangi servis hangi rolle çalışcak emrini veriyoruz.
                .antMatchers("/customer/list","/customer/update","/customer/delete").hasRole("admin")
                .antMatchers("/definexSystem/auth").permitAll()
                .antMatchers("/credit/apply").hasRole("customer")
                .antMatchers("/credit/listByIdAndBirthdate").hasRole("admin")
                .and()
                .csrf().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);  //jwt nin üreteceği sessionun oluşturulmasına izin veriyor

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

}
