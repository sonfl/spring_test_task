package com.sonfl.datahub.api.mapper;

import com.sonfl.datahub.api.model.MissionDTO;
import com.sonfl.datahub.models.Mission;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MissionMapper {
    MissionMapper INSTANCE = Mappers.getMapper(MissionMapper.class);

    MissionDTO missionToMissionDTO(Mission mission);

    Mission missionDTOToMission(MissionDTO missionDTO);

}
