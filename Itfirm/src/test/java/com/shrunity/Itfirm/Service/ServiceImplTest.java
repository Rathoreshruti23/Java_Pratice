package com.shrunity.Itfirm.Service;

import com.shrunity.Itfirm.DTO.ProductDTO;
import com.shrunity.Itfirm.Repository.ProductRepository;
import com.shrunity.Itfirm.entity.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
//“In unit tests, the expected DTO is created in the test itself;
// the database response is mocked as an entity, and the service converts it into a DTO which is then compared with the expected DTO.”

@ExtendWith(MockitoExtension.class)
class ServiceImplTest {
    @InjectMocks
    private ServiceImpl productService; // ✅ class under test

    @Mock
    private ProductRepository productRepository;

    @Test
    void getAllShouldSuccessfullyGetAllProduct() {
        System.out.println("First test case for getAll method");
        List<Product> productList = new ArrayList<>();
        Product product1 = new Product(); //creating object of entity
        product1.setProductName("Comb");
        product1.setId(1L); // L =long
        productList.add(product1); // added to arraylist

        Product product2 = new Product();
        product2.setProductName("Makeup");
        product2.setId(1L);
        productList.add(product2);

        when(productRepository.findAll()).thenReturn(productList); //Whenever productRepository.findAll() is called,
        // return this productList instead of actually querying the database.

        //Act
        List<ProductDTO> productDTOList = productService.getAll();
        //Assert
        assertNotNull(productDTOList);
        assertEquals(2, productDTOList.size()); // expected 2 elemenet in array
        assertEquals("Comb", productDTOList.get(0).getProductName());
        assertEquals("Makeup", productDTOList.get(1).getProductName());

        verify(productRepository, times(1)).findAll(); //Confirms that productRepository.findAll() was called exactly once.
        //Ensures the service actually interacted with the repository.
    }

    @Test
    void CreateShouldSuccessfullyCreateProduct() {
        //Arrange : Set up your test data, mocks, or objects.
        System.out.println("First test case for getAll method");
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductName("New Product");

        Product productEntity = new Product();
        productEntity.setProductName(productDTO.getProductName());

        Product savedProduct = new Product();
        savedProduct.setId(1L);
        savedProduct.setProductName(productDTO.getProductName());
        // Mock repository save behavior
        when(productRepository.save(productEntity)).thenReturn(savedProduct);

        // Act : Call the method you want to test.
        ProductDTO result = productService.create(productDTO); // we need to pass an object but still the
        // productService is null so to initialize this we to take help from repository

        // Assert : Check if the results are what you expect
        assertNotNull(result); //object should not be null
        assertEquals(1L, result.getId()); //ID should be 1
        assertEquals("New Product", result.getProductName()); //name should match

        verify(productRepository, times(1)).save(productEntity);

    }

    @Test
    void getShouldReturnProductDTOWhenProductExist() {
        //Arrange
        Long ProductId = 1L;
        Product product = new Product();
        product.setId(ProductId);
        product.setProductName("Shruti");
//Mock
        when(productRepository.findById(ProductId)).thenReturn(Optional.of(product)); // optional becoz the id may or may not exist in db
//Act
        ProductDTO result = productService.get(ProductId);
        //Assert
        assertNotNull(result);
        assertEquals(ProductId, result.getId());
        assertEquals("Shruti", result.getProductName());

        //verify
        verify(productRepository, times(1)).findById(ProductId);
    }

    @Test
    void getShouldThrowExceptionWhenProductDoesNotExist() {
        /*Full flow in simple words

productService.get(2L) is executed

Service throws RuntimeException

assertThrows catches it

JUnit stores it in exception

exception.getMessage() extracts the message string

assertEquals() compares:

Expected message

Actual message */

        // Arrange: prepare test data
        Long productId = 2L;

        // Mock repository behavior:
        // findById returns Optional.empty() when product is not found in database
        when(productRepository.findById(productId))
                .thenReturn(Optional.empty());

        // Act & Assert:
        // Execute productService.get(productId) and verify that it throws RuntimeException
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.get(productId);
        });

        // Match exact exception message
        assertEquals("Product not found with ID: " + productId,
                exception.getMessage());

        // Verify:
        // Ensure repository method was called exactly once
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void deleteAllShouldSuccessfullyDeleteALLProduct() {
//Act
        String reponse = productService.deleteAll();
        //assert
        verify(productRepository, times(1)).deleteAll();
        assertEquals("Deleted all items successfully", reponse);
    }

    @Test
    void deleteByIDShouldDeleteProductWhenExits() {
        Long ProductId = 1L;
        //Arrange
        when(productRepository.existsById(ProductId)).thenReturn(true);
//Act
        String reponse = productService.deleteById(ProductId);
        //Assert
        verify(productRepository, times(1)).existsById(ProductId);
        verify(productRepository, times(1)).deleteById(ProductId);
        assertEquals("Successfully deleted product with ID: 1", reponse);
    }

    @Test
    void deleteById_ShouldThrowException_WhenIdDoesNotExist() {
        Long productId = 2L;

        // Mock repository behavior:
        // findById returns Optional.empty() when product is not found in database
        //existsById(id) → CHECK
        //Used before deleting or updating to validate that the record exists.
        when(productRepository.existsById(productId))
                .thenReturn(false);

        // Act & Assert:
        // Execute productService.deleteById(productId) and verify that it throws RuntimeException
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.deleteById(productId);
        });

        // Match exact exception message from serviceimpl
        assertEquals("This Id is not present: " + productId,
                exception.getMessage());

        // Verify:
        // deleteById(id) → ACTION
        //Actually deletes the record from DB.
        //Use never() when: Method must NOT be executed
        //Exception flow / validation failed
        verify(productRepository, never()).deleteById(productId);
    }

    @Test
    void updateById_ShouldUpdateProduct_WhenProductExists() {
        Long id=1L;
        //We create productDTO as client input, existingProduct to simulate DB state,
        // and updatedProduct to simulate repository save response, ensuring the service correctly updates and returns the DTO.”
        ProductDTO productDTO=new ProductDTO(); //dto :Input data from user/client
        productDTO.setProductName("Updated Product");
        productDTO.setPrice(500);
        productDTO.setMfd("23/03/1998");
        productDTO.setExp("12/12/2002");
//You need input to update (productDTO)
//You need existing data (existingProduct)
//You need the saved result (updatedProduct) to assert the service return
        Product existingProduct = new Product(); //entity : Current state in database : Existing DB row
        existingProduct.setId(id);
        existingProduct.setProductName("old Product");

        Product Updatedproduct = new Product(); //
        Updatedproduct.setProductName("Updated Product");
        Updatedproduct.setId(id);
        //Arrange
        when(productRepository.findById(id)).thenReturn(Optional.of(existingProduct)); //Optional.of :Creates an Optional that contains a value.
        //The value cannot be null — if you pass null, it will throw: NullPointerException : When you are sure the value exists.
        //Optional.empty : Creates an empty Optional, i.e., it contains no value.
        //Safe alternative to null → avoids NullPointerException.
        //Use case:
        //When the value does not exist
        when(productRepository.findById(id)).thenReturn(Optional.of(Updatedproduct));
        ProductDTO response = productService.updateById(id, productDTO);
        assertNotNull(response);
        assertEquals("Updated Product", response.getProductName());

        verify(productRepository,times(1)).findById(id);
        verify(productRepository,times(1)).save(existingProduct);
    }
    @Test
    void updateById_ShouldThrowsException_WhenProductNotExists(){
        Long id=99L;
        ProductDTO productDTO=new ProductDTO();
        when(productRepository.findById(id))
                .thenReturn(Optional.empty());

        // Act & Assert:
        // Execute productService.deleteById(productId) and verify that it throws RuntimeException
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.updateById(id,productDTO);
        });
        assertEquals("Product not found with ID: " + id,
                exception.getMessage());
        verify(productRepository, never()).save(Mockito.<Product>any()); // let any product can used here
        //Verify that the save() method was NEVER called on productRepository with ANY argument.
    }
}