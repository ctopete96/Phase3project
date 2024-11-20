package com.example.templateBookingCabMS.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.templateBookingCabMS.config.FeignService;
import com.example.templateBookingCabMS.model.Cab;
import com.example.templateBookingCabMS.service.CabService;
import com.example.templateBookingCabMS.service.CabServiceImpl;
@RestController
@Configuration
@RequestMapping(path = "cab")
@CrossOrigin(origins="http://localhost:4200")
public class BookCabController {

    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private CabService cabService;
    
    @Value("${app.greeting}")
    private String testGreeting;
    
    @Autowired
    private FeignService feignService;

    @GetMapping("/all")
    public ResponseEntity<List<Cab>> getAllCabs() {
        System.out.println("Retrieving all booked cabs");
        List<Cab> allCabsList = cabService.getAllBookedCabs();
        return ResponseEntity.ok(allCabsList);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Cab>> getCabById(@RequestParam Integer id) {
        System.out.println("Retrieving cab with ID: " + id);
        
        Optional<Cab> cab = cabService.getCabById(id);
        
        if (cab != null) {
            return ResponseEntity.ok(cab);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/add")
    public String addBookedCab(@RequestParam String FromLocation, @RequestParam String ToLocation,
            @RequestParam Integer TypeOfCab) {
        Cab cab = new Cab(1, FromLocation, ToLocation, TypeOfCab, 50.0);
        return null;
    }

    @PostMapping("/addtwo")
    public ResponseEntity<Cab> addBookedCabtwo(@RequestBody Cab cab) {
        ResponseEntity<Double> calcFareResponse = feignService.calculateFare(cab.getFromLocation(), cab.getToLocation());
        Double calculatedFare = calcFareResponse.getBody();
        cab.setRate(calculatedFare);
        
        Cab cabBookedToDB = cabService.bookCab(cab);
        System.out.println("Testing!: " + testGreeting);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(cabBookedToDB);
    }
}
