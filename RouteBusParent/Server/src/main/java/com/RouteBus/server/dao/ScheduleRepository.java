package com.RouteBus.server.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.RouteBus.server.model.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>{
}
