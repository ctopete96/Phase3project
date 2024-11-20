package com.example.templateBookingCabMS.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//import org.springframework.stereotype.Repository;
import com.example.templateBookingCabMS.model.Cab;

@Repository
public interface CabRepository extends CrudRepository<Cab, Integer> {
}
