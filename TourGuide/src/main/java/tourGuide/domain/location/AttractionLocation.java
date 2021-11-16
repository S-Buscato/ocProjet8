package tourGuide.domain.location;

import gpsUtil.location.Attraction;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Data
@Generated
@NoArgsConstructor
public class AttractionLocation implements Comparable<AttractionLocation> {
    Attraction attraction;
    double distance;

    @Override
    public int compareTo(AttractionLocation attractionLocation) {
        return Double.compare(this.distance, attractionLocation.getDistance());
    }

}
