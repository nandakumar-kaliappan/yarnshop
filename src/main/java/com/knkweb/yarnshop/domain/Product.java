package com.knkweb.yarnshop.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@Entity
public class Product extends BaseEntity {
    private String colour;
    private String description;
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "product_category",
            inverseJoinColumns = @JoinColumn(name = "category_id"),
             joinColumns= @JoinColumn(name = "product_id"))
    private Set<Category> categories;

    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;


}
