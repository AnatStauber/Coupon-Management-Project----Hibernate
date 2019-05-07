package com.anat.coupons.apis;

	import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anat.coupons.controllers.CustomerController;
import com.anat.coupons.entities.CustomerEntity;
import com.anat.coupons.exceptions.ApplicationException;


	@RestController
	@CrossOrigin(origins="*", allowedHeaders = "*" )
	@RequestMapping(value = "/register")
	public class RegisterApi {
		
			@Autowired
			CustomerController customerController;// = new CustomerController();
		
			@PostMapping 
			public void register (HttpServletRequest request, HttpServletResponse response,@RequestBody CustomerEntity customer) throws ApplicationException, ServletException, IOException {
					
				
				Long customerId = this.customerController.createCustomer(customer);
				
				if (customerId!=null) {
					response.setStatus(200);
//					response.getWriter().println("Congrats! you have registered successfully. you will now be redirected to the login page.");
//					request.getRequestDispatcher("http://localhost:8080/COUPONS_PROJECT_API's/LoginPage.html").forward(request, response);
				}
				else {
					response.setStatus(401);
//					response.getWriter().println("unfortunately, the register process was unsuccessful. please try again or contact support@couper.com for further assistance.");
				}
							
			}
				
			
	}
	
	
	
	


