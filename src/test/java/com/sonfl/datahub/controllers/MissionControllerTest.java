package com.sonfl.datahub.controllers;

import com.sonfl.datahub.TestUtils;
import com.sonfl.datahub.api.model.MissionDTO;
import com.sonfl.datahub.models.ImageryType;
import com.sonfl.datahub.models.Mission;
import com.sonfl.datahub.orders.OrderingHistory;
import com.sonfl.datahub.orders.ProductOrder;
import com.sonfl.datahub.services.MissionService;
import com.sonfl.datahub.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jersey.JerseyProperties;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@WithMockUser(username = "manager", roles = "MANAGER")
class MissionControllerTest {

    private static final String NAME = "first";
    private static final long ID = 1L;
    private static final ImageryType IMAGERY_TYPE = ImageryType.HYPERSPECTRAL;
    private static Date START_DATE = null;
    private static Date FINISH_DATE = null;



    @MockBean
    MissionService missionService;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductService productService;

    @MockBean
    OrderingHistory orderingHistory;

    @MockBean
    ProductOrder productOrder;

    @BeforeEach
    void setUp() throws Exception {
        START_DATE = new SimpleDateFormat("yyyy-MM-dd").parse("1988-06-13");
        FINISH_DATE = new SimpleDateFormat("yyyy-MM-dd").parse("2000-07-13");

    }


    @Test
    void addMission() throws Exception {
        MissionDTO missionDTO = new MissionDTO();
        missionDTO.setName(NAME);
        missionDTO.setImageryType(IMAGERY_TYPE);
        missionDTO.setStartDate(START_DATE);
        missionDTO.setFinishDate(FINISH_DATE);

        when(missionService.save(any(MissionDTO.class))).thenReturn(missionDTO);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/private/missions/add")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtils.objectToJson(missionDTO))).andReturn();

        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());

        verify(missionService).save(any(MissionDTO.class));

        MissionDTO resultMissionDTO = TestUtils.jsonToObject(result.getResponse().getContentAsString(), MissionDTO.class);
        assertNotNull(resultMissionDTO);
        assertEquals(missionDTO.getName(), resultMissionDTO.getName());
    }

    @Test
    void updateMission() throws Exception{
        MissionDTO missionDTO = new MissionDTO();
        missionDTO.setName(NAME);
        missionDTO.setImageryType(IMAGERY_TYPE);
        missionDTO.setStartDate(START_DATE);
        missionDTO.setFinishDate(FINISH_DATE);

        when(missionService.update(any(Long.class), any(MissionDTO.class))).thenReturn(missionDTO);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/private/missions/{id}","1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(TestUtils.objectToJson(missionDTO))).andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

        verify(missionService).update(any(Long.class), any(MissionDTO.class));

        MissionDTO resultMissionDTO = TestUtils.jsonToObject(result.getResponse().getContentAsString(), MissionDTO.class);
        assertNotNull(resultMissionDTO);
        assertEquals(missionDTO.getName(), resultMissionDTO.getName());
    }

    @Test
    void deleteMission() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/private/missions/{id}","1")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }
}