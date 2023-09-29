package com.knkweb.yarnshop.domain;

import lombok.*;

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
public class Authority extends BaseEntity{

    @ManyToOne(cascade = CascadeType.PERSIST)
    private User user;

    private String roll;
}
