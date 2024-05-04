package com.RouteBus.server.service;

import com.RouteBus.server.dao.NationalityRepository;
import com.RouteBus.server.model.Nationality;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NationalityService {

	private final NationalityRepository nationalityRepository;

	public NationalityService(NationalityRepository nationalityRepository) {
		this.nationalityRepository = nationalityRepository;
	}

	public List<Nationality> getAllNationalities() {
		return nationalityRepository.findAll();
	}
}
