package com.soft.recipservice.entities;

import jakarta.persistence.Column;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Admin {
    private int id;
    private String firstName;
    private String lastName;
    private Date lastConnexion;
    private String email;
    private String phone;
    private String profile;
    private List<Role> roles;
}
