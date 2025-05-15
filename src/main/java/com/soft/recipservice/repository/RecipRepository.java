package com.soft.recipservice.repository;

import com.soft.recipservice.entities.Recip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecipRepository extends JpaRepository<Recip, Integer> {

    public Page<Recip> findByNameContains(String name, Pageable pageable);

    public Page<Recip> findBySubCategoryId(int id, Pageable pageable);

    @Query("select r from Recip r")
    public Page<Recip> pageRecip(Pageable pageable);

    public Page<Recip> findByAdminId(int adminId, Pageable pageable);

    @Query("select r from Recip r where r.isEnable = true")
    Page<Recip> findAllEnables(Pageable pageable);
}
