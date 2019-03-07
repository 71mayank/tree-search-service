package com.holidu.assignment.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.holidu.assignment.TreeSearchApplicationConstant;
import com.holidu.assignment.dto.TreeData;
import com.holidu.assignment.request.TreeSearchRequest;
import com.holidu.assignment.response.TreeSearchResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class TreeSearchServiceImpl implements TreeSearchService {

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public ResponseEntity<TreeSearchResponse> findTreeSpecies(TreeSearchRequest treeSearchRequest) {
        TreeSearchResponse treeSearchResponse = null;
        try {
            URL resource = TreeSearchServiceImpl.class.getResource(TreeSearchApplicationConstant.TREE_DATA_SET);
            List<TreeData> treeDataList = objectMapper.readValue(new File(String.valueOf(Paths.get(resource.toURI()).toFile())), new TypeReference<List<TreeData>>() {
            });

            Map<String, Integer> resultMap = scanTreeSpeciesAndPrepareTreeSplit(treeDataList, treeSearchRequest, new HashMap<>());

            if (resultMap.size() == 0) {
                treeSearchResponse = buildTreeSearchResponse(null, 0, 0, TreeSearchApplicationConstant.TREE_NOT_FOUND);
            } else {
                // Find Trees without name
                Integer noNameTreeCount = resultMap.get(null);
                // Give NoName
                resultMap.put(TreeSearchApplicationConstant.TREE_NO_NAME, noNameTreeCount);
                // Remove null key from the map
                resultMap.remove(null);
                treeSearchResponse = buildTreeSearchResponse(objectMapper.writeValueAsString(resultMap), resultMap.size(), resultMap.values().stream().mapToInt(treeCount -> treeCount).sum(),
                        TreeSearchApplicationConstant.TREE_SPECIES_RETRIEVED);
            }

            return new ResponseEntity<>(treeSearchResponse, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(treeSearchResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private TreeSearchResponse buildTreeSearchResponse(String speciesSplit,
                                                       Integer distinctSpeciesCount,
                                                       Integer totalSpecies,
                                                       String searchResult) {
        return TreeSearchResponse.builder()
                .speciesSplit(speciesSplit)
                .distinctSpeciesCount(distinctSpeciesCount)
                .totalSpecies(totalSpecies)
                .searchResult(searchResult).build();
    }

    private Map scanTreeSpeciesAndPrepareTreeSplit(List<TreeData> treeDataList, TreeSearchRequest treeSearchRequest, Map resultMap) {
        treeDataList.forEach(treeData -> {
            if (Objects.nonNull(treeData)) {
                    countDistinctSpecies(treeData,
                                        treeData.getXSp(),
                                        treeData.getYSp(),
                                        treeSearchRequest.getCartesianX(),
                                        treeSearchRequest.getCartesianY(),
                                        treeSearchRequest.getSearchRadiusInMeters(),
                                        new BigDecimal(TreeSearchApplicationConstant.FEET_TO_METER_CONVERSION_FACTOR),
                                        resultMap);
                    }
            });
        return resultMap;
    }


    private Map countDistinctSpecies(TreeData treeData,
                                     String xSp,
                                     String ySp,
                                     BigDecimal cartesianX,
                                     BigDecimal cartesianY,
                                     BigDecimal givenRadiusInMtr,
                                     BigDecimal feetToMeter,
                                     Map<String, Integer> resultMap) {
        BigDecimal absX = cartesianX.subtract(new BigDecimal(xSp)).abs();
        BigDecimal absY = cartesianY.subtract(new BigDecimal(ySp)).abs();

        BigDecimal powX = absX.pow(TreeSearchApplicationConstant.POWER_OF_TWO);
        BigDecimal powY = absY.pow(TreeSearchApplicationConstant.POWER_OF_TWO);

        double relativeRadiusInFeet = Math.sqrt(powX.add(powY).doubleValue());

        BigDecimal relativeRadiusInMtr = BigDecimal.valueOf(relativeRadiusInFeet * feetToMeter.doubleValue());

        if (relativeRadiusInMtr.doubleValue() <= givenRadiusInMtr.doubleValue()) {
            if (!resultMap.isEmpty() && resultMap.containsKey(treeData.getSpcCommon())) {
                Integer integer = resultMap.get(treeData.getSpcCommon());
                integer++;
                resultMap.put(treeData.getSpcCommon(), integer);
            } else {
                resultMap.put(treeData.getSpcCommon(), TreeSearchApplicationConstant.INITIAL_TREE_COUNT);
            }
        }
        return resultMap;
    }

    public boolean isValidTreeSearchRequest(TreeSearchRequest treeSearchRequest) {
        boolean isValid = true;
        if (Objects.isNull(treeSearchRequest) ||
                treeSearchRequest.getCartesianX() == null ||
                treeSearchRequest.getCartesianY() == null ||
                treeSearchRequest.getSearchRadiusInMeters() == null
        ) {
            isValid = false;
        }
        return isValid;
    }

}
