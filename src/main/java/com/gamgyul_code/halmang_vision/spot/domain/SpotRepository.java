package com.gamgyul_code.halmang_vision.spot.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpotRepository extends JpaRepository<Spot, Long> {

    Optional<Spot> findByName(String name);
}
