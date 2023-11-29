package com.knkweb.yarnshop.command;

import com.knkweb.yarnshop.domain.Address;
import com.knkweb.yarnshop.domain.User;
import lombok.*;

import javax.persistence.Access;
import javax.persistence.Embedded;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerCommand {

    private Long id;
    private User user;
    private String userName;
    private String customerName;
    private String address;
    private String phone;
    private String email;
    private String message = "Enter customer details here(msg from command)";

}
