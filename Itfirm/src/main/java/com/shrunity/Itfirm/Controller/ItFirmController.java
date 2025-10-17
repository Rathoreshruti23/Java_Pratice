package com.shrunity.Itfirm.Controller;

import com.shrunity.Itfirm.DTO.ProductDTO;
import com.shrunity.Itfirm.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@Validated //automatic validation on incoming requests
public class ItFirmController {

    @Autowired
    ProductService service;

    //read : get mapping : all product
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts(){
        List<ProductDTO> productDTO= service.getAll();
        return ResponseEntity.ok(productDTO);
    }

    //read: by id
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getById(@PathVariable Long id){
        ProductDTO productDTO=service.get(id);
        return ResponseEntity.ok(productDTO);
    }

    //create : post mapping
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Validated @RequestBody ProductDTO productDTO){
//        if(productDTO.getPrice()>100000){
//            return ResponseEntity.badRequest().body("Price too high");
//        }
        ProductDTO productDTO1=service.create(productDTO);
        return ResponseEntity.ok(productDTO1);
    }

    //update by id
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProductId(@PathVariable Long id , @RequestBody ProductDTO productDTO){
        ProductDTO productDTO1 = service.updateById(id , productDTO);
        return ResponseEntity.ok(productDTO1);
    }

    //delete by id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    //delete all product
    @DeleteMapping
    public ResponseEntity<String> deleteAllProduct(){
        service.deleteAll();
        return ResponseEntity.ok("Succesfully deleted all items");
    }
}