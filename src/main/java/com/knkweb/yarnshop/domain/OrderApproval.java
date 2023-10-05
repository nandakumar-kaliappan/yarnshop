package com.knkweb.yarnshop.domain;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@Entity
public class OrderApproval extends BaseEntity {

    @OneToOne
    private User approvedBy;

    @OneToOne
    private OrderHeader orderHeader;

}
