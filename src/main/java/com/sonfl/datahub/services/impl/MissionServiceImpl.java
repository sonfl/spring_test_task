package com.sonfl.datahub.services.impl;

import com.sonfl.datahub.api.mapper.MissionMapper;
import com.sonfl.datahub.api.model.MissionDTO;
import com.sonfl.datahub.models.Mission;
import com.sonfl.datahub.repositories.MissionRepository;
import com.sonfl.datahub.services.MissionService;
import org.springframework.stereotype.Service;

@Service
public class MissionServiceImpl implements MissionService {
    private MissionRepository missionRepository;
    private MissionMapper missionMapper;

    public MissionServiceImpl(MissionRepository missionRepository, MissionMapper missionMapper) {
        this.missionRepository = missionRepository;
        this.missionMapper = missionMapper;
    }

    @Override
    public MissionDTO save(MissionDTO missionDTO) {
        Mission mission = missionMapper.missionDTOToMission(missionDTO);
        return missionMapper.missionToMissionDTO(missionRepository.save(mission));
    }

    @Override
    public void deleteById(Long id) {
        missionRepository.deleteById(id);
    }

    @Override
    public MissionDTO update(Long id, MissionDTO missionDTO) {
        Mission mission = missionMapper.missionDTOToMission(missionDTO);
        return missionMapper.missionToMissionDTO(
                missionRepository.findById(id)
                        .map(e -> {
                            e.setName(mission.getName());
                            e.setFinishDate(mission.getFinishDate());
                            e.setImageryType(mission.getImageryType());
                            e.setProducts(mission.getProducts());
                            e.setStartDate(mission.getStartDate());
                            return missionRepository.save(e);
                        }).get()
        );
    }

    @Override
    public Mission getMissionByName(String name) {
        return missionRepository.findMissionByName(name);
    }
}
