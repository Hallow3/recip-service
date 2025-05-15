package com.soft.recipservice.service;

import com.soft.recipservice.entities.SubCategory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@FeignClient("CATEGORY-SERVICE")
public interface SubCategoryService {

    @GetMapping("/category/subcategory/get/{id}")
    SubCategory findSubCategoryById(@PathVariable(name = "id") int subCategoryId);
}
