package com.knkweb.yarnshop.domain;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
@Entity
public class Authority extends BaseEntity implements GrantedAuthority {

    @ManyToOne
    private User user;
    private String role;

    @Override
    public String getAuthority() {
        return role;
    }
}
