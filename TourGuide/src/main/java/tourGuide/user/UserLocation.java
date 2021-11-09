package tourGuide.user;

import lombok.Data;
import lombok.Generated;

@Data
@Generated
public class UserLocation {
    private Location location;
    private String userId;

    public UserLocation(Location location, String userId) {
        this.location = location;
        this.userId = userId;
    }

    public UserLocation(gpsUtil.location.Location location, String userId) {
    }
}
