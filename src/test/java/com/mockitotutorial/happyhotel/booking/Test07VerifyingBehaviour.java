package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.*;


//golden rule: we can mock all the other classes the class we are testing has dependencies on,
//but we should NEVER mock the class we are testing.  Then we'd be testing the mock...


class Test07VerifyingBehaviour {

    private BookingService bookingService;
    private PaymentService paymentServiceMock;
    private RoomService roomServiceMock;
    private BookingDAO bookingDAOMock;
    private MailSender mailSenderMock;

    @BeforeEach
    void setup() {

        this.paymentServiceMock = mock(PaymentService.class);
        this.roomServiceMock = mock(RoomService.class);
        this.bookingDAOMock = mock(BookingDAO.class);
        this.mailSenderMock = mock(MailSender.class);
        this.bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock, mailSenderMock);
    }

    @Test
    void should_InvokePayment_When_Prepaid() {
        //given
        //the 'prepaid' argument is true, so the paymentServiceMock should get called, and we want to test that it was in fact called;
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01),
                LocalDate.of(2020, 01, 05), 2, true);

        //when
        bookingService.makeBooking(bookingRequest);

        //then
        //verify that the paymentServiceMock was used once:
        verify(paymentServiceMock).pay(bookingRequest, 400.0);

        //verify that it was used multiple times specified in args:
        verify(paymentServiceMock, times(1)).pay(bookingRequest, 400.0);

        //verifies that the paymentServiceMock was only used ONCE:
        verifyNoMoreInteractions(paymentServiceMock);

    }

    @Test
    void should_NotInvokePayment_When_Prepaid() {
        //given
        //if the prepaid arg is 'false', the bookingRequest should NOT call the paymentServiceMock
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01),
                LocalDate.of(2020, 01, 05), 2, false);

        //when
        bookingService.makeBooking(bookingRequest);

        //then
        //this verifies that the paymentServiceMock is NEVER called, and the method pay() was never invoked for any kind of input:
        verify(paymentServiceMock, never()).pay(any(), anyDouble());

    }
}
