package com.RouteBus.server.service;

import java.util.List;
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

	public List<Schedule> getAllSchedules() {
		return scheduleRepository.findAll();
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
			existingSchedule.setArrivalTime(schedule.getArrivalTime());
			existingSchedule.setDepartureTime(schedule.getDepartureTime());
			existingSchedule.setRoute(schedule.getRoute());
			existingSchedule.setTickets(schedule.getTickets());
			scheduleRepository.save(existingSchedule);
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
