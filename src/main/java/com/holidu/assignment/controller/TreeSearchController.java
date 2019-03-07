package com.holidu.assignment.controller;

import com.holidu.assignment.TreeSearchApplicationConstant;
import com.holidu.assignment.request.TreeSearchRequest;
import com.holidu.assignment.response.TreeSearchResponse;
import com.holidu.assignment.service.TreeSearchServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("api")
@Api(value = "Tree Search Service ", description = "Operations related to tree search")
public class TreeSearchController {

    @Autowired
    TreeSearchServiceImpl treeSearchServiceImpl;

    @ApiOperation(value = "Search tree species in cartesian plane with given radius in meters ", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully searched tree species"),
            @ApiResponse(code = 403, message = "Access to the search tree you were trying is forbidden"),
            @ApiResponse(code = 404, message = "The tree search you were trying to reach is not found")
    })
    @PostMapping(value = "/findTreeSpecies", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TreeSearchResponse> findTreeSpecies(@RequestBody TreeSearchRequest treeSearchRequest) {
        if(treeSearchServiceImpl.isValidTreeSearchRequest(treeSearchRequest)) {
            return treeSearchServiceImpl.findTreeSpecies(treeSearchRequest);
        }else{
            return new ResponseEntity<>(TreeSearchResponse.builder().searchOutcome(TreeSearchApplicationConstant.INVALID_INPUT).build(), HttpStatus.BAD_REQUEST);
        }
    }

}
