package com.holidu.assignment.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.holidu.assignment.dto.TreeData;
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
    public ResponseEntity<TreeSearchResponse> findTreeSpecies() {
        TreeSearchResponse treeSearchResponse = null;
        try {
            URL resource = TreeSearchServiceImpl.class.getResource("/nwxe-4ae8.json");
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            List<TreeData> treeDataList = objectMapper.readValue(
                    new File(String.valueOf(Paths.get(resource.toURI()).toFile())),
                    new TypeReference<List<TreeData>>() {
                    });

            BigDecimal givenCartesianX = BigDecimal.valueOf(1100000);
            BigDecimal givenCartesianY = BigDecimal.valueOf(210000);
            BigDecimal givenRadius = BigDecimal.valueOf(23000);
            BigDecimal feetToMeter = BigDecimal.valueOf(0.3048);

            Map<String, Integer> resultMap = new HashMap<>();
            treeDataList.forEach(treeData -> {
                if (Objects.nonNull(treeData)) {
                    countDistinctSpecies(treeData,
                            treeData.getXSp(),
                            treeData.getYSp(),
                            givenCartesianX,
                            givenCartesianY,
                            givenRadius,
                            feetToMeter,
                            resultMap);
                }
            });
            Integer noNameTreeCount = resultMap.get(null);
            resultMap.put("NoNameSpecies", noNameTreeCount);
            resultMap.remove(null);
            int totalTrees = resultMap.values().stream().mapToInt(treeCount -> treeCount).sum();

            String speciesCount = objectMapper.writeValueAsString(resultMap);

            treeSearchResponse = TreeSearchResponse.builder()
                    .speciesCount(speciesCount).distinctSpeciesCount(resultMap.size()).totalSpecies(totalTrees).build();
            System.out.print(treeSearchResponse.toString());

            return new ResponseEntity<>(treeSearchResponse, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(treeSearchResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    private static Map countDistinctSpecies(TreeData treeData,
                                            String xSp,
                                            String ySp,
                                            BigDecimal cartesianX,
                                            BigDecimal cartesianY,
                                            BigDecimal givenRadius,
                                            BigDecimal feetToMeter,
                                            Map<String, Integer> resultMap) {

        BigDecimal bigDecimalXsp = new BigDecimal(xSp);
        BigDecimal bigDecimalYsp = new BigDecimal(ySp);

        BigDecimal absX = cartesianX.subtract(bigDecimalXsp).abs();
        BigDecimal absY = cartesianY.subtract(bigDecimalYsp).abs();

        BigDecimal powX = absX.pow(2);
        BigDecimal powY = absY.pow(2);
        BigDecimal add = powX.add(powY);

        double relativeRadiusInFeet = Math.sqrt(add.doubleValue());

        BigDecimal relativeRadiusInMtr = BigDecimal.valueOf(relativeRadiusInFeet * feetToMeter.doubleValue());

        if (relativeRadiusInMtr.doubleValue() <= givenRadius.doubleValue()) {
            if (!resultMap.isEmpty() && resultMap.containsKey(treeData.getSpcCommon())) {
                Integer integer = resultMap.get(treeData.getSpcCommon());
                integer++;
                resultMap.put(treeData.getSpcCommon(), integer);
            } else {
                // Initialize Count with 1 when an specie found
                resultMap.put(treeData.getSpcCommon(), 1);
            }
        } else {
            //TODO Some more statistics can be collected for Analytics
        }

        return resultMap;
    }

}
