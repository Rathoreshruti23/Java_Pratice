package com.shrunity.Itfirm.Exception;

import com.shrunity.Itfirm.exception.GlobalExceptionHandler;
import com.shrunity.Itfirm.exception.InvalidPriceException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    @Test
    void testHandleInvalidPrice() {

        // Arrange
        GlobalExceptionHandler exceptionHandler =
                new GlobalExceptionHandler();

        InvalidPriceException exception =
                mock(InvalidPriceException.class);

        when(exception.getMessage())
                .thenReturn("Price must be greater than zero");

        // Act
        ResponseEntity<Map<String, String>> response =
                exceptionHandler.handleInvalidPrice(exception);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        assertEquals(
                "Price must be greater than zero",
                response.getBody().get("error")
        );
    }
}