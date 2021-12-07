package gpsUtil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        Locale.setDefault(new Locale("en", "EN"));
        SpringApplication.run(Application.class, args);
        System.out.println("run my GPS UTIL !");

        for(int i = 0; i < 100; ++i) {
            double randomLat = ThreadLocalRandom.current().nextDouble(-180.0D, 180.0D);
            randomLat = Double.parseDouble(String.format("%.6f", randomLat));
            double randomLong = ThreadLocalRandom.current().nextDouble(-85.05112878D, 85.05112878D);
            randomLong = Double.parseDouble(String.format("%.6f", randomLong));
            System.out.println(randomLat);
            System.out.println(randomLong);
        }

    }

}


