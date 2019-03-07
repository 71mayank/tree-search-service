package com.tree.search.response;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class TreeSearchResponse {

    @JsonRawValue
    String speciesSplit;

    Integer totalSpeciesCount;

    Integer distinctSpeciesCount;

    String searchOutcome;
}
