package com.holidu.assignment.response;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TreeSearchResponse {
    String speciesCount;
    Integer totalSpecies;
    Integer distinctSpeciesCount;
}
