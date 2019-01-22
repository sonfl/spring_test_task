package com.sonfl.datahub.api.mapper;

import com.sonfl.datahub.api.model.MissionDTO;
import com.sonfl.datahub.models.ImageryType;
import com.sonfl.datahub.models.Mission;
import com.sonfl.datahub.models.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class MissionMapperTest {

    public static final String NAME = "first";
    public static final long ID = 1L;
    public static final ImageryType IMAGERY_TYPE = ImageryType.HYPERSPECTRAL;
    public static  Date START_DATE = null;
    public static  Date FINISH_DATE = null;

    MissionMapper missionMapper = MissionMapper.INSTANCE;

    @BeforeEach
    void setUp() throws Exception{

        START_DATE = new SimpleDateFormat("yyyy-MM-dd").parse("1988-06-13");
        FINISH_DATE = new SimpleDateFormat("yyyy-MM-dd").parse("2000-07-13");
    }

    @org.junit.jupiter.api.Test
    void missionToMissionDTO() {
        Mission mission = new Mission();
        mission.setName(NAME);
        mission.setImageryType(IMAGERY_TYPE);
        mission.setStartDate(START_DATE);
        mission.setFinishDate(FINISH_DATE);

        MissionDTO missionDTO = missionMapper.missionToMissionDTO(mission);

        assertEquals(NAME, missionDTO.getName());
        assertEquals(IMAGERY_TYPE, missionDTO.getImageryType());
        assertEquals(START_DATE, missionDTO.getStartDate());
        assertEquals(FINISH_DATE, missionDTO.getFinishDate());

    }

    @org.junit.jupiter.api.Test
    void missionDTOToMission() {
        MissionDTO missionDTO = new MissionDTO();
        missionDTO.setName(NAME);
        missionDTO.setImageryType(IMAGERY_TYPE);
        missionDTO.setStartDate(START_DATE);
        missionDTO.setFinishDate(FINISH_DATE);

        Mission mission = missionMapper.missionDTOToMission(missionDTO);

        assertEquals(missionDTO.getName(), NAME);
        assertEquals(missionDTO.getImageryType(), IMAGERY_TYPE);
        assertEquals(missionDTO.getStartDate(), START_DATE);
        assertEquals(missionDTO.getFinishDate(), FINISH_DATE);
    }
}