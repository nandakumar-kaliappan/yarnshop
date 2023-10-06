package com.knkweb.yarnshop.command;

import com.sun.istack.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QuickOrderCommand {
    private String itemsData;
    private String quantitiesData;
    private String unitsData;
}
