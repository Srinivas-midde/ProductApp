package com.capgemini.productapp;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

		/*product.setProductId(123);
		product.setProductName("DELL");
		product.setProductCategory("Laptop");
		product.setProductPrice(24000);*/
		
		when(service.addProduct(Mockito.isA(Product.class))).thenReturn(new Product(123, "DELL", "Laptop", 24000));
		
		mockmvc.perform(post("/product").
					 contentType(MediaType.APPLICATION_JSON)
					.content("{\"productId\": 123, \"productName\": \"DELL\", \"productCategory\": \"Laptop\", \"productPrice\": 24000 }")
					.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.productId").exists())
				.andDo(print());
				
	}
	
/*	@Test
	public void testAddGreeterWhichAddsGreeterObject() throws Exception {
		mockMvc.perform(post("/greet").
				               contentType(MediaType.APPLICATION_JSON)
				               .content("{\"title\": \"Hi\", \"message\": \"hello\"}")
				               .accept(MediaType.APPLICATION_JSON))
		                     .andExpect(status().isOk())
		                     .andExpect(jsonPath("$.title").exists())
		                     .andExpect(jsonPath("$.message").exists())
		                     .andExpect(jsonPath("$.title").value("Hi"))
		                     .andExpect(jsonPath("$.message").value("hello"))
		                     .andDo(print());		              
	}
*/
	
	
}
