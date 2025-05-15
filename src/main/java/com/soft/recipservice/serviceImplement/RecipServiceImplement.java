package com.soft.recipservice.serviceImplement;

import com.soft.recipservice.Exceptions.CustomizeException;
import com.soft.recipservice.entities.Admin;
import com.soft.recipservice.entities.Recip;
import com.soft.recipservice.entities.Role;
import com.soft.recipservice.entities.SubCategory;
import com.soft.recipservice.repository.RecipRepository;
import com.soft.recipservice.service.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class RecipServiceImplement implements RecipService {

    @Autowired
    private RecipRepository recipRepository;

    @Autowired
    private AdminServiceFetcher adminServiceFetcher;

    @Autowired
    private StepServiceFetcher stepServiceFetcher;

    @Autowired
    private SubCategoryService subCategoryService;

    @Autowired
    private FlickrService flickrService;

    RecipServiceImplement (){}

    @Override
    public Recip addRecip(Recip recip, MultipartFile image, HttpServletRequest request) throws CustomizeException {
        File fichier = null;
        //récupération de l'admin qui fait l'ajout
        String token = request.getHeader("Authorization");
        Admin admin = new Admin();
        if(token.startsWith("Bearer"))
            token = token.substring(7);
        admin = adminServiceFetcher.findAdminByToken(token);
        if(admin.equals(null))
            return null;
        List<Role> roles = admin.getRoles();
        for(Role role: roles){
            if(role.getName().equals("ADMIN")){
                //gestion de limage et ajout
        try{
            //gestion de limage de la recette
            if(image.getSize() > 2000000 || image.getSize() < 0){
                log.info("your image size is out of bounds");
                return null;
            }
            //on extrait le nom et on place l'image dans le serveur
            String imageName = StringUtils.cleanPath(image.getOriginalFilename());
            if(!imageName.isEmpty() && imageName.trim() != " "){
                System.out.println("ajout de: "+imageName);
                fichier = new File("C:\\Users\\Halloween\\Documents\\workspace-spring-tool-suite-4-4.15.3.RELEASE\\Digest\\digest-front\\src\\assets\\images\\recipes\\"+imageName);
                fichier.createNewFile();
                FileOutputStream fout = new FileOutputStream(fichier);
                //InputStream inputStream = new FileInputStream(fichier);
                fout.write(image.getBytes());
                fout.close();
                recip.setPicture(imageName);
                //
                //flickrService.savePhoto(inputStream, imageName);
            }else{
                log.info("error while trying to get image name");
                return null;
            }
            SubCategory subCategory = subCategoryService.findSubCategoryById(recip.getSubCategoryId());
            recip.setSubCategory(subCategory);
            recip.setAdminId(admin.getId());
            recip.setAdmin(admin);
            recip.setCookingTime(0);
            System.out.println("enregistrement");
            return recipRepository.save(recip);
        }catch (Exception e){
            log.info("error while trying to add recipe: "+e);
            fichier.deleteOnExit();
            return null;
        }
            }
        }
        fichier.deleteOnExit();
        throw new CustomizeException("une erreur s'est produite lors de l'ajout");
    }

    @Override
    public List<Recip> GetAllRecip() {
        return recipRepository.findAll();
    }

    @Override
    public Page<Recip> getAllRecipPage(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 20);
        return recipRepository.pageRecip(pageable);
    }

    @Override
    public Recip updateRecip(Recip recip,MultipartFile image, HttpServletRequest request) throws CustomizeException {
        //récupération de l'admin qui fait l'ajout
        String token = request.getHeader("Authorization");
        Admin admin = new Admin();
        if(token.startsWith("Bearer"))
            token = token.substring(7);
        admin = adminServiceFetcher.findAdminByToken(token);
        if(admin.equals(null))
            return null;
        List<Role> roles = admin.getRoles();
        for(Role role: roles){
            if(role.getName().equals("ADMIN")) {
                //gestion de limage et ajout
                if (image != null) {

                    try {
                        Recip recip1 = recipRepository.findById(recip.getId()).get();
                        SubCategory subCategory = subCategoryService.findSubCategoryById(recip.getSubCategoryId());
                        //gestion de limage de la recette
                        if (image.getSize() > 2000000 || image.getSize() < 0) {
                            log.info("your image size is out of bounds");
                            return null;
                        }
                        //on extrait le nom et on place l'image dans le serveur
                        String imageName = StringUtils.cleanPath(image.getOriginalFilename());
                        if (!imageName.isEmpty() && imageName.trim() != " ") {
                            File fichier = new File("C:\\Users\\Halloween\\Documents\\workspace-spring-tool-suite-4-4.15.3.RELEASE\\Digest\\digest-front\\src\\assets\\images\\recipes\\" + imageName);
                            fichier.createNewFile();
                            FileOutputStream fout = new FileOutputStream(fichier);
                            fout.write(image.getBytes());
                            fout.close();
                            recip1.setPicture(imageName);
                            //
                        } else {
                            log.info("error while trying to get image name");
                            return null;
                        }
                        recip1.setSubCategory(subCategory);
                        recip1.setName(recip.getName());
                        recip1.setDescription(recip.getDescription());
                        recip1.setAdminId(admin.getId());
                        recip1.setAdmin(admin);
                        return recipRepository.save(recip1);
                    } catch (Exception e) {
                        log.info("error while trying to add recipe: " + e);
                        return null;
                    }
                } else {
                    SubCategory subCategory = subCategoryService.findSubCategoryById(recip.getSubCategoryId());
                    Recip recip1 = recipRepository.findById(recip.getId()).get();
                    recip1.setName(recip.getName());
                    recip1.setDescription(recip.getDescription());
                    recip1.setAdminId(admin.getId());
                    recip1.setAdmin(admin);
                    recip1.setSubCategory(subCategory);
                    recip1.setSubCategoryId(recip.getSubCategoryId());
                    return recipRepository.save(recip1);
                }
            }
        }
        throw new CustomizeException("impossible de recuperer ladmin");
    }

    @Override
    public void deleteRecip(int id) throws CustomizeException {
        Recip recip = recipRepository.findById(id).get();
        if(recip.getCookingTime() <= 0){
            recipRepository.deleteById(id);
        }else{
            throw new CustomizeException("la recette possède des étapes et ne peut être supprimée");
        }

    }

    @Override
    public Page<Recip> findByNameContains(String name, int pageNo) {

        Pageable pageable = PageRequest.of(pageNo,12);
        return recipRepository.findByNameContains(name, pageable);
    }

    @Override
    public Page<Recip> findBySubCategoryId(int id, int pageNo) {
        Pageable page = PageRequest.of(pageNo, 12);
        return recipRepository.findBySubCategoryId(id, page);
    }

    @Override
    public Recip upgradeRecip(int recipId, int stepId) {
        Recip recip = recipRepository.findById(recipId).get();
        if(stepServiceFetcher.stepExist(stepId)){
          recip.setCookingTime(recip.getCookingTime()+1);
          activeRecip(recipId);
          return recipRepository.save(recip);
        }
        log.info("an error occurred while trying to upgrade");
        return null;
    }

    @Override
    public void activeRecip(int idRecipToActive) {
        Recip recip = recipRepository.findById(idRecipToActive).get();
        //lets active by setting isEnable attribute to true
        recip.setEnable(true);
        recipRepository.save(recip);
    }

    @Override
    public Recip getRecipById(int id, HttpServletRequest request) throws CustomizeException {
        //récupération de l'admin qui fait l'ajout
        String token = request.getHeader("Authorization");
        Admin admin = new Admin();
        if(token.startsWith("Bearer"))
            token = token.substring(7);
        admin = adminServiceFetcher.findAdminByToken(token);
        if(admin.equals(null))
            return null;
        List<Role> roles = admin.getRoles();
        for(Role role: roles){
            if(role.getName().equals("ADMIN")){
                //gestion de limage et ajout
                try{
                    return recipRepository.findById(id).get();
                }catch (Exception e){
                    e.printStackTrace();
                    return null;
                }

            }
        }
        throw new CustomizeException("une erreur est survenue");
    }

    @Override
    public Recip getRecipById(int id) {
        Recip recip = recipRepository.findById(id).get();
        if(recip.isEnable()) {
            Admin admin = adminServiceFetcher.findAdminPartById(recip.getAdminId());
            recip.setAdmin(admin);
            return recip;
        }
        else
            return null;
    }

    @Override
    public Page<Recip> getRecipByUser(String token, int page){
        Admin admin = adminServiceFetcher.findAdminByToken(token);
        Pageable pageable = PageRequest.of(page, 20);
        return recipRepository.findByAdminId(admin.getId(),pageable);
    }

    @Override
    public Page<Recip> getEnableRecipes(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo,20);
        return recipRepository.findAllEnables(pageable);
    }
}
