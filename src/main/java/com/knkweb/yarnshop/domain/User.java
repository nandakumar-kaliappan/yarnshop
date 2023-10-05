package com.knkweb.yarnshop.domain;

import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"authorities","customer"})
@ToString(exclude = {"authorities"})
@Entity
public class User extends BaseEntity{

    private String username;
    private String password;
    private boolean enabled;


    @OneToOne(cascade = {CascadeType.PERSIST})
    private Customer customer;

    @Builder.Default
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private List<Authority> authorities = new ArrayList<>();

    public void addAuthorities(Authority authority) {
        if(authorities==null){
            authorities = new ArrayList<>();
        }
        authority.setUser(this);
        this.authorities.add(authority);
    }

    public void addCustomer(Customer customer){
        this.customer  = customer;
        customer.setUser(this);
    }
}
