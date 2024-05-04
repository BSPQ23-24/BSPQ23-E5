package com.RouteBus.server.dao;

import com.RouteBus.server.model.Nationality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NationalityRepository extends JpaRepository<Nationality, String> {
}
