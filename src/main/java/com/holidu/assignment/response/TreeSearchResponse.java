package com.holidu.assignment.response;

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

    Integer totalSpecies;

    Integer distinctSpeciesCount;

    String searchResult;
}
