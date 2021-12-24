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
}
