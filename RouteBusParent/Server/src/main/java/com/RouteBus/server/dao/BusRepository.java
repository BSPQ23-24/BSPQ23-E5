package com.RouteBus.server.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.RouteBus.server.model.Bus;
import java.util.Optional;

@Repository
public interface BusRepository extends JpaRepository<Bus, Integer> {
}
