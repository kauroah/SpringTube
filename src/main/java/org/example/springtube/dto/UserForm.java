package org.example.springtube.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class UserForm {
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private Integer age;
    private String phone;

}
