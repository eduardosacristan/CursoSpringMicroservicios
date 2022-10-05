package academy.digitallab.tore.serviceproduct.controller;

import academy.digitallab.tore.serviceproduct.entity.Category;
import academy.digitallab.tore.serviceproduct.entity.Product;
import academy.digitallab.tore.serviceproduct.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.Binding;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductService productService;

//    @GetMapping
//    public ResponseEntity<List<Product>> listProduct() {
//        List<Product> products = productService.listAllProducts();
//
//        if(products.isEmpty()) {
//            return ResponseEntity.noContent().build();
//        }
//
//        return ResponseEntity.ok(products);
//    }

    @GetMapping
    public ResponseEntity<List<Product>> listProduct(@RequestParam(name = "categoryId", required = false) Long categoryId) {
        List<Product> products = new ArrayList<>();
        if(null == categoryId) {
            products = productService.listAllProducts();

            if(products.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
        } else {
            products = productService.findByCategory(Category.builder().id(categoryId).build());

            if(products.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> getProduct (@PathVariable("id") Long productId) {
        Product product;
        if(null == productId) {
            return ResponseEntity.notFound().build();
        } else {
            product = productService.getProduct(productId);
            if(null != product)
                return ResponseEntity.ok(product);
            else
                return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Product> postProduct (@Valid @RequestBody Product product, BindingResult result) {
        if(result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, formatMessage(result));
        }
        Product newProduct = productService.createProduct(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Product> putProduct (@PathVariable("id") Long id, @RequestBody Product product) {
        product.setId(id);
        Product productDB = productService.updateProduct(product);
        if(null == productDB) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(product);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Product> deleteProduct (@PathVariable("id") Long id){
        if(null != id) {
            Product product = productService.deleteProduct(id);
            if(null != product) {
                return ResponseEntity.ok(product);
            } else {
                return ResponseEntity.notFound().build();
            }
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping(value = "/{id}/stock")
    public ResponseEntity<Product> updateStock(@PathVariable("id") Long id, @RequestParam(name="stock", required = true) Double quatity) {
        Product productUpdated = productService.updateStock(id, quatity);
        if(null != productUpdated) {
            return ResponseEntity.ok(productUpdated);
        }
        else {
            return ResponseEntity.notFound().build();

        }
    }

    private String formatMessage (BindingResult result) {
        List<Map<String, String>> errors = result.getFieldErrors().stream()
                .map(err -> {
                    Map<String, String> error = new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());
        ErrorMessages errorMessages = ErrorMessages.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(errorMessages);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonString;
    }
}
