package com.holidu.assignment.request;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class TreeSearchRequest {
    BigDecimal cartesianX;
    BigDecimal cartesianY;
    BigDecimal searchRadiusInMeters;
}
