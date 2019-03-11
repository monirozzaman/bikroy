package com.spring.bikroy.bikroy.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class RegistrationRequest {
    private String name;
    private String username;
    private String address;
    private String email;
    private String password;
}
