package com.capgemini.productapp;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.capgemini.productapp.controller.ProductController;
import com.capgemini.productapp.entity.Product;
import com.capgemini.productapp.service.ProductService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductControllerTests {
	
	MockMvc mockmvc;
	@Mock
	public ProductService service;
	
	@InjectMocks
	private ProductController productcontroller;
	
	
	
	@Before
	public void setUP() {
		
		MockitoAnnotations.initMocks(this);
		mockmvc = MockMvcBuilders.standaloneSetup(productcontroller).build();

	}

	
	@Test
	public void testAddProduct() throws Exception {

		when(service.addProduct(Mockito.isA(Product.class))).thenReturn(new Product(123, "DELL", "Laptop", 24000));
		
		mockmvc.perform(post("/product").
					 contentType(MediaType.APPLICATION_JSON_UTF8)
					.content("{\"productId\": 123, \"productName\": \"DELL\", \"productCategory\": \"Laptop\", \"productPrice\": 24000 }")
					.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.productId").value(123))
				.andDo(print());
				
	}
	
	
	@Test
	public void testupdateProduct() throws Exception {
		when(service.updateProduct(Mockito.isA(Product.class))).thenReturn(new Product(123, "DELL", "Laptop", 24500));
		
		mockmvc.perform(put("/product").
				contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\"productId\": 123, \"productName\": \"DELL\", \"productCategory\": \"Laptop\", \"productPrice\": 24000 }")
				.accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$.productPrice").value(24500))
		.andExpect(status().isOk());
			
	}
	
	public void testFindProductById() throws Exception {
		when(service.findProductById(123)).thenReturn(new Product(124, "Dell", "Laptop", 24000));
		
		mockmvc.perform(get("/product/123").accept(MediaType.APPLICATION_JSON))
						.andDo(print())
						.andExpect(jsonPath("$.productName").value("Dell"))
						.andExpect(status().isOk());
	}
	
	@Test
	public void testDelete() throws Exception {
		
		Product product = new Product(123, "DELL", "Laptop", 24000);
	
		
		when(service.findProductById(123))
					.thenReturn(product);
		
		mockmvc.perform(delete("/products/123").accept(MediaType.APPLICATION_JSON))
						.andExpect(status().isOk())
						.andDo(print());
		
	}

	
	
}
