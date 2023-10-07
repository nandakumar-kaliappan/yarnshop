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
@ToString(exclude = "orderHeader")
@Entity
public class OrderLine extends BaseEntity {

    private Integer level;
    private String status;
    private Integer quantityOrdered;
    private String unit;

    @ManyToOne
    private OrderHeader orderHeader;

    @ManyToOne
    private Product product;
}
