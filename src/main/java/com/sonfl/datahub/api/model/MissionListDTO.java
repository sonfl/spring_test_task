package com.sonfl.datahub.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MissionListDTO {

    List<MissionDTO> missions;
}
