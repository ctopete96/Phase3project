package com.example.templateBookingCabMS;

import com.example.templateBookingCabMS.controller.BookCabController;
import com.example.templateBookingCabMS.model.Cab;
import com.example.templateBookingCabMS.service.CabService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BookCabControllerTest {

    @InjectMocks
    private BookCabController bookCabController;

    @Mock
    private CabService cabService;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addBookedCabtwo_ShouldReturnSuccessMessage_WhenFareCalculatedSuccessfully() {
        // Arrange
        Cab cab = new Cab();
        cab.setFromLocation("Location A");
        cab.setToLocation("Location B");
        cab.setTypeOfCab("Sedan");

        String fareServiceUrl = "http://TEMPLATECALCULATEFARE/CalculateFare";
        double expectedFare = 15.75;

        when(restTemplate.exchange(
                any(String.class),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(Double.class)))
                .thenReturn(ResponseEntity.ok(expectedFare));

        // Act
        ResponseEntity<String> response = bookCabController.addBookedCabtwo(cab);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Cab booked successfully! Calculated Fare: 15.75, From: Location A, To: Location B, Type: Sedan",
                response.getBody());

        verify(cabService, times(1));
		// Verify that the cabService's bookCab method was called once
        CabService.bookCab(cab);
    }

    @Test
    void addBookedCabtwo_ShouldReturnErrorMessage_WhenFareCalculationFails() {
        // Arrange
        Cab cab = new Cab();
        cab.setFromLocation("Location A");
        cab.setToLocation("Location B");
        cab.setTypeOfCab("Sedan");

        when(restTemplate.exchange(
                any(String.class),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(Double.class)))
                .thenThrow(new RuntimeException("Service unavailable"));

        // Act
        ResponseEntity<String> response = bookCabController.addBookedCabtwo(cab);

        // Assert
        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Error calculating fare: Service unavailable", response.getBody());
    }
}
