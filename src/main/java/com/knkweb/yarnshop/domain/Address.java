package com.knkweb.yarnshop.domain;


import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.Size;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class Address {
    @Size(min = 3, max = 45, message = "Address length must be between 3 and 255")
    private String address;
    @Size(min = 3, max = 7, message = "Zip code length must be between 3 and 7")
    private String zipCode;
}
