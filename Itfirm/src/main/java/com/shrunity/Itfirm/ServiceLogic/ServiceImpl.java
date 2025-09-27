package com.shrunity.Itfirm.ServiceLogic;
import com.shrunity.Itfirm.DTO.ProductDTO;
import com.shrunity.Itfirm.ProductRepository.ProductRepo;
import com.shrunity.Itfirm.ServiceLogic.ProductService;
import com.shrunity.Itfirm.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service // Marks this class as a Service Bean
public class ServiceImpl implements ProductService {

    @Autowired
    private ProductRepo productRepo;

    // Convert Entity → DTO
    private ProductDTO mapToDTO(Product product) {
        return new ProductDTO(product.getId(), product.getName());
    }

    // Convert DTO → Entity
    private Product mapToEntity(ProductDTO dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        return product;
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepo.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductById(Long id) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        return mapToDTO(product);
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = mapToEntity(productDTO);
        Product savedProduct = productRepo.save(product);
        return mapToDTO(savedProduct);
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product existing = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        existing.setName(productDTO.getName());

        Product updated = productRepo.save(existing);
        return mapToDTO(updated);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        productRepo.delete(product);
    }
}
