package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

public class Test01FirstMocks {
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

    @Test
    void should_CalculateCorrectPrice_WhenCorrectInput() {
        // Given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2021, 7, 8),
                LocalDate.of(2021, 7, 18), 2, false);
        double expected = 10 * 2 * 50;

        // When
        double actual = bookingService.calculatePrice(bookingRequest);

        // Then
        assertEquals(expected, actual);
    }

}
