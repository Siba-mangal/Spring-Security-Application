package com.coding.SecurityApp.SecurityApplication.dto;


import com.coding.SecurityApp.SecurityApplication.entities.enums.Permission;
import com.coding.SecurityApp.SecurityApplication.entities.enums.Role;
import lombok.Data;

import java.util.Set;

@Data
public class SignUpDto {
    private String email;
    private String password;
    private String name;
    private Set<Role> roles;
    private Set<Permission> permissions;
}
