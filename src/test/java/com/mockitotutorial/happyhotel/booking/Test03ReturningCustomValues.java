package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Test03ReturningCustomValues {

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
        when(roomService.getAvailableRooms()).thenReturn(Collections.singletonList(new Room("Room2", 2)));
        int expected = 2;

        // When
        int actual = bookingService.getAvailablePlaceCount();

        // Then
        assertEquals(expected, actual);
    }

    @Test
    void should_CountAvailablePlaces_WhenMultipleRoomAvailable() {
        // Given
        List<Room> rooms = Arrays.asList(new Room("Room 1", 2), new Room("Room 2", 5));
        when(roomService.getAvailableRooms()).thenReturn(rooms);
        int expected = 7;

        // When
        int actual = bookingService.getAvailablePlaceCount();

        // Then
        assertEquals(expected, actual);
    }

}
