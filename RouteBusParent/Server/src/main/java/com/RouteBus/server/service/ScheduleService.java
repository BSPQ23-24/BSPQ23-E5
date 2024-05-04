package com.RouteBus.server.service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.springframework.stereotype.Service;
import com.RouteBus.server.dao.ScheduleRepository;
import com.RouteBus.server.model.Schedule;

@Service
public class ScheduleService {
	private final ScheduleRepository scheduleRepository;

	public ScheduleService(ScheduleRepository scheduleRepository) {
		this.scheduleRepository = scheduleRepository;
	}

	public enum ScheduleServiceResult {
		SUCCESS, NOT_FOUND, ERROR
	}

	public Schedule getScheduleById(String id) {
		return scheduleRepository.findById(id).orElse(null);
	}

	public Set<Schedule> getAllSchedules() {
		return new HashSet<Schedule>(scheduleRepository.findAll());
	}

	public ScheduleServiceResult createSchedule(Schedule schedule) {
		if (!scheduleRepository.findById(schedule.getId()).isPresent()) {
			Schedule savedSchedule = scheduleRepository.save(schedule);
			return savedSchedule != null ? ScheduleServiceResult.SUCCESS : ScheduleServiceResult.ERROR;
		}
		return ScheduleServiceResult.ERROR;
	}

	public ScheduleServiceResult updateSchedule(Schedule schedule) {
	    return scheduleRepository.findById(schedule.getId()).map(existingSchedule -> {
	        boolean updated = false;
	        if (!Objects.equals(existingSchedule.getArrivalTime(), schedule.getArrivalTime())) {
	            existingSchedule.setArrivalTime(schedule.getArrivalTime());
	            updated = true;
	        }
	        if (!Objects.equals(existingSchedule.getDepartureTime(), schedule.getDepartureTime())) {
	            existingSchedule.setDepartureTime(schedule.getDepartureTime());
	            updated = true;
	        }
	        if (!Objects.equals(existingSchedule.getRoute(), schedule.getRoute())) {
	            existingSchedule.setRoute(schedule.getRoute());
	            updated = true;
	        }
	        if (!Objects.equals(existingSchedule.getTickets(), schedule.getTickets())) {
	            existingSchedule.setTickets(schedule.getTickets());
	            updated = true;
	        }
	        if (updated) {
	            scheduleRepository.save(existingSchedule);
	        }
	        return ScheduleServiceResult.SUCCESS;
	    }).orElse(ScheduleServiceResult.NOT_FOUND);
	}


	public ScheduleServiceResult deleteSchedule(String id) {
		return scheduleRepository.findById(id).map(schedule -> {
			scheduleRepository.delete(schedule);
			return ScheduleServiceResult.SUCCESS;
		}).orElse(ScheduleServiceResult.NOT_FOUND);
	}
}
