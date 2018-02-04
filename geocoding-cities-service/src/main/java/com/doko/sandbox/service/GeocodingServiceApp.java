package com.doko.sandbox.service;


import com.doko.sandbox.service.utils.SphericalUtil;
import com.doko.sandbox.store.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@SpringBootApplication
public class GeocodingServiceApp implements CommandLineRunner {

    @Autowired
    public GeocodingService geocodingService;

    public static void main(String[] args) {
        SpringApplication.run(GeocodingServiceApp.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        boolean exitApplication = false;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            geocodingService.loadInputToStore();
            while (!exitApplication) {
                exitApplication = runUntilExit(reader);
            }
        }
    }

    private boolean runUntilExit(BufferedReader reader) throws IOException {
        System.out.print("Enter latitude: ");
        Double lat = Double.valueOf(reader.readLine());
        System.out.print("Enter longitude: ");
        Double lng = Double.valueOf(reader.readLine());
        System.out.println("Entered Lat lon is : " + lat + " " + lng);

        List<Geometry> closestLocations = geocodingService.getClosestLocations(lat, lng);

        System.out.println("Closest locations from datastore:");
        closestLocations.forEach(
                (Geometry geometry) -> {
                    double dist = SphericalUtil.computeDistanceBetween(geometry.getLat(), geometry.getLng(), lat, lng);
                    System.out.printf("%30s. Distance (km): %.2f \n", geometry.getAddress(), dist);
                }
        );

        System.out.print("Do you want to quit? Enter '[y]es': ");
        String answerToQuit = reader.readLine();
        return "yes".equalsIgnoreCase(answerToQuit) || "y".equalsIgnoreCase(answerToQuit);
    }
}
