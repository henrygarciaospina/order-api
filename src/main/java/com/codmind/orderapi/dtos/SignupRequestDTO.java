package com.codmind.orderapi.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDTO {
    private String username;
    private String password;
}
