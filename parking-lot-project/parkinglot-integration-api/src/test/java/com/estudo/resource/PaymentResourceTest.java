package com.estudo.resource;

import com.estudo.service.PaymentService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static io.restassured.RestAssured.*;
import static jakarta.ws.rs.core.Response.Status.OK;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import jakarta.ws.rs.core.MediaType;
import static jakarta.ws.rs.core.Response.Status.*;

import com.estudo.repository.payment.PaymentStatus;

@QuarkusTest
class PaymentResourceTest {

    @InjectMock
    PaymentService paymentService;

    @Test
    void testCalculateByCarPlateReturnsCorrectCalculation() {
        // Given
        String carPlate = "XYZ789";
        CalculationDTO expectedCalculation = new CalculationDTO(BigDecimal.valueOf(15.50), 3);
        when(paymentService.calculatePaymentByCarPlate(carPlate)).thenReturn(expectedCalculation);

        // When & Then
        given()
            .when().get("/payment/calculate/" + carPlate)
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(MediaType.APPLICATION_JSON)
            .body("value", comparesEqualTo(15.50f))
            .body("hoursStayed", equalTo(3));

        verify(paymentService).calculatePaymentByCarPlate(carPlate);
    }

    @Test
    void testCalculateByCarPlate_serviceException() {
        /**
         * Test case for exception thrown by PaymentService in calculateByCarPlate method
         */
        when(paymentService.calculatePaymentByCarPlate("ERR000"))
                .thenThrow(new RuntimeException("Service error"));

        given()
                .when().get("/payment/calculate/ERR000")
                .then()
                .statusCode(INTERNAL_SERVER_ERROR.getStatusCode());
    }

    @Test
    void testPaymentServiceException() {
        /**
         * Test payment when PaymentService throws an exception
         */
        String carPlate = "ABC123";
        when(paymentService.payment(carPlate)).thenThrow(new RuntimeException("Service error"));

        given()
            .when().get("/payment/" + carPlate)
            .then()
            .statusCode(INTERNAL_SERVER_ERROR.getStatusCode());

        verify(paymentService).payment(carPlate);
    }

    @Test
    void testPaymentWithAlreadyPaidCarPlate() {
        /**
         * Test payment for a car plate that has already been paid
         */
        String paidCarPlate = "DEF456";
        when(paymentService.payment(paidCarPlate)).thenReturn(PaymentStatus.ALREADY_PAID);

        given()
            .when().get("/payment/" + paidCarPlate)
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(MediaType.APPLICATION_JSON)
            .body(equalTo("\"ALREADY_PAID\""));

        verify(paymentService).payment(paidCarPlate);
    }

    @Test
    void testPaymentWithEmptyCarPlate() {
        /**
         * Test payment with an empty car plate
         */
        given()
            .when().get("/payment/")
            .then()
            .statusCode(NOT_FOUND.getStatusCode());
    }

    @Test
    void testPaymentWithInvalidCarPlate() {
        /**
         * Test payment with an invalid car plate format
         */
        String invalidCarPlate = "ABC12345";
        when(paymentService.payment(invalidCarPlate)).thenThrow(new IllegalArgumentException("Invalid car plate format"));

        given()
            .when().get("/payment/" + invalidCarPlate)
            .then()
            .statusCode(INTERNAL_SERVER_ERROR.getStatusCode());

        verify(paymentService).payment(invalidCarPlate);
    }

    @Test
    void testPaymentWithNonExistentCarPlate() {
        /**
         * Test payment with a non-existent car plate
         */
        String nonExistentCarPlate = "XYZ999";
        when(paymentService.payment(nonExistentCarPlate)).thenReturn(PaymentStatus.NOT_EXISTS);

        given()
            .when().get("/payment/" + nonExistentCarPlate)
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(MediaType.APPLICATION_JSON)
            .body(equalTo("\"NOT_EXISTS\""));

        verify(paymentService).payment(nonExistentCarPlate);
    }

    /**
     * Test successful payment processing for a given car plate
     */
    @Test
    void testSuccessfulPaymentByCarPlate() {
        // Given
        String carPlate = "ABC123";
        PaymentStatus expectedStatus = PaymentStatus.APPROVED;
        when(paymentService.payment(carPlate)).thenReturn(expectedStatus);

        // When & Then
        given()
            .when().get("/payment/" + carPlate)
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(MediaType.APPLICATION_JSON)
            .body(equalTo("\"APPROVED\""));

        verify(paymentService).payment(carPlate);
    }
}
