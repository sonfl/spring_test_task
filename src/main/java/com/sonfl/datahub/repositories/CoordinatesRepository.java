package com.sonfl.datahub.repositories;

import com.sonfl.datahub.models.Coordinates;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoordinatesRepository extends JpaRepository<Coordinates, Long> {
}
