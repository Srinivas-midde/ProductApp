package com.capgemini.productapp;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.capgemini.productapp.entity.Product;
import com.capgemini.productapp.repository.ProductRepository;
import com.capgemini.productapp.service.impl.ProductServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTests {

	MockMvc mockmvc;
	
	@Mock
	public ProductRepository repository;
	
	@InjectMocks
	private ProductServiceImpl service;
	
	Product product;
	
	@Before
	public void setUP() {
		MockitoAnnotations.initMocks(this);
		mockmvc = MockMvcBuilders.standaloneSetup(service).build();
		product = new Product(123, "DELL", "Laptop", 24000);
	}
	
	@Test
	public void testAddproduct() throws Exception {
		when(repository.save(Mockito.isA(Product.class))).thenReturn(product);
		Product test = service.addProduct(product);
		assertEquals(product, test);
	}
	
	@Test
	public void testUpdateProduct() {
		Product test = new Product(123, "DELL", "Laptop", 24500);
		when(repository.save(Mockito.isA(Product.class))).thenReturn(product);
		Product temp =service.updateProduct(test);
		assertEquals(product, temp);
	}
	
	@Test
	public void findProductByIdTest() throws Exception {
		Optional<Product> optionalProduct = Optional.of(product);
		when(repository.findById(Mockito.isA(Integer.class))).thenReturn(optionalProduct);
		assertEquals(optionalProduct.get(), service.findProductById(123));
	}
	
	@Test
	public void deleteProductTest() throws Exception {
		service.deleteProduct(product);
		verify(repository).delete(product);
	}
	

}
