package com.tree.search.service;

import com.tree.search.constant.TreeSearchApplicationConstant;
import com.tree.search.dto.TreeData;
import com.tree.search.request.TreeSearchRequest;
import com.tree.search.response.TreeSearchResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TreeSearchServiceImplTest {

    @Autowired
    TreeSearchServiceImpl treeSearchServiceImpl;

    @Test
    public void testFindTreeSpeciesPositive() {
        ResponseEntity<TreeSearchResponse> treeSpecies = treeSearchServiceImpl.findTreeSpecies(buildValidTreeSearchRequest());
        TreeSearchResponse treeSearchResponse = treeSpecies.getBody();
        assertThat(treeSpecies.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(treeSpecies.getBody()).isNotNull();
        assertThat(treeSearchResponse.getDistinctSpeciesCount()).isGreaterThan(0);
        assertThat(treeSearchResponse.getTotalSpeciesCount()).isGreaterThan(0);
        assertThat(treeSearchResponse.getSpeciesSplit().length()).isGreaterThan(0);
        assertThat(treeSearchResponse.getSearchOutcome()).isEqualTo(TreeSearchApplicationConstant.TREE_SPECIES_RETRIEVED);
    }

    @Test
    public void testFindTreeSpeciesInternalNegative() {
        ResponseEntity<TreeSearchResponse> treeSpecies = treeSearchServiceImpl.findTreeSpecies(buildInValidTreeSearchRequest());
        TreeSearchResponse treeSearchResponse = treeSpecies.getBody();
        assertThat(treeSearchResponse).isNotNull();
        assertThat(treeSpecies.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(treeSearchResponse.getDistinctSpeciesCount()).isEqualTo(0);
        assertThat(treeSearchResponse.getTotalSpeciesCount()).isEqualTo(0);
        assertThat(treeSearchResponse.getSpeciesSplit()).isEqualTo(null);
        assertThat(treeSearchResponse.getSearchOutcome()).isEqualTo(TreeSearchApplicationConstant.TREE_NOT_FOUND);
    }

    @Test
    public void testValidTreeSearchRequest() {
        boolean validTreeSearchRequest = treeSearchServiceImpl.isValidTreeSearchRequest(buildValidTreeSearchRequest());
        assertThat(validTreeSearchRequest).isNotNull();
        assertThat(validTreeSearchRequest).isTrue();
    }

    @Test
    public void testInValidTreeSearchRequest() {
        boolean validTreeSearchRequest = treeSearchServiceImpl.isValidTreeSearchRequest(buildInValidTreeSearchRequest());
        assertThat(validTreeSearchRequest).isNotNull();
        assertThat(validTreeSearchRequest).isFalse();
    }

    @Test
    public void testScanTreeSpeciesAndPrepareTreeSplitSuccess() {
        Map map = treeSearchServiceImpl.scanTreeSpeciesAndPrepareTreeSplit(buildValidTreeDataList(), buildValidTreeSearchRequest(), new HashMap<>());
        assertThat(map).isNotNull();
        assertThat(map.size()).isEqualTo(1);
    }

    @Test(expected = NumberFormatException.class)
    public void testScanTreeSpeciesAndPrepareTreeSplitFailure() {
        treeSearchServiceImpl.scanTreeSpeciesAndPrepareTreeSplit(buildInValidTreeDataList(), buildValidTreeSearchRequest(), new HashMap<>());
    }

    @Test
    public void testCountDistinctSpecies(){
        Map map = treeSearchServiceImpl.countDistinctSpecies(buildTreeData(),
                "1032564.664",
                "217592.9651",
                new BigDecimal("1100000"),
                new BigDecimal("210000"),
                new BigDecimal("23000"),
                new BigDecimal(TreeSearchApplicationConstant.FEET_TO_METER_CONVERSION_FACTOR),
                new HashMap<>());
        assertThat(map).isNotNull();
        assertThat(map.size()).isEqualTo(1);
    }

    @Test
    public void testCountDistinctSpeciesFailure(){
        Map map = treeSearchServiceImpl.countDistinctSpecies(buildTreeData(),
                null,
                "217592.9651",
                new BigDecimal("1100000"),
                new BigDecimal("210000"),
                new BigDecimal("23000"),
                new BigDecimal(TreeSearchApplicationConstant.FEET_TO_METER_CONVERSION_FACTOR),
                new HashMap<>());
        assertThat(map).isNull();
    }

    private TreeData buildTreeData() {
        return TreeData.builder().xSp("1032564.664").ySp("217592.9651").spcCommon("Norway maple").build();
    }

    private List<TreeData> buildValidTreeDataList() {
        ArrayList<TreeData> treeDataList = new ArrayList<>();
        treeDataList.add(TreeData.builder().xSp("1032564.664").ySp("217592.9651").spcCommon("Norway maple").build());
        return treeDataList;
    }

    private List<TreeData> buildInValidTreeDataList() {
        ArrayList<TreeData> treeDataList = new ArrayList<>();
        treeDataList.add(TreeData.builder().xSp("").ySp("").spcCommon("Norway maple").build());
        return treeDataList;
    }

    private TreeSearchRequest buildValidTreeSearchRequest() {
        return TreeSearchRequest.builder()
                .cartesianX(new BigDecimal(1100000))
                .cartesianY(new BigDecimal(210000))
                .searchRadiusInMeters(new BigDecimal(23000))
                .build();
    }

    private TreeSearchRequest buildInValidTreeSearchRequest() {
        return TreeSearchRequest.builder().build();
    }
}
