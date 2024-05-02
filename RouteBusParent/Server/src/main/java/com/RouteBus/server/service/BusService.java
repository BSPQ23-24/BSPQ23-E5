package com.RouteBus.server.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.RouteBus.server.dao.BusRepository;
import com.RouteBus.server.dao.RouteRepository;
import com.RouteBus.server.dao.StationRepository;
import com.RouteBus.server.dao.TicketRepository;
import com.RouteBus.server.dao.UserRepository;
import com.RouteBus.server.model.Bus;
import com.RouteBus.server.model.Route;
import com.RouteBus.server.model.Station;
import com.RouteBus.server.model.Ticket;
import com.RouteBus.server.model.User;
import com.RouteBus.server.service.UserService.UserServiceResult;

@Service
public class BusService {
	private BusRepository busRepository;
	private RouteRepository routeRepository;
	private StationRepository stationRepository;
	private TicketRepository ticketRepository;
	
	public BusService(BusRepository busRepository,RouteRepository routeRepository, StationRepository stationRepository, TicketRepository ticketRepository) {
        this.busRepository = busRepository;
        this.routeRepository=routeRepository;
        this.stationRepository=stationRepository;
        this.ticketRepository=ticketRepository;
    }
    public enum BusServiceResult {
		SUCCESS,
		FAIL;
	}

    /**  Returning User information */
    public Bus getBusById(int id) {
    	Optional<Bus> result = busRepository.findById(id);
    	return result.orElse(null);
    }

    
    public List<Bus> getAllBuses() {
        return busRepository.findAll();
    }

    /** Creating a New User */
	public BusServiceResult createBus(Bus bus) {
		Optional<Bus> result = busRepository.findById(bus.getId());
		
		if (result.isEmpty()) {
			Bus savedBus = busRepository.save(bus);
			
			if (savedBus != null) {
				return BusServiceResult.SUCCESS;
			}
		}
		
		return BusServiceResult.FAIL;
	}    
   
    /** Update an Existing User */
    public BusServiceResult updateBus(Bus bus, int id) {
    	Optional<Bus> result = busRepository.findById(id);
		
		if (!result.isEmpty()) {
			Bus updatedBus = result.get();
			
			updatedBus.setCapacity(bus.getCapacity());
			updatedBus.setDriver(bus.getDriver());

			busRepository.save(updatedBus);
			
			if (!busRepository.findById(id).isEmpty()) {
				return BusServiceResult.SUCCESS;
			}
		}
		
		return BusServiceResult.FAIL;
    }
  
    /** Delete one User*/
    public BusServiceResult deleteBus(int id) {
    	Optional<Bus> result = busRepository.findById(id);
		
		if (!result.isEmpty()) {
			busRepository.delete(result.get());

			if (busRepository.findById(id).isEmpty()) {
				return BusServiceResult.SUCCESS;
			}
		}
		
		return BusServiceResult.FAIL;
    }
    
    /** Delete all Users in the database  */
    public BusServiceResult deleteAllBuses() {
    	BusServiceResult result = BusServiceResult.SUCCESS;
		
		for (Bus b : busRepository.findAll()) {
			busRepository.deleteById(b.getId());

			if (!busRepository.findById(b.getId()).isEmpty()) {
				result = BusServiceResult.FAIL;
			}
		}
		return result;
    }
    
    public Route getRouteById(int id) {
    	Optional<Route> result = routeRepository.findById(id);
    	return result.orElse(null);
    }
    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
    }

    @Transactional
	public BusServiceResult createRoute(Route route) {
		Optional<Route> result = routeRepository.findById(route.getId());
		
		if (result.isEmpty()) {
			Route savedroute = routeRepository.save(route);
			
			if (savedroute != null) {
				return BusServiceResult.SUCCESS;
			}
		}
		
		return BusServiceResult.FAIL;
	}    
   
    /** Update an Existing User */
    public BusServiceResult updateRoute(Route route, int id) {
    	Optional<Route> result = routeRepository.findById(id);
		
		if (!result.isEmpty()) {
			Route updatedRoute = result.get();
			
			updatedRoute.setBuses(route.getBuses());
			updatedRoute.setStations(route.getStations());
			updatedRoute.setTotalDistance(route.getTotalDistance());

			routeRepository.save(updatedRoute);
			
			if (!routeRepository.findById(id).isEmpty()) {
				return BusServiceResult.SUCCESS;
			}
		}
		
		return BusServiceResult.FAIL;
    }
  
    /** Delete one User*/
    public BusServiceResult deleteRoute(int id) {
    	Optional<Route> result = routeRepository.findById(id);
		
		if (!result.isEmpty()) {
			routeRepository.delete(result.get());

			if (routeRepository.findById(id).isEmpty()) {
				return BusServiceResult.SUCCESS;
			}
		}
		
		return BusServiceResult.FAIL;
    }
    
    /** Delete all Users in the database  */
    public BusServiceResult deleteAllRoutes() {
    	BusServiceResult result = BusServiceResult.SUCCESS;
		
		for (Route b : routeRepository.findAll()) {
			routeRepository.deleteById(b.getId());

			if (!routeRepository.findById(b.getId()).isEmpty()) {
				result = BusServiceResult.FAIL;
			}
		}
		return result;
    }
    
    public Station getStationById(int id) {
    	Optional<Station> result = stationRepository.findById(id);
    	return result.orElse(null);
    }
    public List<Station> getAllStations() {
        return stationRepository.findAll();
    }

    /** Creating a New User */
	public BusServiceResult createStation(Station Station) {
		Optional<Station> result = stationRepository.findById(Station.getId());
		
		if (result.isEmpty()) {
			Station savedStation = stationRepository.save(Station);
			
			if (savedStation != null) {
				return BusServiceResult.SUCCESS;
			}
		}
		
		return BusServiceResult.FAIL;
	}    
   
    /** Update an Existing User */
    public BusServiceResult updateStation(Station station, int id) {
    	Optional<Station> result = stationRepository.findById(id);
		
		if (!result.isEmpty()) {
			Station updatedStation = result.get();
			
			updatedStation.setLocation(station.getLocation());
			updatedStation.setName(station.getName());

			stationRepository.save(updatedStation);
			
			if (!stationRepository.findById(id).isEmpty()) {
				return BusServiceResult.SUCCESS;
			}
		}
		
		return BusServiceResult.FAIL;
    }
  
    /** Delete one User*/
    public BusServiceResult deleteStation(int id) {
    	Optional<Station> result = stationRepository.findById(id);
		
		if (!result.isEmpty()) {
			stationRepository.delete(result.get());

			if (stationRepository.findById(id).isEmpty()) {
				return BusServiceResult.SUCCESS;
			}
		}
		
		return  BusServiceResult.FAIL;
    }
    
    /** Delete all Users in the database  */
    public BusServiceResult deleteAllStations() {
    	BusServiceResult result = BusServiceResult.SUCCESS;
		
		for (Station b : stationRepository.findAll()) {
			stationRepository.deleteById(b.getId());

			if (!stationRepository.findById(b.getId()).isEmpty()) {
				result = BusServiceResult.FAIL;
			}
		}
		return result;
    }

    public Ticket getTicketById(long id) {
    	Optional<Ticket> result = ticketRepository.findById(id);
    	return result.orElse(null);
    }
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    /** Creating a New User */
	public BusServiceResult createTicket(Ticket Ticket) {
		Optional<Ticket> result = ticketRepository.findById(Ticket.getId());
		
		if (result.isEmpty()) {
			Ticket savedTicket = ticketRepository.save(Ticket);
			
			if (savedTicket != null) {
				return BusServiceResult.SUCCESS;
			}
		}
		
		return BusServiceResult.FAIL;
	}    
   
    /** Update an Existing User */
    public BusServiceResult updateTicket(Ticket ticket, Long id) {
    	Optional<Ticket> result = ticketRepository.findById(id);
		
		if (!result.isEmpty()) {
			Ticket updatedTicket = result.get();
			
			updatedTicket.setClient(ticket.getClient());
			updatedTicket.setDate(ticket.getDate());
			updatedTicket.setDestination(ticket.getDestination());
			updatedTicket.setPrice(ticket.getPrice());
			ticketRepository.save(updatedTicket);
			if (!ticketRepository.findById(id).isEmpty()) {
				return BusServiceResult.SUCCESS;
			}
		}
		
		return BusServiceResult.FAIL;
    }
  
    /** Delete one User*/
    public BusServiceResult deleteTicket(Long id) {
    	Optional<Ticket> result = ticketRepository.findById(id);
		
		if (!result.isEmpty()) {
			ticketRepository.delete(result.get());

			if (ticketRepository.findById(id).isEmpty()) {
				return BusServiceResult.SUCCESS;
			}
		}
		
		return BusServiceResult.FAIL;
    }
    
    
    public BusServiceResult deleteAllTickets() {
    	BusServiceResult result = BusServiceResult.SUCCESS;
		
		for (Ticket b : ticketRepository.findAll()) {
			ticketRepository.deleteById(b.getId());
			if (!ticketRepository.findById(b.getId()).isEmpty()) {
				result = BusServiceResult.FAIL;
			}
		}
		return result;
    }
    @Transactional
    public BusServiceResult addStationToRoute(Route route, Station station) {
    	if (route.addStation(station)&&station.addRoute(route)) {
    		 if(this.updateRoute(route, route.getId()).equals(BusServiceResult.SUCCESS)&&this.updateStation(station, station.getId()).equals(BusServiceResult.SUCCESS)) {
    			 return BusServiceResult.SUCCESS;
    		 }
    	}
    	return BusServiceResult.FAIL;
    }
    @Transactional
    public BusServiceResult addBusToRoute(Route route, Bus bus) {
    	if (route.addBus(bus)&&bus.addRoute(route)) {
    		 if(this.updateRoute(route, route.getId()).equals(BusServiceResult.SUCCESS)&&this.updateBus(bus, bus.getId()).equals(BusServiceResult.SUCCESS)) {
    			 return BusServiceResult.SUCCESS;
    		 }
    	}
    	return BusServiceResult.FAIL;
    }

    
    public List<Route> obtainRoutesByBus(int busId) {
            return routeRepository.findByBusesId(busId);
        }
}
