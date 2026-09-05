package com.shrunity.Itfirm.Controller;


import com.shrunity.Itfirm.DTO.ProductDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/product")
public class ControllerV1 extends ItFirmController{
    @Override
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = service.getAll();
        products.forEach(p -> p.setProductName("Enhanced field available in V2"));
        return ResponseEntity.ok(products);
    }
}
