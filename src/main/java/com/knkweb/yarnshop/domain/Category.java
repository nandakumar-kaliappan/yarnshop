package com.knkweb.yarnshop.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
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
    @Size(min = 1, max = 100, message = "description length must be between 3 and 100")
    private String description;

    @Builder.Default
    @ManyToMany(mappedBy = "categories",fetch = FetchType.EAGER)
    private Set<Product> products = new HashSet<>();

}
