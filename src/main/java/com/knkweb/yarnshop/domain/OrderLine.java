package com.knkweb.yarnshop.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode(exclude = "orderHeader")
@Entity
public class OrderLine extends BaseEntity {

    private int level;
    private String status;
    private Integer quantityOrdered;

    @ManyToOne
    private OrderHeader orderHeader;

    @ManyToOne
    private Product product;
}
