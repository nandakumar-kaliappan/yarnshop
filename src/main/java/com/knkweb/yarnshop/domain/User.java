package com.knkweb.yarnshop.domain;

import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @Builder.Default
    private boolean enabled = true;


    @OneToOne
    private Customer customer;

    @Builder.Default
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private Set<Authority> authorities = new HashSet<>();

    public void addAuthorities(Authority authority) {
        if(authorities==null){
            authorities = new HashSet<>();
        }
        authority.setUser(this);
        this.authorities.add(authority);
    }

}
