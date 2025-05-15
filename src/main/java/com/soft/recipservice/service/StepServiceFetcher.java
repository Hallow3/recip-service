package com.soft.recipservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("STEP-SERVICE")
public interface StepServiceFetcher {

    @GetMapping("/step/exist/{id}")
    public boolean stepExist(@PathVariable(name = "id") int id);

}
