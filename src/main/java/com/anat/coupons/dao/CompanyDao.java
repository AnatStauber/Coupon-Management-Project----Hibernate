package com.anat.coupons.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.anat.coupons.beans.Company;
import com.anat.coupons.entities.CompanyEntity;
import com.anat.coupons.enums.ErrorType;
import com.anat.coupons.exceptions.ApplicationException;
import com.anat.coupons.utils.DateUtils;
import com.anat.coupons.utils.JdbcUtils;

@Repository
public class CompanyDao {
	
	
	@PersistenceContext(unitName="coupons_project_JPA")
	private EntityManager entityManager;


	// 1. creating a new company in the BD
	@Transactional(propagation=Propagation.REQUIRED)
	public long createCompany(CompanyEntity company) throws ApplicationException,RuntimeException {

				
		try {

			entityManager.persist(company);
			return company.getCompanyId();
		}

		catch (Exception e) {
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR,
					"error in CompanyDao: failed to create company. " + DateUtils.getCurrentDateAndTime());
		} 
	}

	// 2. delete a company from the DB (and it's coupons and active purchases.)
	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteCompany(long companyId) throws ApplicationException {

		CompanyEntity company = null;
		
		try {
		
			company = entityManager.find(CompanyEntity.class, companyId);
			
			// copying the data of the soon-to-be-deleted coupons and purchases to the
			// coupons history table and the purchase history table.
//			updatePurchaseHistory(companyId, "company deleted");
//			updateCouponsHistory(companyId, "company deleted");

			// deleting all of the company's coupon purchases from the customer_coupons
			// table
			// (because it is a foreign key and the company cannot be deleted if foreign
			// keys of it exists in another tables)
//			
//			String sql = "delete from customer_coupon where companyId=?";
//
//			// deleting the company's coupon from the coupons table
//			sql = "delete from coupon where companyId=?";
//
//			// deleting the company from the companies table
//			sql = "delete from company where companyId=?";

			//deleting the company itself:
			entityManager.remove(company);

		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR,
					"failed to delete company " + DateUtils.getCurrentDateAndTime());
		} 
	}

	// 3. updating the company's info
	@Transactional(propagation=Propagation.REQUIRED)
	public void updateCompany(CompanyEntity company) throws ApplicationException {

		try {

			entityManager.merge(company);
			
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR,
					"error in CompanyDao: failed to update company" + DateUtils.getCurrentDateAndTime());
		} 
	}

	// 4.getting a full company object out of the DB based on it's Id
	@Transactional(propagation=Propagation.REQUIRED)
	public CompanyEntity getCompanyByCompanyId(long companyId) throws ApplicationException {

		
		CompanyEntity company = null;
	
		try {
//			String sql = "SELECT * FROM COMPANY WHERE companyId=?";
//
//			Query getCompanyQuery = entityManager.createQuery("SELECT * FROM COMPANY WHERE companyId=:");
//			getCompanyQuery.setParameter("companyId", companyId);
//			
//			company = (Company) getCompanyQuery.getSingleResult();
			
			company = entityManager.find(CompanyEntity.class, companyId);
			
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR,
					"error in CompanyDao: failed to get a company by ID " + DateUtils.getCurrentDateAndTime());
		} 
		return company;
	}

	// 5. producing a list of all companies in the DB
	@Transactional(propagation=Propagation.REQUIRED)
	public List<CompanyEntity> getAllCompanies() throws ApplicationException {

		List<CompanyEntity> companiesList = new ArrayList<CompanyEntity>();

		try {

			Query getAllQuery = entityManager.createQuery("SELECT c FROM CompanyEntity c");

			
			
			if (getAllQuery.getResultList().isEmpty()) {
				return null;
			}
			else {
				companiesList = getAllQuery.getResultList();
				return companiesList;
			}
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR,
					"error in CompanyDao: failed to gat list of all companies " + DateUtils.getCurrentDateAndTime());
		}

	}

	// 6. checking login and getting the company who logged in
	@Transactional(propagation=Propagation.REQUIRED)
	public CompanyEntity checkLogin(String companyName, String companyPassword) throws ApplicationException {

		CompanyEntity company = null;

		try {

			Query chackLoginQuery = entityManager.createQuery("SELECT c FROM CompanyEntity c where  c.companyName=:companyName and  c.companyPassword=:companyPassword");
			chackLoginQuery.setParameter("companyName", companyName);
			chackLoginQuery.setParameter("companyPassword", companyPassword);
			
			try {
			company = (CompanyEntity) chackLoginQuery.getSingleResult();
			} catch (NoResultException e) {
			}
			} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR,
					"error in CompanyDao: failed to check login " + DateUtils.getCurrentDateAndTime());
		} 
		
		return company;
	}

	// 7. checking if the company exists
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean doesCompanyExistById(long companyId) throws ApplicationException {

		CompanyEntity company = null;
		try {
			company=entityManager.find(CompanyEntity.class, companyId);
			if (company==null) {
				return false;
			}
			else {
				return true;
			}
			
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR,
					"error in CompanyDao:doesCompanyExistById Failed. " + DateUtils.getCurrentDateAndTime());
		} 
	}

	// 8.
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean doesCompanyNameExist(String companyName) throws ApplicationException {

			CompanyEntity company = null;
			try {
				
				Query checkNameQuery = entityManager.createQuery("SELECT c FROM CompanyEntity c where c.companyName=:companyName");
				checkNameQuery.setParameter("companyName", companyName);
				
				try {
				company = (CompanyEntity) checkNameQuery.getSingleResult();
				} catch (NoResultException e) {
					return false;
				}
				
				return true;
				
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR,
					"error in CompanyDao:doesCompanyExistByName Failed. " + DateUtils.getCurrentDateAndTime());
		}
	}

}