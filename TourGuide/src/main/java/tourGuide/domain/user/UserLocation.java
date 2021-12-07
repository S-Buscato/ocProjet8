package tourGuide.domain.user;

import gpsUtil.location.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Data
@Generated
@AllArgsConstructor
@NoArgsConstructor
public class UserLocation {
    private Location location;
    private String userId;
/*
    public UserLocation(location location, String userId) {
        this.location = location;
        this.userId = userId;
    }

    public UserLocation(gpsUtil.location.location location, String userId) {
    }*/
}
