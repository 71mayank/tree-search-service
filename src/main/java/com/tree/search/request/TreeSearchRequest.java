package com.tree.search.request;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TreeSearchRequest {
    BigDecimal cartesianX;
    BigDecimal cartesianY;
    BigDecimal searchRadiusInMeters;
}
