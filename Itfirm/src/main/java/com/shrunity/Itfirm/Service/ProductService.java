
package com.shrunity.Itfirm.Service;

        import com.shrunity.Itfirm.DTO.ProductDTO;

        import java.util.List;

public interface ProductService {
    ProductDTO create(ProductDTO productDTO);
    ProductDTO get(Long id);
    List<ProductDTO> getAll();
    ProductDTO updateById(Long id, ProductDTO productDTO);
    String deleteById(Long id);
    String deleteAll();
}
