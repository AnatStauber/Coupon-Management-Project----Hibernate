package com.anat.coupons.apis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anat.coupons.beans.Customer;
import com.anat.coupons.controllers.CustomerController;
import com.anat.coupons.entities.CustomerEntity;
import com.anat.coupons.exceptions.ApplicationException;


	@RestController
	@CrossOrigin(origins="*", allowedHeaders = "*")
	@RequestMapping(value = "/customers")
	public class CustomerApi {

		@Autowired
		CustomerController customerController ;
		
		@PostMapping ("/createNewCustomer")
		 public void createCustomer (@RequestBody CustomerEntity customer) throws ApplicationException {
			long id = this.customerController.createCustomer(customer);	
			System.out.println("customer #"+ id+ " created");
			//http://localhost:8080/CouponProjectApisV2/rest/customers/createNewCustomer
		 }
		 
		@GetMapping ("/{customerId}")
//		@com.fasterxml.jackson.annotation.JsonIgnore
		 public CustomerEntity getCustomer(@PathVariable("customerId") long customerId) throws ApplicationException{
			 return (this.customerController.getCustomer(customerId));
			 //http://localhost:8080/CouponProjectApisV2/rest/customers/5/byId
		 }
		 
		@PutMapping ("/update")
		 public void updateCustomer (@RequestBody CustomerEntity customer)  throws ApplicationException {
			 this.customerController.updateCustomer(customer);
			 long id = customer.getCustomerId();
			 System.out.println("the customer with id : " + id + " has updated");
			 //http://localhost:8080/CouponProjectApisV2/rest/customers/update
		 } 
		 
		@DeleteMapping ("/{customerId}")
		 public void deleteCustomer (@PathVariable("customerId") long customerId) throws ApplicationException {
			 this.customerController.deleteCustomer(customerId);
//			 if (customerController.getCustomer(customerId)==null) {
//				 System.out.println("customer with cutomerId: " + customerId+ " was deleted succesfully!");
			 
			 //http://localhost:8080/CouponProjectApisV2/rest/customers/8
		 }
		 
		@GetMapping ("/getAll")
		 public List<CustomerEntity> getAllCustomers () throws ApplicationException {
			 return this.customerController.getAllCustomers();
			 //http://localhost:8080/CouponProjectApisV2/rest/customers/
		 }
		 
	 }


	

	
	
	
	
	


