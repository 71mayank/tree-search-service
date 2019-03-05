package com.holidu.assignment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class TreeData {
    @JsonProperty("address")
    String address;

    @JsonProperty("bbl")
    String bbl;

    @JsonProperty("bin")
    String bin;

    @JsonProperty("block_id")
    String blockId;

    @JsonProperty("boro_ct")
    String boroCt;

    @JsonProperty("borocode")
    String borocode;

    @JsonProperty("boroname")
    String boroname;

    @JsonProperty("brch_light")
    String brchLight;

    @JsonProperty("brch_other")
    String brchOther;

    @JsonProperty("brch_shoe")
    String brchShoe;

    @JsonProperty("cb_num")
    String cbNum;

    @JsonProperty("census_tract")
    String censusTract;

    @JsonProperty("cncldist")
    String cncldist;

    @JsonProperty("council_district")
    String councilDistrict;

    @JsonProperty("created_at")
    String createdAt;

    @JsonProperty("curb_loc")
    String curbLoc;

    @JsonProperty("guards")
    String guards;

    @JsonProperty("health")
    String health;

    @JsonProperty("latitude")
    String latitude;

    @JsonProperty("longitude")
    String longitude;

    @JsonProperty("nta")
    String nta;

    @JsonProperty("nta_name")
    String ntaName;

    @JsonProperty("problems")
    String problems;

    @JsonProperty("root_grate")
    String rootGrate;

    @JsonProperty("root_other")
    String rootOther;

    @JsonProperty("root_stone")
    String rootStone;

    @JsonProperty("sidewalk")
    String sidewalk;

    @JsonProperty("spc_common")
    String spcCommon;

    @JsonProperty("spc_latin")
    String spcLatin;

    @JsonProperty("st_assem")
    String stAssem;

    @JsonProperty("st_senate")
    String stSenate;

    @JsonProperty("state")
    String state;

    @JsonProperty("status")
    String status;

    @JsonProperty("steward")
    String steward;

    @JsonProperty("stump_diam")
    String stumpDiam;

    @JsonProperty("tree_dbh")
    String treeDbh;

    @JsonProperty("tree_id")
    String treeId;

    @JsonProperty("trnk_light")
    String trnkLight;

    @JsonProperty("trnk_other")
    String trnkOther;

    @JsonProperty("trunk_wire")
    String trunkWire;

    @JsonProperty("user_type")
    String userType;

    @JsonProperty("x_sp")
    String xSp;

    @JsonProperty("y_sp")
    String ySp;

    @JsonProperty("zip_city")
    String zipCity;

    @JsonProperty("zipcode")
    String zipcode;
    


}
