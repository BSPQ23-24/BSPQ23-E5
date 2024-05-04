package com.RouteBus.server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.RouteBus.server.model.Route;
import java.util.List;
import java.util.Optional;

@Repository
public interface RouteRepository extends JpaRepository<Route, String> {
    List<Route> findByBusesLicensePlate(String licensePlate);
    List<Route> findByStationsName(String name);
    Optional<Route> findByName(String name);
}
