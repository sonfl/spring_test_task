package com.sonfl.datahub.controllers;

import com.sonfl.datahub.api.model.MissionDTO;
import com.sonfl.datahub.services.MissionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/private/missions")
public class MissionController {
    private MissionService missionService;

    public MissionController(MissionService missionService) {
        this.missionService = missionService;
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public MissionDTO addMission(@RequestBody MissionDTO newMission) {
        return missionService.save(newMission);
    }

    @PutMapping("/{id}")
    public MissionDTO updateMission(@PathVariable Long id, @RequestBody MissionDTO newMission) {
        return missionService.update(id, newMission);
    }


    @DeleteMapping("/{id}")
    public void deleteMission(@PathVariable Long id) {
        missionService.deleteById(id);
    }

}
