package com.estudo.resource;

import com.estudo.repository.allocation.Allocation;
import com.estudo.service.AllocationService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@QuarkusTest
class AllocationResourceTest {

    @InjectMock
    AllocationService allocationService;

    @Test
    void crateAllocationSuccessful() {
        final var body = new AllocationDTO("A1", "ENG-2394", "Branco");
        when(allocationService.createAllocation(body)).thenReturn(true);
        given()
                .when()
                .body(body)
                .contentType(ContentType.JSON)
                .post("/allocation")
                .then()
                .statusCode(HttpStatus.SC_CREATED);
    }

    @Test
    void crateAllocationError() {
        final var body = new AllocationDTO("A1", "ENG-2394", "Branco");
        when(allocationService.createAllocation(eq(body))).thenReturn(false);
        given()
                .when()
                .body(body)
                .contentType(ContentType.JSON)
                .post("/allocation")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }



    @Test
    void testGetAllocationByCarPlateNotFound() {
        String carPlate = "ABC-1234";
        when(allocationService.getAllocationByCarPlate(carPlate)).thenReturn(null);

        given()
                .when()
                .get("/allocation/car-plate/{carPlate}", carPlate)
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    void testGetAllocationByCarPlateSuccess() {
        String carPlate = "ABC-1234";
        Allocation mockAllocation = Allocation.of("B2", carPlate, "Preto");
        
        when(allocationService.getAllocationByCarPlate(carPlate)).thenReturn(mockAllocation);

        given()
                .when()
                .get("/allocation/car-plate/" + carPlate)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON)
                .body("parkingSpaceCode", equalTo("B2"))
                .body("carPlate", equalTo(carPlate))
                .body("carColor", equalTo("Preto"));
    }

    @Test
    void testGetAllocationByCarPlate_EmptyInput() {
        /**
         * Test case for empty input in getAllocationByCarPlate
         */
        given()
                .when()
                .get("/allocation/car-plate/")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    void testGetAllocationByCarPlate_ExceptionHandling() {
        /**
         * Test case for exception handling in getAllocationByCarPlate
         */
        String carPlate = "ABC-1234";
        when(allocationService.getAllocationByCarPlate(carPlate)).thenThrow(new RuntimeException("Simulated exception"));

        given()
                .when()
                .get("/allocation/car-plate/" + carPlate)
                .then()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    void testGetAllocationByCarPlate_IncorrectFormat() {
        /**
         * Test case for incorrect format input in getAllocationByCarPlate
         */
        String incorrectFormatPlate = "ABC1234";
        when(allocationService.getAllocationByCarPlate(incorrectFormatPlate)).thenReturn(null);

        given()
                .when()
                .get("/allocation/car-plate/" + incorrectFormatPlate)
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    void testGetAllocationByCarPlate_InvalidInput() {
        /**
         * Test case for invalid input in getAllocationByCarPlate
         */
        String invalidCarPlate = "!@#$%";
        when(allocationService.getAllocationByCarPlate(invalidCarPlate)).thenReturn(null);

        given()
                .when()
                .get("/allocation/car-plate/" + invalidCarPlate)
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    void testGetAllocationByCarPlate_NonExistentPlate() {
        /**
         * Test case for non-existent car plate in getAllocationByCarPlate
         */
        String nonExistentPlate = "XYZ-9999";
        when(allocationService.getAllocationByCarPlate(nonExistentPlate)).thenReturn(null);

        given()
                .when()
                .get("/allocation/car-plate/" + nonExistentPlate)
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    void testGetAllocationByCarPlate_OutsideBounds() {
        /**
         * Test case for input outside accepted bounds in getAllocationByCarPlate
         */
        String longCarPlate = "ABC-12345678901234567890";
        when(allocationService.getAllocationByCarPlate(longCarPlate)).thenReturn(null);

        given()
                .when()
                .get("/allocation/car-plate/" + longCarPlate)
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }
}
