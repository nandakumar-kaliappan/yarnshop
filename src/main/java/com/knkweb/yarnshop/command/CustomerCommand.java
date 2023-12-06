package com.knkweb.yarnshop.command;

import com.knkweb.yarnshop.domain.Address;
import com.knkweb.yarnshop.domain.User;
import lombok.*;

import javax.persistence.Access;
import javax.persistence.Embedded;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerCommand {

    private Long id;
    private User user;
    private String userName;
    @NotBlank
    @Size(min = 3, max = 45, message = "Customer Name length must be between 3 and 45")
    private String customerName;
    private String address;
    @Size(min = 10, max = 10, message = "Phone number must have 10 digits")
    private String phone;
    @Email
    private String email;
    private String message = "Enter customer details here(msg from command)";

}
