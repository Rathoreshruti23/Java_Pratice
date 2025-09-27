package com.shrunity.Itfirm.ServiceLogic;
import com.shrunity.Itfirm.DTO.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAllProducts();
    ProductDTO getProductById(Long id);
    ProductDTO createProduct(ProductDTO productdto);

   ProductDTO updateProduct(Long id, ProductDTO productdto);

    void deleteProduct(Long id);
}
