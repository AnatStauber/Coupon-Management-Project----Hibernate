package com.anat.coupons.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.anat.coupons.beans.Customer;
import com.anat.coupons.entities.CompanyEntity;
import com.anat.coupons.entities.CustomerEntity;
import com.anat.coupons.enums.ErrorType;
import com.anat.coupons.exceptions.ApplicationException;
import com.anat.coupons.utils.DateUtils;
import com.anat.coupons.utils.JdbcUtils;

@Repository
public class CustomerDao {

	@PersistenceContext(unitName="coupons_project_JPA")
	private EntityManager entityManager;
	
	
	// 1. creating a new customer in the customers table
	@Transactional(propagation=Propagation.REQUIRED)
	public long createCustomer(CustomerEntity customer) throws ApplicationException {


		try {
			entityManager.persist(customer);
			return customer.getCustomerId();


		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR,
					"error in CustomerDao: failed to create a customer" + DateUtils.getCurrentDateAndTime());
		} 
	}

	// 2. deleting a customer from the table
	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteCustomer(long customerId) throws ApplicationException {

		CustomerEntity customer = null;
		
		try {

			customer = entityManager.find(CustomerEntity.class, customerId);
			
			entityManager.remove(customer);

		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR,
					"error in CustomerDao: failed to delete a customer " + DateUtils.getCurrentDateAndTime());
		} 
	}

	// 3. updating a customer
	@Transactional(propagation=Propagation.REQUIRED)
	public void updateCustomer(CustomerEntity customer) throws ApplicationException {

		try {

			entityManager.merge(customer);
			
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR,
					"error in CustomerDao: failed to update customers information "
							+ DateUtils.getCurrentDateAndTime());
		} 
		

	}

	// 4. getting a full customer object from the table, based on its ID
	@Transactional(propagation=Propagation.REQUIRED)
	public CustomerEntity getCustomerByCustomerId(long customerId) throws ApplicationException {
		
		CustomerEntity customer = null;

		try {

			customer = entityManager.find(CustomerEntity.class, customerId);
			
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR,
					"error in CustomerDao: failed to get a customer by ID " + DateUtils.getCurrentDateAndTime());
		}
		return customer;
	}

	// 5. getting a list of all customers
	@Transactional(propagation=Propagation.REQUIRED)
	public List<CustomerEntity> getAllCustomers() throws ApplicationException {
		
		List<CustomerEntity> customersList = new ArrayList<CustomerEntity>();

		try {
			Query getAllQuery = entityManager.createQuery("SELECT c FROM CustomerEntity c");

			if (getAllQuery.getResultList().isEmpty()) {
				return null;
			}
			else {
				customersList = getAllQuery.getResultList();
				return customersList;
			}
			
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR,
					"error in CustomerDao: failed to get a list of all customers " + DateUtils.getCurrentDateAndTime());
		} 
		
	}

	// 6. checking if a login of a customer is valid and returning the customer who logged in
	@Transactional(propagation=Propagation.REQUIRED)
	public CustomerEntity checkLogin(String customerName, String customerPassword) throws ApplicationException {
		CustomerEntity customer = null;
		
		try {
			Query chackLoginQuery = entityManager.createQuery("SELECT c FROM CustomerEntity c where  c.customerName=:customerName and  c.customerPassword=:customerPassword");
			chackLoginQuery.setParameter("customerName", customerName);
			chackLoginQuery.setParameter("customerPassword", customerPassword);
			
			try {
				customer = (CustomerEntity) chackLoginQuery.getSingleResult();
			} catch (NoResultException e) {
			}

		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR,
					"error in CustomerDao: failed to activate the login method " + DateUtils.getCurrentDateAndTime());
		} 
		return customer;
	}

	// 7. checking if the customer exists
//	@Transactional(propagation=Propagation.REQUIRED)
	public boolean doesCustomerExistById(long customerId) throws ApplicationException {
		java.sql.PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;

		try {
			connection = JdbcUtils.getConnection();

			String sql = "select * from phase3.customers where customerId=? ";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, customerId);
			resultSet = preparedStatement.executeQuery();

			if (!resultSet.next()) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR,
					"error in CustomerDao:doesCustomerExistById Failed. " + DateUtils.getCurrentDateAndTime());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	// 9. checking if customer's username already exists
//	@Transactional(propagation=Propagation.REQUIRED)
	public boolean doesCustomerNameExist(String customerName) throws ApplicationException {
		java.sql.PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;

		try {
			connection = JdbcUtils.getConnection();

			String sql = "select * from phase3.customers where customerName=? ";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, customerName);
			resultSet = preparedStatement.executeQuery();

			if (!resultSet.next()) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR,
					"error in CustomerDao:doesCompanyExistByName Failed. " + DateUtils.getCurrentDateAndTime());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}


}
