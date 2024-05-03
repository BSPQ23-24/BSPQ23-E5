package com.RouteBus.server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.RouteBus.server.model.Route;

import java.util.List;
import java.util.Optional;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
	List<Route> findByBusesId(Long id);
	List<Route> findByStationsId(Long id);
	Optional<Route> findByName(String name);
}
