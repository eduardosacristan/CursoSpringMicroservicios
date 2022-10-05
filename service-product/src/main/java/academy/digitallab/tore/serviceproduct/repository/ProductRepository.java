package academy.digitallab.tore.serviceproduct.repository;

import academy.digitallab.tore.serviceproduct.entity.Category;
import academy.digitallab.tore.serviceproduct.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>{
    public List<Product> findByCategory(Category category);
}
