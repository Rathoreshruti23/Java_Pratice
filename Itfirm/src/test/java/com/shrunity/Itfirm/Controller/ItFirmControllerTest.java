package com.shrunity.Itfirm.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shrunity.Itfirm.DTO.ProductDTO;
import com.shrunity.Itfirm.Service.ProductService;
import com.shrunity.Itfirm.exception.InvalidPriceException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItFirmController.class)
public class ItFirmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService service;

    @Autowired
    private ObjectMapper mapper;


    // =========================================================
    // GET /api/product
    // =========================================================

    @Test
    public void testGetAllProducts() throws Exception {

        ProductDTO p1 = new ProductDTO(
                1L,
                "Book",
                230,
                "23/03/2002",
                "23/12/2025"
        );

        ProductDTO p2 = new ProductDTO(
                2L,
                "Pen",
                20,
                "01/01/2020",
                "01/01/2026"
        );

        when(service.getAll())
                .thenReturn(Arrays.asList(p1, p2));

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/product")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))

                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].productName").value("Book"))
                .andExpect(jsonPath("$[0].price").value(230))

                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].productName").value("Pen"))
                .andExpect(jsonPath("$[1].price").value(20));
    }


    // =========================================================
    // GET /api/product/{id}
    // =========================================================

    @Test
    public void testGetById() throws Exception {

        ProductDTO productDTO = new ProductDTO(
                1L,
                "Book",
                230,
                "23/03/2002",
                "23/12/2025"
        );

        when(service.get(1L))
                .thenReturn(productDTO);

        mockMvc.perform(
                        MockMvcRequestBuilders.get(
                                "/api/product/{id}",
                                1L
                        )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.productName").value("Book"))
                .andExpect(jsonPath("$.price").value(230));
    }


    // =========================================================
    // POST /api/product/create
    // =========================================================

    @Test
    public void testCreateProduct() throws Exception {

        ProductDTO productDTO = new ProductDTO(
                1L,
                "Book",
                230,
                "23/03/2002",
                "23/12/2025"
        );

        when(service.create(any(ProductDTO.class)))
                .thenReturn(productDTO);

        String productJson =
                mapper.writeValueAsString(productDTO);

        mockMvc.perform(
                        MockMvcRequestBuilders.post(
                                        "/api/product/create"
                                )
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(productJson)
                )
                .andExpect(status().isCreated())
                .andExpect(
                        content().string(
                                "Successfully Added the given product"
                        )
                );
    }


    // =========================================================
    // POST /api/product/create - Invalid Price
    // =========================================================

    @Test
    public void testCreateProductInvalidPrice() throws Exception {

        ProductDTO productDTO = new ProductDTO(
                1L,
                "Book",
                -5,
                "23/03/2002",
                "23/12/2025"
        );

        String productJson =
                mapper.writeValueAsString(productDTO);

        /*
         * If InvalidPriceException is thrown by the SERVICE,
         * mock it here.
         */
        when(service.create(any(ProductDTO.class)))
                .thenThrow(
                        new InvalidPriceException(
                                "Price must be greater than zero"
                        )
                );

        mockMvc.perform(
                        MockMvcRequestBuilders.post(
                                        "/api/product/create"
                                )
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(productJson)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error")
                        .value("Price must be greater than zero"));
    }


    // =========================================================
    // PUT /api/product/{id}
    // =========================================================

    @Test
    public void testUpdateProductById() throws Exception {

        ProductDTO updatedDto = new ProductDTO(
                1L,
                "Updated Book",
                300,
                "23/03/2002",
                "23/12/2025"
        );

        when(
                service.updateById(
                        eq(1L),
                        any(ProductDTO.class)
                )
        ).thenReturn(updatedDto);

        String requestJson =
                mapper.writeValueAsString(updatedDto);

        mockMvc.perform(
                        MockMvcRequestBuilders.put(
                                        "/api/product/{id}",
                                        1L
                                )
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson)
                )
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.productName")
                                .value("Updated Book")
                )
                .andExpect(
                        jsonPath("$.price")
                                .value(300)
                );
    }


    // =========================================================
    // DELETE /api/product/{id}
    // =========================================================

    @Test
    public void testDeleteProductById() throws Exception {

        when(service.deleteById(1L))
                .thenReturn(
                        "Successfully deleted product with ID: 1"
                );

        mockMvc.perform(
                        MockMvcRequestBuilders.delete(
                                "/api/product/{id}",
                                1L
                        )
                )
                .andExpect(status().isNoContent());
    }


    // =========================================================
    // DELETE /api/product
    // =========================================================

    @Test
    public void testDeleteAllProduct() throws Exception {

        when(service.deleteAll())
                .thenReturn(
                        "Deleted all items successfully"
                );

        mockMvc.perform(
                        MockMvcRequestBuilders.delete(
                                "/api/product"
                        )
                )
                .andExpect(status().isOk())
                .andExpect(
                        content().string(
                                "Succesfully deleted all items"
                        )
                );
    }
}