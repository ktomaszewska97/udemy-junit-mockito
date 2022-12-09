package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Test04MultipleThenReturnCalls {

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
    void should_CountAvailablePlaces_WhenOneRoomAvailable() {
        // Given
        when(roomService.getAvailableRooms())
                .thenReturn(Collections.singletonList(new Room("Room2", 2)))
                .thenReturn(Collections.emptyList());
        int expectedFirst = 2;
        int expectedSecond = 0;

        // When
        int actualFirst = bookingService.getAvailablePlaceCount();
        int actualSecond = bookingService.getAvailablePlaceCount();

        // Then
        assertAll(
                () -> assertEquals(expectedFirst, actualFirst),
                () -> assertEquals(expectedSecond, actualSecond)
        );
    }

}
