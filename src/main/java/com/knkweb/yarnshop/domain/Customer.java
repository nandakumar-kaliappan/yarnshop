package com.knkweb.yarnshop.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {"orders"})
@ToString(exclude = {"orders","User"})
@Builder
@Entity
public class Customer extends BaseEntity {


    @OneToOne(cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private User user;

    private String userName;
    @NotBlank(message = "CustomerName can't be blank")
    @Size(min = 3, max = 45, message = "Customer Name length must be between 3 and 45")
    private String customerName;

    @Embedded
    @Valid
    private Address address;

    @Size(min = 10, max = 10, message = "Phone number must be 10 digit")
    private String phone;

    @Email
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
