package com.example.templateCalculateFare.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculateFareController {

    private static final double BASE_FARE = 5.0;
    private static final double PER_MILE_RATE = 1.5;
    private static final Logger logger = LoggerFactory.getLogger(CalculateFareController.class);

    @Value("{app.calculateFare")
    private String calculateFareGreeting;
    
    @GetMapping("/CalculateFare")
    public ResponseEntity<?> calculateFare(@RequestParam String fromLocation, @RequestParam String toLocation) {
        if (fromLocation.isEmpty() || toLocation.isEmpty()) {
            logger.error("Invalid request: fromLocation or toLocation is empty");
            return ResponseEntity.badRequest().body("fromLocation and toLocation must not be empty");
        }

        double distance = getDistance(fromLocation, toLocation);
        if (distance < 0) {
            logger.error("Distance calculation failed for locations: {} to {}", fromLocation, toLocation);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error calculating distance");
        }

        double fare = calculateFare(distance);
        return ResponseEntity.ok(fare);
    }

    private double calculateFare(double distance) {
        return BASE_FARE + (PER_MILE_RATE * distance);
    }

    private double getDistance(String fromLocation, String toLocation) {
        // Placeholder for actual distance calculation logic
        return 10.0; // Replace with real logic
    }
}
