package com.estudo.resource;

import com.estudo.service.PriceService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Collections;

import static io.restassured.RestAssured.*;

import java.util.List;
import java.util.UUID;

import static jakarta.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static jakarta.ws.rs.core.Response.Status.OK;
import static org.hamcrest.Matchers.*;

import static jakarta.ws.rs.core.Response.Status.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doThrow;

@QuarkusTest
class PriceResourceTest {

    @InjectMock
    PriceService priceService;

    @Test
    void testGetPricesHandlesException() {
        /**
         * Test case for getPrices when the service throws an exception
         */
        Mockito.when(priceService.getAllPrices()).thenThrow(new RuntimeException("Service error"));

        given()
            .when().get("/price")
            .then()
            .statusCode(INTERNAL_SERVER_ERROR.getStatusCode());
    }

    @Test
    void testGetPricesReturnsEmptyList() {
        /**
         * Test case for getPrices when the service returns an empty list
         */
        Mockito.when(priceService.getAllPrices()).thenReturn(Collections.emptyList());

        given()
            .when().get("/price")
            .then()
            .statusCode(OK.getStatusCode())
            .body("", hasSize(0));
    }

    /**
     * Test that getPrices returns a list of PriceDTOs with correct status code and content type
     */
    @Test
    public void testGetPricesReturnsListOfPrices() {
        // Arrange
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        List<PriceDTO> mockPrices = List.of(
            PriceDTO.of(id1, "Price 1", BigDecimal.valueOf(10.0)),
            PriceDTO.of(id2, "Price 2", BigDecimal.valueOf(20.0))
        );
        Mockito.when(priceService.getAllPrices()).thenReturn(mockPrices);

        // Act & Assert
        given()
            .when().get("/price")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType("application/json")
            .body("size()", is(2))
            .body("[0].id", is(id1.toString()))
            .body("[0].description", is("Price 1"))
            .body("[0].value", is(10.0f))
            .body("[1].id", is(id2.toString()))
            .body("[1].description", is("Price 2"))
            .body("[1].value", is(20.0f));
    }

    @Test
    void testGetPricesReturnsValidData() {
        /**
         * Test case for getPrices when the service returns valid data
         */
        List<PriceDTO> mockPrices = List.of(
            PriceDTO.of(java.util.UUID.randomUUID(), "Test Price", BigDecimal.valueOf(10.0))
        );
        Mockito.when(priceService.getAllPrices()).thenReturn(mockPrices);

        given()
            .when().get("/price")
            .then()
            .statusCode(OK.getStatusCode())
            .body("", hasSize(1))
            .body("[0].description", equalTo("Test Price"))
            .body("[0].value", equalTo(10.0f));
    }

    @Test
    void testCreatePriceHandlesException() {
        /**
         * Test case for createPrice when the service throws an exception
         */
        InsertPriceDTO validDto = new InsertPriceDTO("Test Price", BigDecimal.valueOf(10.0));
        doThrow(new RuntimeException("Service error")).when(priceService).createPrice(any());

        given()
            .contentType("application/json")
            .body(validDto)
        .when()
            .post("/price")
        .then()
            .statusCode(INTERNAL_SERVER_ERROR.getStatusCode());
    }

    /**
     * Test that createPrice successfully creates a new price
     */
    @Test
    public void testCreatePriceSuccessful() {
        InsertPriceDTO insertPriceDTO = new InsertPriceDTO("New Price", BigDecimal.valueOf(15.0));

        given()
            .contentType("application/json")
            .body(insertPriceDTO)
        .when()
            .post("/price")
        .then()
            .statusCode(CREATED.getStatusCode());

        verify(priceService, times(1)).createPrice(insertPriceDTO);
    }

    @Test
    void testCreatePriceWithExcessivelyLargeValue() {
        /**
         * Test case for createPrice when the input value is excessively large
         */
        InsertPriceDTO largePriceDto = new InsertPriceDTO("Large Price", new BigDecimal("1000000000000000000000"));

        given()
            .contentType("application/json")
            .body(largePriceDto)
        .when()
            .post("/price")
        .then()
            .statusCode(CREATED.getStatusCode());
    }

    @Test
    void testCreatePriceWithIncorrectTypeForValue() {
        /**
         * Test case for createPrice when the input value is of incorrect type
         */
        String invalidJson = "{\"description\":\"Test Price\",\"value\":\"not a number\"}";

        given()
            .contentType("application/json")
            .body(invalidJson)
        .when()
            .post("/price")
        .then()
            .statusCode(BAD_REQUEST.getStatusCode());
    }
}
