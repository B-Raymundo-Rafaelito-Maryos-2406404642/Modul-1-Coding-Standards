package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

	@Mock
	private ProductRepository repo;

	@InjectMocks
	private ProductServiceImpl service;

	@Test
	void createProduct_generatesIdWhenNull_andCallsRepo() {
		Product p = new Product();
		p.setProductName("Item A");
		p.setProductQuantity(3);

		when(repo.createProduct(any())).thenAnswer(inv -> inv.getArgument(0));

		Product result = service.createProduct(p);

		assertNotNull(result.getProductId(), "productId should be generated when null");
		verify(repo, times(1)).createProduct(result);
	}

	@Test
	void createProduct_keepsExistingId_andCallsRepo() {
		Product p = new Product();
		UUID id = UUID.randomUUID();
		p.setProductId(id);
		p.setProductName("Item B");
		p.setProductQuantity(5);

		when(repo.createProduct(any())).thenAnswer(inv -> inv.getArgument(0));

		Product result = service.createProduct(p);

		assertEquals(id, result.getProductId());
		verify(repo).createProduct(result);
	}

	@Test
	void findAllProducts_returnsListFromRepository() {
		Product p1 = new Product(); p1.setProductId(UUID.randomUUID());
		Product p2 = new Product(); p2.setProductId(UUID.randomUUID());
		Iterator<Product> it = Arrays.asList(p1, p2).iterator();

		when(repo.findAllProducts()).thenReturn(it);

		List<Product> list = service.findAllProducts();

		assertEquals(2, list.size());
		assertTrue(list.contains(p1));
		assertTrue(list.contains(p2));
		verify(repo).findAllProducts();
	}

	@Test
	void findProductByItsId_foundAndNotFound() {
		UUID id = UUID.randomUUID();
		Product p = new Product(); p.setProductId(id);

		when(repo.findProductByItsId(id)).thenReturn(p);
		assertSame(p, service.findProductByItsId(id));

		UUID missing = UUID.randomUUID();
		when(repo.findProductByItsId(missing)).thenReturn(null);
		assertNull(service.findProductByItsId(missing));
	}

	@Test
	void editProduct_successAndNegativeScenarios() {
		UUID id = UUID.randomUUID();
		Product edited = new Product();
		edited.setProductId(id);
		edited.setProductName("New name");
		edited.setProductQuantity(10);

		when(repo.editProduct(edited)).thenReturn(edited);
		Product res = service.editProduct(edited);
		assertSame(edited, res);
		verify(repo).editProduct(edited);

		// negative: repository returns null (e.g., id not found)
		Product unknown = new Product();
		unknown.setProductId(UUID.randomUUID());
		when(repo.editProduct(unknown)).thenReturn(null);
		assertNull(service.editProduct(unknown));
	}

	@Test
	void editProduct_nullInput_delegatesAndReturnsNull() {
		when(repo.editProduct(null)).thenReturn(null);
		assertNull(service.editProduct(null));
		verify(repo).editProduct(null);
	}

	@Test
	void deleteProductByItsId_trueAndFalse() {
		UUID id = UUID.randomUUID();
		when(repo.deleteProductByItsId(id)).thenReturn(true);
		assertTrue(service.deleteProductByItsId(id));
		verify(repo).deleteProductByItsId(id);

		UUID missing = UUID.randomUUID();
		when(repo.deleteProductByItsId(missing)).thenReturn(false);
		assertFalse(service.deleteProductByItsId(missing));
	}

	@Test
	void deleteProductByItsId_nullId_returnsFalse() {
		when(repo.deleteProductByItsId(null)).thenReturn(false);
		assertFalse(service.deleteProductByItsId(null));
		verify(repo).deleteProductByItsId(null);
	}
}