package com.RouteBus.server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.RouteBus.server.model.Station;
import java.util.Optional;

@Repository
public interface StationRepository extends JpaRepository<Station, Integer> {

}
