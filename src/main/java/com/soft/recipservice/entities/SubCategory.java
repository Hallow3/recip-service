package com.soft.recipservice.entities;

import jakarta.persistence.Column;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SubCategory {

    private int id;
    private String name;
    private String picture;
    private String description;
}
