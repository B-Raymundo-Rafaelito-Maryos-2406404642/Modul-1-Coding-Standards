package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {
    private final static String PRODUCT_LIST_URL = "/product/list";

	private MockMvc mockMvc;

	@Mock
	private ProductService service;

	@InjectMocks
	private ProductController controller;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	void createProductPage_showsForm() throws Exception {
		mockMvc.perform(get("/product/create"))
				.andExpect(status().isOk())
				.andExpect(view().name("CreateProduct"))
				.andExpect(model().attributeExists("product"));
	}

	@Test
	void createProductPost_callsServiceAndRedirects() throws Exception {
		mockMvc.perform(post("/product/create")
						.param("productName", "X")
						.param("productQuantity", "10"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("list"));

		verify(service).createProduct(any(Product.class));
	}

	@Test
	void productListPage_showsProductsFromService() throws Exception {
		Product p = new Product(); p.setProductId(UUID.randomUUID());
		when(service.findAllProducts()).thenReturn(Arrays.asList(p));

		mockMvc.perform(get(PRODUCT_LIST_URL))
				.andExpect(status().isOk())
				.andExpect(view().name("ProductList"))
				.andExpect(model().attribute("products", hasSize(1)));

		verify(service).findAllProducts();
	}

	@Test
	void editProductPage_whenFound_showsEditForm() throws Exception {
		UUID id = UUID.randomUUID();
		Product p = new Product(); p.setProductId(id);
		when(service.findProductByItsId(id)).thenReturn(p);

		mockMvc.perform(get("/product/edit/{id}", id.toString()))
				.andExpect(status().isOk())
				.andExpect(view().name("EditProduct"))
				.andExpect(model().attributeExists("product"));
	}

	@Test
	void editProductPage_whenNotFound_redirectsToList() throws Exception {
		UUID id = UUID.randomUUID();
		when(service.findProductByItsId(id)).thenReturn(null);

		mockMvc.perform(get("/product/edit/{id}", id.toString()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl(PRODUCT_LIST_URL));
	}

	@Test
	void editProductPage_invalidUuid_badRequest() throws Exception {
		mockMvc.perform(get("/product/edit/{id}", "not-a-uuid"))
				.andExpect(status().isBadRequest());
	}

	@Test
	void editProductPost_callsServiceAndRedirects() throws Exception {
		UUID id = UUID.randomUUID();

		mockMvc.perform(post("/product/edit")
						.param("productId", id.toString())
						.param("productName", "Updated")
						.param("productQuantity", "99"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl(PRODUCT_LIST_URL));

		verify(service).editProduct(any(Product.class));
	}

	@Test
	void deleteProductPost_callsServiceAndRedirects() throws Exception {
		UUID id = UUID.randomUUID();

		mockMvc.perform(post("/product/delete/{id}", id.toString()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl(PRODUCT_LIST_URL));

		verify(service).deleteProductByItsId(id);
	}
}
