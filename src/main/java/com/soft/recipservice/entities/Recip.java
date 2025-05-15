package com.soft.recipservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Getter
@Setter
@Table(name = "recip")
public class Recip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private int id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @Column(name = "DESCRIPTION", nullable = false, length = 1000)
    private String description;

    @Column(name = "PICTURE", nullable = false)
    private String picture;

    @Column(name = "COOKINGTIME", nullable = false)
    private int cookingTime;

    @Column(name = "ISENABLE", nullable = false)
    private boolean isEnable;

    @Column(name = "NEEDS", nullable = false, length = 1000)
    private String needs;

    @Column(name = "SUBCATEGORYID", nullable = false)
    private int subCategoryId;

    @Transient
    private SubCategory subCategory;

    @Column(name = "ADMINID", nullable = false)
    private int adminId;

    @Transient
    private Admin admin;

}
