package com.abcd.test.springboot.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.hamcrest.Matchers.equalTo;  
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;  
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;  

@RunWith(SpringJUnit4ClassRunner.class)  
@SpringApplicationConfiguration(classes = MockServletContext.class)  
@WebAppConfiguration
public class GreetingControllerTest {

	private MockMvc mvc;  
	  
    @Before  
    public void setUp() {  
        mvc = MockMvcBuilders.standaloneSetup(new GreetingController()).build();  
    }  
  
    @Test  
    public void getHello() throws Exception {  
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.get("/greeting").accept(MediaType.APPLICATION_JSON));  
        actions.andExpect(status().isOk());  
//      actions.andExpect(content().string(equalTo("Hello world")));  
    } 

}
