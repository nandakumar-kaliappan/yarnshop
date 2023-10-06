package com.knkweb.yarnshop.domain;

import lombok.*;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {"orders"})
@Builder
@Entity
public class Customer extends BaseEntity {


    @OneToOne(cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    User user;

    private String userName;
    private String customerName;

    @Embedded
    private Address address;

    private String phone;
    private String email;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<OrderHeader> orders = new LinkedHashSet<>();

   // @Version
    private Integer version;

    public void setUser(User user){
        if(user != null) {
            this.user = user;
            user.setCustomer(this);
        }
    }
}
