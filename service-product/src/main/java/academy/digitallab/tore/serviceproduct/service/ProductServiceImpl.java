package academy.digitallab.tore.serviceproduct.service;

import academy.digitallab.tore.serviceproduct.entity.Category;
import academy.digitallab.tore.serviceproduct.entity.Product;
import academy.digitallab.tore.serviceproduct.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    @Override
    public List<Product> listAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product createProduct(Product product) {
        product.setStatus("CREATED");
        product.setCreateAt(new Date());
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        Product productDB = getProduct(product.getId());
        if(null == productDB) {
            return null;
        }
        productDB.setName(product.getName());
        productDB.setStatus(product.getStatus());
        productDB.setCategory(product.getCategory());
        productDB.setPrice(product.getPrice());
        productDB.setCreateAt(product.getCreateAt());
        productDB.setDescription(product.getDescription());
        productDB.setPrice(product.getPrice());
        return productRepository.save(productDB);
    }

    @Override
    public Product deleteProduct(Long id) {
        Product productDB = getProduct(id);
        if(null == productDB) {
            return null;
        }

        productDB.setStatus("DELETED");
        return productRepository.save(productDB);
    }

    @Override
    public List<Product> findByCategory(Category category){
        return productRepository.findByCategory(category);
    }

    @Override
    public Product updateStock(Long id, Double quantity) {
            Product productDB = productRepository.findById(id).orElse(null);
            if(null == productDB) {
                return null;
            }
            productDB.setStock(quantity + productDB.getStock());
            return productRepository.save(productDB);
    }
}
