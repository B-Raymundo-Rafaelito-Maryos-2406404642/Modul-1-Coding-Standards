package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product createProduct(Product product) {
        if (product.getProductId() == null) {
            product.setProductId(UUID.randomUUID());
        }
        productRepository.createProduct(product);
        return product;
    }

    @Override
    public List<Product> findAllProducts() {
        Iterator<Product> productIterator = productRepository.findAllProducts();
        List<Product> allProduct = new ArrayList<>();
        productIterator.forEachRemaining(allProduct::add);
        return allProduct;
    }

    @Override
    public Product findProductByItsId(UUID productId) {
        return productRepository.findProductByItsId(productId);
    }

    @Override
    public Product editProduct(Product editedProduct) {
        return productRepository.editProduct(editedProduct);
    }
}
