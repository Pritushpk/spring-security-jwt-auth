package com.example.employeemanagementsystem.controller;

import com.example.employeemanagementsystem.dto.JwtRequest;
import com.example.employeemanagementsystem.dto.JwtResponse;
import com.example.employeemanagementsystem.entity.User;
import com.example.employeemanagementsystem.security.JwtHelper;
import com.example.employeemanagementsystem.service.UserService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;
    @Autowired
    JwtHelper jwtHelper;

    @GetMapping("/current-user")
    public ResponseEntity<?> getCurrentLoginUser(Principal principal){
        // return user DTO
        return new ResponseEntity<>(userDetailsService.loadUserByUsername(principal.getName()),HttpStatus.OK);
    }

    @GetMapping("login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) throws BadRequestException {
        this.doAuthenticate(request.getEmail(),request.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = jwtHelper.generateToken(userDetails);

        User user=convertUserDetailsToUser(userDetails);

        JwtResponse response = JwtResponse.builder()
                .jwtToken(token)
                .user(user).build();

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) throws BadRequestException {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
                new UsernamePasswordAuthenticationToken(email,password);
        try {
            authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        }catch (Exception e){
            throw new BadRequestException("invalid username pswd!!!");
        }
    }

    private static User convertUserDetailsToUser(UserDetails userDetails){
        User user = new User();
        user.setUserName(userDetails.getUsername());
        user.setEmail(userDetails.getUsername());
        user.setPassword(userDetails.getPassword());
        return user;

    }
}
