package com.soft.recipservice.service;

import com.soft.recipservice.Exceptions.CustomizeException;
import com.soft.recipservice.entities.Recip;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RecipService {

    Recip addRecip(Recip recip, MultipartFile image, HttpServletRequest request) throws CustomizeException;

    public List<Recip> GetAllRecip();

    public Page<Recip> getAllRecipPage(int pageNo);

    public Recip updateRecip(Recip recip, MultipartFile image, HttpServletRequest request) throws CustomizeException;

    public void deleteRecip(int id) throws CustomizeException;

    public Page<Recip> findByNameContains(String name, int pageNumber);

    public Page<Recip> findBySubCategoryId(int id, int pageNo);

    public Recip upgradeRecip(int recipId, int StepId);

    public void activeRecip(int idRecipToActive);

    public Recip getRecipById(int id, HttpServletRequest request) throws CustomizeException;

    public Recip getRecipById(int id);

    Page<Recip> getRecipByUser(String token, int page);

    Page<Recip> getEnableRecipes(int pageNo);
}
