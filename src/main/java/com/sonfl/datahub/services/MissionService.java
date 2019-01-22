package com.sonfl.datahub.services;

import com.sonfl.datahub.api.model.MissionDTO;
import com.sonfl.datahub.models.Mission;

public interface MissionService {
    MissionDTO save(MissionDTO missionDTO);

    void deleteById(Long id);

    MissionDTO update(Long id, MissionDTO missionDTO);

    Mission getMissionByName(String name);
}
