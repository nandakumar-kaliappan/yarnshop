package com.knkweb.yarnshop.domain;


import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"authorities","customer"})
@ToString(exclude = {"authorities","customer"})
@Entity
public class User extends BaseEntity implements UserDetails {

    @Size(min = 3, max = 255, message = "User Name length must be between 3 and 255")
    @NotBlank
    private String username;

    @Size(min = 1, max = 255, message = "Customer Name length must be between 3 and 45")
    @NotBlank
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return authorities;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
