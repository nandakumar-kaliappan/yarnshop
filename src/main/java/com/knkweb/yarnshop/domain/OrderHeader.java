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
@ToString(exclude = {"orderLines","orderApproval"})
@EqualsAndHashCode(exclude = {"orderLines","orderApproval"})
@AttributeOverrides({
        @AttributeOverride(
                name = "billingAddress.address",
                column = @Column(name = "billing_address")
        ),
        @AttributeOverride(
                name = "billingAddress.zipCode",
                column = @Column(name = "billing_zip_code")
        ),
        @AttributeOverride(
                name = "shippingAddress.address",
                column = @Column(name = "shipping_address")
        ),
        @AttributeOverride(
                name = "shippingAddress.zipCode",
                column = @Column(name = "shipping_zip_code")
        )
})
@Entity
public class OrderHeader extends BaseEntity {

    
    private Integer levels;

    private Integer count;

    @Embedded
    private Address billingAddress;

    @Embedded
    private Address shippingAddress;



    private String orderStatus;

    @OneToMany(mappedBy = "orderHeader",
            cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private Set<OrderLine> orderLines;

    @ManyToOne
    private Customer customer;

    @OneToOne(cascade = {CascadeType.ALL}, mappedBy = "orderHeader")
    private OrderApproval orderApproval;

    public void setOrderApproval(OrderApproval orderApproval){
        this.orderApproval = orderApproval;
        orderApproval.setOrderHeader(this);
    }


    public void addOrderLine(OrderLine orderLine) {
        if(orderLines == null){
            orderLines = new HashSet<>();
        }
        orderLines.add(orderLine);
        orderLine.setOrderHeader(this);
    }
}
