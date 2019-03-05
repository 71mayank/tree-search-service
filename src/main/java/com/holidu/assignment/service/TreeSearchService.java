package com.holidu.assignment.service;

import com.holidu.assignment.response.TreeSearchResponse;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;

public interface TreeSearchService {
    ResponseEntity<TreeSearchResponse> findTreeSpecies();
}
