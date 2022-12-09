package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Test02DefaultReturnValues {
    private BookingService bookingService;
    private PaymentService paymentService;
    private RoomService roomService;
    private BookingDAO bookingDAO;
    private MailSender mailSender;

    @BeforeEach
    void setup() {
        paymentService = mock(PaymentService.class);
        roomService = mock(RoomService.class);
        bookingDAO = mock(BookingDAO.class);
        mailSender = mock(MailSender.class);
        bookingService = new BookingService(paymentService, roomService, bookingDAO, mailSender);
    }

    // Mocks if not specified - return by default: null, empty lists
    @Test
    void should_CountAvailablePlaces() {
        // Given
        int expected = 0;

        // When
        int actual = bookingService.getAvailablePlaceCount();

        // Then
        assertEquals(expected, actual);
    }
}
