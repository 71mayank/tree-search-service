package com.holidu.assignment.service;

import com.holidu.assignment.request.TreeSearchRequest;
import com.holidu.assignment.response.TreeSearchResponse;
import org.springframework.http.ResponseEntity;

public interface TreeSearchService {
    ResponseEntity<TreeSearchResponse> findTreeSpecies(TreeSearchRequest treeSearchRequest);
}
