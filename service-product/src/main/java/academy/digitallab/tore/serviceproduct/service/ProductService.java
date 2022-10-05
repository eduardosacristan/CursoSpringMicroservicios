package academy.digitallab.tore.serviceproduct.service;

import academy.digitallab.tore.serviceproduct.entity.Category;
import academy.digitallab.tore.serviceproduct.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProductService {
    public List<Product> listAllProducts();
    public Product getProduct(Long id);
    public Product createProduct(Product product);
    public Product updateProduct(Product product);
    public Product deleteProduct(Long id);
    public List<Product> findByCategory(Category category);
    public Product updateStock(Long id, Double quantity);

}
