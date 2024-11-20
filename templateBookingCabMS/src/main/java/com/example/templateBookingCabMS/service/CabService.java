package com.example.templateBookingCabMS.service;

import java.util.List;
import java.util.Optional;
import com.example.templateBookingCabMS.model.Cab;

public interface CabService {
    Cab bookCab(Cab cab); // Change to instance method

    Optional<Cab> getCabById(Integer id);
    
    List<Cab> getAllBookedCabs();
    
    void deleteCab(Integer id);

	Optional<Cab> getCabId(Integer id);
}

 