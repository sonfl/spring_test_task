package com.sonfl.datahub.repositories;

import com.sonfl.datahub.models.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionRepository extends JpaRepository<Mission, Long> {
    Mission findMissionByName(String name);
}
