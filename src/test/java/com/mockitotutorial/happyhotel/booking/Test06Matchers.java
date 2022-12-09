package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;
import java.time.LocalDate;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;


//golden rule: we can mock all the other classes the class we are testing has dependencies on,
//but we should NEVER mock the class we are testing.  Then we'd be testing the mock...


class Test06Matchers {

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

//	3 golden rules for argument matchers:
//		1.  Use "any()" for objects.  For primitives, use anyDouble(), anyBoolean() etc.
//		2.  Use "eq()" to mix matchers and concrete values:
//							method(any(), eq(400.0))
//	    3.  For nullable Strings, use "any()"

    @Test
    void should_NotCompleteBooking_When_PriceTooHigh() {

        //given
        BookingRequest bookingRequest = new BookingRequest("2", LocalDate.of(2020, 01, 01),
                LocalDate.of(2020, 01, 05), 2, true);
        //if we want to throw an exception no matter the input, we can use the Matcher method "any()"
        //we can give the class: any(BookingRequest.class), but we don't have to give the class;
        // for matching a primitive (like a Double), we have to pass specific any, "anyDouble()" for Double;
        //if we want to use a specific value (mix any() with a specific value) we can use the eq() method of Matchers like this:
        when(this.paymentServiceMock.pay(any(), eq(400.0))).thenThrow(BusinessException.class);

        //for Strings we can use anyString(), but this will NOT match a null String;
        //to match a null String we must use the generic any() method of Matchers
        System.out.println("Any string is this: " +   anyString());

        //when
        Executable executable = () -> bookingService.makeBooking(bookingRequest);

        //then
        assertThrows(BusinessException.class, executable);

    }
}
