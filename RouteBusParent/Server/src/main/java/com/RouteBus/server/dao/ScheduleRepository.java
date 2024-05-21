package com.RouteBus.server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.RouteBus.server.model.Schedule;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, String> {
    List<Schedule> findByRoute_Name(String routeName);
    Optional<Schedule> findByRoute_NameAndDepartureTime(String routeName, String departureTime);
}
