package com.knkweb.yarnshop.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode(exclude = "products")
@Entity
public class Category extends BaseEntity{
    private String description;

    @Builder.Default
    @ManyToMany(mappedBy = "categories",fetch = FetchType.EAGER)
    private Set<Product> products = new HashSet<>();

}
