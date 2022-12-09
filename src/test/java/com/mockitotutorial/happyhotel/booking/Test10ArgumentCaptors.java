package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import java.util.List;

class Test10ArgumentCaptors {
    private BookingService bookingService;
    private PaymentService paymentServiceMock;
    private RoomService roomServiceMock;
    private BookingDAO bookingDAOMock;
    private MailSender mailSenderMock;
    private ArgumentCaptor<Double> doubleCaptor;
    //if we wanted an argument captor for another type of object, we just pass in the "<>" like this:
    private ArgumentCaptor<BookingRequest> bookingRequestCaptor;

    @BeforeEach
    void setup() {

        this.paymentServiceMock = mock(PaymentService.class);
        this.roomServiceMock = mock(RoomService.class);
        this.bookingDAOMock = mock(BookingDAO.class);
        this.mailSenderMock = mock(MailSender.class);
        this.bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock, mailSenderMock);
        //we have to initiate our argumentcaptor it in our setup method:
        this.doubleCaptor = ArgumentCaptor.forClass(Double.class);

    }

    @Test
    void should_PayCorrectPrice_When_InputOK() {
        //given
        //the 'prepaid' argument is true, so the paymentServiceMock should get called, and we want to test that it was in fact called;
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01),
                LocalDate.of(2020, 01, 05), 2, true);

        //when
        bookingService.makeBooking(bookingRequest);

        //then
        //previously we knew that the price to pay would be '400.0',
        //this time instead of using the value that we expect, we will use a captor to capture/catch the value
        //so we can reuse it later for various purposes:
        //old:  verify(paymentServiceMock).pay(bookingRequest, 400.0);
        //new (with captor):
        verify(paymentServiceMock).pay(eq(bookingRequest), doubleCaptor.capture());
        //b/c doubleCaptors & all captors work just like matches, we need to use a matcher for the 1st argument so we don't get an exception thrown
        //now that the doubleCaptor captured the argument, let's save it somewhere:
        double capturedArgument = doubleCaptor.getValue();
        //now the 'capturedArgument' contains the value that was used when the pay() method was invoked and we can do various things with it:
        System.out.println(capturedArgument);

        assertEquals(400.0, capturedArgument);



    }

    //what happens when we have multiple invocations?
    @Test
    void should_PayCorrectPrices_When_MultipleCalls() {
        //given
        //we'll create a new sample BookingRequest, BookingRequest2, which will be for just 1 night
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01),
                LocalDate.of(2020, 01, 05), 2, true);
        BookingRequest bookingRequest2 = new BookingRequest("1", LocalDate.of(2020, 01, 01),
                LocalDate.of(2020, 01, 02), 2, true);
        List<Double> expectedValues = Arrays.asList(400.0, 100.0);
        //when
        bookingService.makeBooking(bookingRequest);
        //and to test our multiple BookingRequests we want to call it again like so:
        bookingService.makeBooking(bookingRequest2);

        //then
        //we want to make sure paymentServiceMock is called twice this time:
        verify(paymentServiceMock, times(2)).pay(any(), doubleCaptor.capture());
        List<Double> capturedArguments = doubleCaptor.getAllValues();

        assertEquals(expectedValues, capturedArguments);

    }

}
