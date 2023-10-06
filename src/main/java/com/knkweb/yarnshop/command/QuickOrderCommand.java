package com.knkweb.yarnshop.command;

import com.knkweb.yarnshop.domain.Customer;
import com.sun.istack.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QuickOrderCommand {
    private Long OrderHeaderId;
    private String itemsData;
    private String quantitiesData;
    private String unitsData;
    @Builder.Default
    private Integer levels = 0;
    private String orderStatus;
    private Long customerId;
}
