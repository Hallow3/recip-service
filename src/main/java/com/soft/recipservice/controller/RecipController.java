package com.soft.recipservice.controller;

import com.soft.recipservice.Exceptions.CustomizeException;
import com.soft.recipservice.entities.Admin;
import com.soft.recipservice.entities.Recip;
import com.soft.recipservice.service.AdminServiceFetcher;
import com.soft.recipservice.service.RecipService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/recip")
public class RecipController {

    @Autowired
    private RecipService recipService;

    @Autowired
    private AdminServiceFetcher adminServiceFetcher;
//
//    @GetMapping("/all")
//    public ResponseEntity<List<Recip>> getAllRecipes(){
//        return new ResponseEntity<>(recipService.GetAllRecip(), HttpStatus.OK);
//    }

    @GetMapping("/all/{pageNo}")
    public Page<Recip> getAllRecipesPages(@PathVariable(name = "pageNo") int page){
        return recipService.getAllRecipPage(page);
    }

    @GetMapping("/enable/{pageNo}")
    public Page<Recip> getAllEnableRecipes(@PathVariable(name = "pageNo") int page){
        return recipService.getEnableRecipes(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recip> getRecipById(@PathVariable(name = "id") int id, HttpServletRequest request) throws CustomizeException {
        return new ResponseEntity<>(recipService.getRecipById(id, request), HttpStatus.OK);
    }

    @GetMapping("/unique/{id}")
    public ResponseEntity<Recip> getUserRecipById(@PathVariable(name = "id") int id){
        return new ResponseEntity<>(recipService.getRecipById(id), HttpStatus.OK);
    }

    @GetMapping("/user/{token}/{pageNo}")
    public Page<Recip> getAllUserRecip(@PathVariable(name = "token") String token, @PathVariable(name = "pageNo") int page){
        return recipService.getRecipByUser(token, page);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Recip> updateRecip(@RequestPart("recip") Recip recip, @RequestPart(value = "image", required = false) MultipartFile file, HttpServletRequest request) throws CustomizeException {
        return new ResponseEntity<>(recipService.updateRecip(recip, file, request), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Recip> createRecip(@RequestPart("recip") Recip recip, @RequestPart("image") MultipartFile file, HttpServletRequest request) throws CustomizeException {
        return new ResponseEntity<>(recipService.addRecip(recip, file, request), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Recip> delete(@PathVariable(name = "id") int id, HttpServletRequest request) throws CustomizeException {
        try {
            Recip recip = recipService.getRecipById(id, request);
            recipService.deleteRecip(id);
            return new ResponseEntity<>(recip,HttpStatus.OK);
        }catch (Exception e){
            throw new CustomizeException("impossible de supprimer la recette");
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Page<Recip>> getByName(@PathVariable("name") String name, @RequestParam(name = "page") int pageNo){
        return new ResponseEntity<>(recipService.findByNameContains(name, pageNo), HttpStatus.OK);
    }

    @GetMapping("/subcategory/{id}")
    public ResponseEntity<Page<Recip>> getBySubCategory(@PathVariable("id") int id, @RequestParam(name = "page") int page){
        return new ResponseEntity<>(recipService.findBySubCategoryId(id, page), HttpStatus.OK);
    }

    @PostMapping("/upgrade")
    public Recip upgradeRecip(@RequestParam(name = "idrecip") int recipId, @RequestParam(name = "idstep") int stepId){
        return recipService.upgradeRecip(recipId, stepId);
    }
}
