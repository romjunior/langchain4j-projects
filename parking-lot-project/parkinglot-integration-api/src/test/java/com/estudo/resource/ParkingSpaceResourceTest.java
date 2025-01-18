package com.estudo.resource;

import com.estudo.repository.parkingspace.ParkingSpace;
import com.estudo.repository.parkingspace.ParkingSpaceStatus;
import com.estudo.service.ParkingSpaceService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static io.restassured.RestAssured.given;
import static jakarta.ws.rs.core.Response.Status.CREATED;
import static jakarta.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static org.mockito.ArgumentMatchers.any;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static jakarta.ws.rs.core.Response.Status.*;
import static jakarta.ws.rs.core.Response.Status.OK;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

import java.util.Optional;

@QuarkusTest
class ParkingSpaceResourceTest {

    @InjectMock
    ParkingSpaceService parkingSpaceService;

    @Test
    void createParkingSpaceSuccessful() {
        final var parkingSpace = new ParkingSpace("A-1", ParkingSpaceStatus.AVAILABLE);
        given()
                .when()
                .body(parkingSpace)
                .contentType(ContentType.JSON)
                .post("/parkingspace")
                .then()
                .statusCode(CREATED.getStatusCode());
    }

    @Test
    void createParkingSpaceError() {
        final var parkingSpace = new ParkingSpace("A-1", ParkingSpaceStatus.AVAILABLE);
        Mockito.doThrow(new RuntimeException("Error test")).when(parkingSpaceService).createParkingSpace(any(ParkingSpace.class));
        given()
                .when()
                .body(parkingSpace)
                .contentType(ContentType.JSON)
                .post("/parkingspace")
                .then()
                .statusCode(INTERNAL_SERVER_ERROR.getStatusCode());
    }

    /**
     * Test getAllParkingSpaces returns all parking spaces successfully
     */
    @Test
    public void testGetAllParkingSpacesReturnsAllSpaces() {
        List<ParkingSpace> mockParkingSpaces = Arrays.asList(
            new ParkingSpace("A-1", ParkingSpaceStatus.AVAILABLE),
            new ParkingSpace("B-2", ParkingSpaceStatus.OCCUPIED)
        );

        when(parkingSpaceService.getAllParkingSpaces()).thenReturn(mockParkingSpaces);

        given()
            .when()
            .get("/parkingspace")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(ContentType.JSON)
            .body("$", hasSize(2))
            .body("[0].code", org.hamcrest.Matchers.is("A-1"))
            .body("[0].status", org.hamcrest.Matchers.is("AVAILABLE"))
            .body("[1].code", org.hamcrest.Matchers.is("B-2"))
            .body("[1].status", org.hamcrest.Matchers.is("OCCUPIED"));
    }

    @Test
    void testGetAllParkingSpaces_EmptyResult() {
        /**
         * Tests the scenario when getAllParkingSpaces returns an empty list.
         */
        when(parkingSpaceService.getAllParkingSpaces()).thenReturn(Collections.emptyList());

        given()
            .when()
            .get("/parkingspace")
            .then()
            .statusCode(OK.getStatusCode())
            .body("size()", is(0));
    }

    @Test
    void testGetAllParkingSpaces_InvalidAcceptHeader() {
        /**
         * Tests the scenario when an invalid Accept header is provided.
         */
        given()
            .when()
            .accept(ContentType.XML)
            .get("/parkingspace")
            .then()
            .statusCode(NOT_ACCEPTABLE.getStatusCode());
    }

    @Test
    void testGetAllParkingSpaces_ServiceException() {
        /**
         * Tests the scenario when getAllParkingSpaces throws an exception.
         */
        when(parkingSpaceService.getAllParkingSpaces()).thenThrow(new RuntimeException("Service error"));

        given()
            .when()
            .get("/parkingspace")
            .then()
            .statusCode(INTERNAL_SERVER_ERROR.getStatusCode());
    }


    /**
     * Test getParkingSpace returns a specific parking space successfully
     */
    @Test
    public void testGetParkingSpaceReturnsSpecificSpace() {
        String code = "A-1";
        ParkingSpace mockParkingSpace = new ParkingSpace(code, ParkingSpaceStatus.AVAILABLE);

        when(parkingSpaceService.getParkingSpace(code)).thenReturn(Optional.of(mockParkingSpace));

        given()
            .when()
            .get("/parkingspace/{code}", code)
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(ContentType.JSON)
            .body("code", is(code))
            .body("status", is("AVAILABLE"));
    }

    @Test
    void testGetParkingSpace_InvalidInput() {
        /**
         * Tests the scenario when an invalid input (special characters) is provided for the parking space code.
         */
        given()
            .when()
            .get("/parkingspace/!@#$%")
            .then()
            .statusCode(NOT_FOUND.getStatusCode());
    }

    @Test
    void testGetParkingSpace_NonExistentCode() {
        /**
         * Tests the scenario when a non-existent parking space code is provided.
         */
        when(parkingSpaceService.getParkingSpace("NON-EXISTENT")).thenReturn(Optional.empty());

        given()
            .when()
            .get("/parkingspace/NON-EXISTENT")
            .then()
            .statusCode(NOT_FOUND.getStatusCode());
    }

    @Test
    void testGetParkingSpace_ServiceException() {
        /**
         * Tests the scenario when the service throws an exception.
         */
        when(parkingSpaceService.getParkingSpace(any())).thenThrow(new RuntimeException("Service error"));

        given()
            .when()
            .get("/parkingspace/A-1")
            .then()
            .statusCode(INTERNAL_SERVER_ERROR.getStatusCode());
    }
}
