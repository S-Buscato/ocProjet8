package tourGuide.service;

import gpsUtil.GpsUtil;
import gpsUtil.location.Location;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tourGuide.domain.location.AttractionLocation;
import tourGuide.domain.user.*;
import tourGuide.domain.user.User;
import tourGuide.helper.InternalTestHelper;
import tourGuide.tracker.Tracker;
import tripPricer.Provider;
import tripPricer.TripPricer;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class TourGuideService {
	private Logger logger = LoggerFactory.getLogger(TourGuideService.class);
	private final GpsUtil gpsUtil;
	private final RewardsService rewardsService;
	private final TripPricer tripPricer = new TripPricer();
	public final Tracker tracker;
	boolean testMode = true;
	
	public TourGuideService(GpsUtil gpsUtil, RewardsService rewardsService) {
		this.gpsUtil = gpsUtil;
		this.rewardsService = rewardsService;
		
		if(testMode) {
			logger.info("TestMode enabled");
			logger.debug("Initializing users");
			initializeInternalUsers();
			logger.debug("Finished initializing users");
		}
		tracker = new Tracker(this);
		addShutDownHook();
	}
	
	public List<UserReward> getUserRewards(User user) {
		return user.getUserRewards();
	}
	
	public VisitedLocation getUserLocation(User user) {
		VisitedLocation visitedLocation = (user.getVisitedLocations().size() > 0) ?
			user.getLastVisitedLocation() :
			trackUserLocation(user);
		return visitedLocation;
	}
	
	public User getUser(String userName) {
		return internalUserMap.get(userName);
	}
	
	public List<User> getAllUsers() {
		return internalUserMap.values().stream().collect(Collectors.toList());
	}
	
	public void addUser(User user) {
		if(!internalUserMap.containsKey(user.getUserName())) {
			internalUserMap.put(user.getUserName(), user);
		}
	}
	
	public List<Provider> getTripDeals(User user) {
		int cumulatativeRewardPoints = user.getUserRewards().stream().mapToInt(i -> i.getRewardPoints()).sum();
		List<Provider> providers = 	tripPricer.getPrice(tripPricerApiKey, user.getUserId(), user.getUserPreferences().getNumberOfAdults(),
				user.getUserPreferences().getNumberOfChildren(), user.getUserPreferences().getTripDuration(), cumulatativeRewardPoints);
		user.setTripDeals(providers);
		return providers;
	}
	
	public VisitedLocation trackUserLocation(User user) {
		VisitedLocation visitedLocation = gpsUtil.getUserLocation(user.getUserId());
		user.addToVisitedLocations(visitedLocation);
		rewardsService.calculateRewards(user);
		return visitedLocation;
	}

	public List<Attraction> getNearByAttractions(VisitedLocation visitedLocation) {
		List<Attraction> nearbyAttractions = new ArrayList<>();
		List<AttractionLocation> attractionLocations = new ArrayList<>();

		//get all attractions and calcul each distance from user
		for(Attraction attraction : gpsUtil.getAttractions()) {
			AttractionLocation attractionLocation = new AttractionLocation();
			attractionLocation.setAttraction(attraction);
			Location locationAttraction = new Location(attraction.longitude, attraction.latitude);
			attractionLocation.setDistance(rewardsService.getDistance(locationAttraction,visitedLocation.location));
			attractionLocations.add(attractionLocation);
		}
		//sort each attraction by distance
		//avec implements comparator dans la classe
		Collections.sort(attractionLocations);

		//autre façon sans implements comparator dans la classe
		/*Comparator<AttractionLocation> attractionLocationComparator = Comparator.comparing(AttractionLocation::getDistance);
		Collections.sort(attractionLocations, attractionLocationComparator);*/

		//peut on faire mieux/plus direct ?
		//get the 5 first attractions
		List<AttractionLocation> fiveAttraction = attractionLocations.stream().limit(5).collect(Collectors.toList());
		for(AttractionLocation attractionLocation1 : fiveAttraction){
			nearbyAttractions.add(attractionLocation1.getAttraction());
			System.out.println(attractionLocation1.getAttraction().attractionName + " --- " + attractionLocation1.getDistance());
		}
		return nearbyAttractions;
	}
	
	private void addShutDownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread() { 
		      public void run() {
		        tracker.stopTracking();
		      } 
		    }); 
	}

	public List<UserLocation> getAllUsersLocation() {
		List<User> userList = getAllUsers();
		List<UserLocation> userLocationList = new ArrayList<>();

		userList.forEach(u -> {
			userLocationList.add(new UserLocation(u.getLastVisitedLocation().location, u.getUserId().toString()));
		});

		return userLocationList;
	}

	public void executorService(List<User> users){
		ExecutorService executorService = Executors.newFixedThreadPool(100);

		for (User user: users) {
			Runnable runnableTask = () -> {
				trackUserLocation(user);
			};
			executorService.submit(runnableTask);
		}
		executorService.shutdown();
		try {
			executorService.awaitTermination(15, TimeUnit.MINUTES);
		} catch (Exception e) {
			e.printStackTrace();
		}
	};
	
	/**********************************************************************************
	 * 
	 * Methods Below: For Internal Testing
	 * 
	 **********************************************************************************/
	private static final String tripPricerApiKey = "test-server-api-key";
	// Database connection will be used for external users, but for testing purposes internal users are provided and stored in memory
	private final Map<String, User> internalUserMap = new HashMap<>();
	private void initializeInternalUsers() {
		IntStream.range(0, InternalTestHelper.getInternalUserNumber()).forEach(i -> {
			String userName = "internalUser" + i;
			String phone = "000";
			String email = userName + "@tourGuide.com";
			User user = new User(UUID.randomUUID(), userName, phone, email);
			generateUserLocationHistory(user);
			
			internalUserMap.put(userName, user);
		});
		logger.debug("Created " + InternalTestHelper.getInternalUserNumber() + " internal test users.");
	}
	
	private void generateUserLocationHistory(User user) {
		IntStream.range(0, 3).forEach(i-> {
			user.addToVisitedLocations(new VisitedLocation(user.getUserId(), new Location(generateRandomLatitude(), generateRandomLongitude()), getRandomTime()));
		});
	}
	
	private double generateRandomLongitude() {
		double leftLimit = -180;
	    double rightLimit = 180;
	    return leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
	}
	
	private double generateRandomLatitude() {
		double leftLimit = -85.05112878;
	    double rightLimit = 85.05112878;
	    return leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
	}
	
	private Date getRandomTime() {
		LocalDateTime localDateTime = LocalDateTime.now().minusDays(new Random().nextInt(30));
	    return Date.from(localDateTime.toInstant(ZoneOffset.UTC));
	}
	
}
