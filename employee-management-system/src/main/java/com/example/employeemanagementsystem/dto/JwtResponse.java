package com.example.employeemanagementsystem.dto;

import com.example.employeemanagementsystem.entity.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class JwtResponse {
    private String jwtToken;
    private User user;

}
