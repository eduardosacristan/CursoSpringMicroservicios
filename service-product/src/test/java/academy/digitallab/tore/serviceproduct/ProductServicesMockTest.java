package academy.digitallab.tore.serviceproduct;

import academy.digitallab.tore.serviceproduct.entity.Category;
import academy.digitallab.tore.serviceproduct.entity.Product;
import academy.digitallab.tore.serviceproduct.repository.ProductRepository;
import academy.digitallab.tore.serviceproduct.service.ProductService;
import academy.digitallab.tore.serviceproduct.service.ProductServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ProductServicesMockTest {
        @Mock
        private ProductRepository productRepository;

        private ProductService productService;

        @BeforeEach
        public void setup(){
                 MockitoAnnotations.initMocks(this);
                 productService = new ProductServiceImpl(productRepository);

                 Product computer = Product.builder()
                         .id(1L)
                         .category(Category.builder().id(1L).build())
                         .name("computer")
                         .price(Double.parseDouble("12.5"))
                         .stock(Double.parseDouble("5"))
                         .build();

                Mockito.when(productRepository.findById(1L))
                        .thenReturn(Optional.of(computer));
                Mockito.when(productRepository.save(computer)).thenReturn(computer);


        }
        @Test
        public void whenValidGetID_thenReturnProduct(){
                Product found = productService.getProduct(1L);
                Assertions.assertThat(found.getName()).isEqualTo("computer");
        }

        @Test
        public void whenValidUpdateStock_ThenReturnNewStock() {
            Product newStock = productService.updateStock(1L, Double.parseDouble("5"));
            Assertions.assertThat(newStock.getStock()).isEqualTo(10);
        }
}

