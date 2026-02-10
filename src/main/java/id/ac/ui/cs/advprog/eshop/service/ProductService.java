package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import java.util.List;
import java.util.UUID;

public interface ProductService {
    public Product createProduct(Product product);
    public List<Product> findAllProducts();
    public Product findProductByItsId(UUID productId);
    public Product editProduct(Product editedProduct);
}
