package com.tree.search.controller;

import com.tree.search.constant.TreeSearchApplicationConstant;
import com.tree.search.request.TreeSearchRequest;
import com.tree.search.response.TreeSearchResponse;
import com.tree.search.service.TreeSearchServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.math.BigDecimal;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TreeSearchControllerTest {

    MockMvc mockMvc;

    @InjectMocks
    TreeSearchController treeSearchController;

    @MockBean
    TreeSearchServiceImpl treeSearchServiceImpl;

    private final static String TREE_SEARCH_REQUEST_PAYLOAD = "{\n" +
            "  \"cartesianX\": 1100000,\n" +
            "  \"cartesianY\": 210000,\n" +
            "  \"searchRadiusInMeters\": 23000\n" +
            "}";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(treeSearchController)
                .build();
    }

    @Test
    public void testFindTreeSpeciesOk() throws Exception {
        when(treeSearchServiceImpl.isValidTreeSearchRequest(any(TreeSearchRequest.class))).thenReturn(true);
        when(treeSearchServiceImpl.findTreeSpecies(buildValidTreeSearchRequest())).thenReturn(buildTreeSearchResponseEntity());
        mockMvc.perform(post("/api/findTreeSpecies")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(TREE_SEARCH_REQUEST_PAYLOAD))
                .andExpect(status().isOk());
        verify(treeSearchServiceImpl, times(1)).isValidTreeSearchRequest(any(TreeSearchRequest.class));
        verify(treeSearchServiceImpl, times(1)).findTreeSpecies(any(TreeSearchRequest.class));
        verifyNoMoreInteractions(treeSearchServiceImpl);
    }

    @Test
    public void testFindTreeSpeciesBadRequest() throws Exception {
        when(treeSearchServiceImpl.isValidTreeSearchRequest(any(TreeSearchRequest.class))).thenReturn(false);
        when(treeSearchServiceImpl.findTreeSpecies(buildValidTreeSearchRequest())).thenReturn(buildTreeSearchResponseEntity());
        mockMvc.perform(post("/api/findTreeSpecies")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{}"))
                .andExpect(status().isBadRequest());
        verify(treeSearchServiceImpl, times(1)).isValidTreeSearchRequest(any(TreeSearchRequest.class));
        verify(treeSearchServiceImpl, times(0)).findTreeSpecies(any(TreeSearchRequest.class));
        verifyNoMoreInteractions(treeSearchServiceImpl);
    }

    private ResponseEntity<TreeSearchResponse> buildTreeSearchResponseEntity() {
        return new ResponseEntity<>(TreeSearchResponse.builder().
                distinctSpeciesCount(1)
                .searchOutcome(TreeSearchApplicationConstant.TREE_SPECIES_RETRIEVED)
                .totalSpeciesCount(1)
                .speciesSplit("Some Split")
                .build(),
                HttpStatus.OK);
    }

    private TreeSearchRequest buildValidTreeSearchRequest() {
        return TreeSearchRequest.builder()
                .cartesianX(new BigDecimal(1100000))
                .cartesianY(new BigDecimal(210000))
                .searchRadiusInMeters(new BigDecimal(23000))
                .build();
    }


}
