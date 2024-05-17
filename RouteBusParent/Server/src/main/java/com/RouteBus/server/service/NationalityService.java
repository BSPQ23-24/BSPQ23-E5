package com.RouteBus.server.service;

import com.RouteBus.server.dao.NationalityRepository;
import com.RouteBus.server.model.Nationality;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class NationalityService {

	private final NationalityRepository nationalityRepository;

	public NationalityService(NationalityRepository nationalityRepository) {
		this.nationalityRepository = nationalityRepository;
	}

	public Set<Nationality> getAllNationalities() {
		return new HashSet<Nationality>(nationalityRepository.findAll());
	}
}
