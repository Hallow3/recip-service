package com.soft.recipservice.service;

import com.soft.recipservice.entities.Admin;
import com.soft.recipservice.entities.SubCategory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient("AUTHENTICATION-SERVICE")
public interface AdminServiceFetcher {

    @GetMapping("/authentication/admin/{id}")
    Admin findAdminById(@PathVariable(name = "id") int adminId);

    @GetMapping("/authentication/admin/profil/{id}")
    Admin findAdminPartById(@PathVariable(name = "id") int adminId);

    @GetMapping("/authentication/user")
    Admin findAdminByToken(@RequestParam(name = "token") String token);
}
