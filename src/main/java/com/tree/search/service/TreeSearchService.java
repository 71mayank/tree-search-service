package com.tree.search.service;

import com.tree.search.request.TreeSearchRequest;
import com.tree.search.response.TreeSearchResponse;
import org.springframework.http.ResponseEntity;

public interface TreeSearchService {
    ResponseEntity<TreeSearchResponse> findTreeSpecies(TreeSearchRequest treeSearchRequest);
}
