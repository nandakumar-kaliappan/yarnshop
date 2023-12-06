package com.knkweb.yarnshop.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
public class Product extends BaseEntity {
    @NotBlank
    @Size(min = 3, max = 45, message = "Customer Name length must be between 3 and 45")
    private String colour;
    private String description;

    @Builder.Default
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "product_category",
            inverseJoinColumns = @JoinColumn(name = "category_id"),
             joinColumns= @JoinColumn(name = "product_id"))
    private Set<Category> categories = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;


    public void addCategory(Category category) {
        if(this.getCategories()==null){
            this.setCategories(new HashSet<>());
        }
        if(category.getProducts() == null){
            this.setCategories(new HashSet<>());
        }
        this.getCategories().add(category);
        category.getProducts().add(this);
    }
}
