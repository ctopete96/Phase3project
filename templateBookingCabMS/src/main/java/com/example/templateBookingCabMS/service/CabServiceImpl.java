package com.example.templateBookingCabMS.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.templateBookingCabMS.dao.CabRepository;
import com.example.templateBookingCabMS.model.Cab;

@Service
public class CabServiceImpl implements CabService {

    @Autowired
    private CabRepository cabRepository;

    @Override
    public Cab bookCab(Cab cab) {
        try {
            return cabRepository.save(cab);
        } catch (Exception e) {
            System.err.println("Error saving cab: " + e.getMessage());
            throw e; // Re-throwing the exception or handle it as needed
        }
    }

    @Override
    public Optional<Cab> getCabById(Integer id) {
        return cabRepository.findById(id);
    }

    @Override
    public List<Cab> getAllBookedCabs() {
        return new ArrayList<>((Collection<? extends Cab>) cabRepository.findAll());
    }

    @Override
    public void deleteCab(Integer id) {
        if (cabRepository.existsById(id)) {
            cabRepository.deleteById(id);
        } else {
            System.err.println("Cab with ID " + id + " does not exist.");
        }
    }

	@Override
	public Optional<Cab> getCabId(Integer id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}
}