package com.spring.bikroy.bikroy.dto.response;

import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseProfile {
    private String name;
    private String username;
    private String address;
    private String email;
    private int phoneNo;
}
