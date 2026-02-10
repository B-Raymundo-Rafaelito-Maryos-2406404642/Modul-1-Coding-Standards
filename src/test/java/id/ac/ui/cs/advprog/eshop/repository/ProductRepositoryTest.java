package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId(UUID.fromString("eb5589f-1c39-46e0-8860-71af6af63bd6"));
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.createProduct(product);

        Iterator<Product> productIterator = productRepository.findAllProducts();
        assertTrue(productIterator.hasNext());

        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAllProducts();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId(UUID.fromString("eb5589f-1c39-46e0-8860-71af6af63bd6"));
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.createProduct(product1);

        Product product2 = new Product();
        product2.setProductId(UUID.fromString("a0f9de46-90b1-437d-a0bf-d0821dde9096"));
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.createProduct(product2);

        Iterator<Product> productIterator = productRepository.findAllProducts();
        assertTrue(productIterator.hasNext());

        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());

        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());

        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindByIdAndFindByName() {
        Product product = new Product();
        UUID id = UUID.randomUUID();
        product.setProductId(id);
        product.setProductName("Sauce X");
        product.setProductQuantity(7);
        productRepository.createProduct(product);

        Product byId = productRepository.findProductByItsId(id);
        assertNotNull(byId);
        assertEquals(product.getProductName(), byId.getProductName());

        Product byName = productRepository.findProductByItsName("Sauce X");
        assertNotNull(byName);
        assertEquals(id, byName.getProductId());

        // negative: name not present
        assertNull(productRepository.findProductByItsName("NonExistingName"));
    }

    @Test
    void testEditProduct_success_and_failures() {
        Product original = new Product();
        UUID id = UUID.randomUUID();
        original.setProductId(id);
        original.setProductName("Original");
        original.setProductQuantity(2);
        productRepository.createProduct(original);

        Product edited = new Product();
        edited.setProductId(id);
        edited.setProductName("Edited");
        edited.setProductQuantity(20);

        Product result = productRepository.editProduct(edited);
        assertNotNull(result);
        assertEquals("Edited", result.getProductName());
        assertEquals(20, result.getProductQuantity());

        // negative: editing non-existing id
        Product unknown = new Product();
        unknown.setProductId(UUID.randomUUID());
        unknown.setProductName("X");
        unknown.setProductQuantity(1);
        assertNull(productRepository.editProduct(unknown));

        // negative: editedProduct null or id null
        assertNull(productRepository.editProduct(null));
        Product noId = new Product();
        noId.setProductName("NoId");
        noId.setProductQuantity(1);
        assertNull(productRepository.editProduct(noId));
    }

    @Test
    void testDeleteProductByItsId_positive_negative_and_null() {
        Product p = new Product();
        UUID id = UUID.randomUUID();
        p.setProductId(id);
        p.setProductName("ToDelete");
        p.setProductQuantity(5);
        productRepository.createProduct(p);

        // positive
        assertTrue(productRepository.deleteProductByItsId(id));
        // now it's gone
        assertFalse(productRepository.deleteProductByItsId(id));

        // negative: null id
        assertFalse(productRepository.deleteProductByItsId(null));
    }
}