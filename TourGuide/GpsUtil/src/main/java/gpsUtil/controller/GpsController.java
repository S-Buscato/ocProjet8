package gpsUtil.controller;

import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import gpsUtil.service.GpsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class GpsController {
    @Autowired
    GpsUtil gpsUtil;

    @GetMapping("getUserLocation")
    public VisitedLocation getUserLocation(@RequestParam UUID userId){
        System.out.println(userId);
        return gpsUtil.getUserLocation(userId);
    }

    @GetMapping("getAttractions")
    public List<Attraction> getAttractions(){
        System.out.println("get attraction");
        return gpsUtil.getAttractions();
    }
}
