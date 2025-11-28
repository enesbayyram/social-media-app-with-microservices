package com.enesbayram.sm_security_manager.config;

import com.enesbayram.sm_core.base.enums.MessageType;
import com.enesbayram.sm_core.base.exception.BaseException;
import com.enesbayram.sm_core.base.exception.ErrorMessage;
import com.enesbayram.sm_core.base.model.UserDef;
import com.enesbayram.sm_security_manager.repository.UserDefRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    private final UserDefRepository userDefRepository;

    @Bean
    public UserDetailsService userDetailsService(){
       return new UserDetailsService() {
           @Override
           public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
               Optional<UserDef> optional = userDefRepository.findByUsername(username);
               if(optional.isEmpty()){
                   throw new BaseException(new ErrorMessage(MessageType.USER_NOT_FOUND , username));
               }
              return optional.get();
           }
       };
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
