package com.anat.coupons.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.anat.coupons.beans.Coupon;
import com.anat.coupons.entities.CompanyEntity;
import com.anat.coupons.entities.CouponEntity;
import com.anat.coupons.entities.CustomerEntity;
import com.anat.coupons.entities.PurchaseEntity;
import com.anat.coupons.enums.CouponType;
import com.anat.coupons.enums.ErrorType;
import com.anat.coupons.exceptions.ApplicationException;
import com.anat.coupons.utils.DateUtils;
import com.anat.coupons.utils.JdbcUtils;

@Repository
public class CouponsDao {
	
	@PersistenceContext(unitName="coupons_project_JPA")
	private EntityManager entityManager;

	private CompanyDao companyDao;
	
	// 1. creating a new coupon in the DB , that will hold the information from a
	// coupon instance
	@Transactional(propagation=Propagation.REQUIRED)
	public long createCoupon(CouponEntity coupon) throws ApplicationException {

		CompanyEntity company = null;

		try {
			//getting the company from the companyId given from the client
			company = companyDao.getCompanyByCompanyId(coupon.getCompanyId());
			
			//inserting the company to the coupon
			coupon.setCompany(company);
			
			//creating the coupon
			entityManager.persist(coupon);
			return coupon.getCouponId();
			
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR,
					"failed to create coupon " + DateUtils.getCurrentDateAndTime());
		} 
	}

	// 2. deleting a coupon from the DB based on it's ID
	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteCoupon(long couponId) throws ApplicationException {

		CouponEntity coupon = null;
		
		
		try {
			
			coupon = entityManager.find(CouponEntity.class, couponId);
			entityManager.remove(coupon);

		} catch (Exception e) {
//			
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR,
					"failed to delete coupon " + DateUtils.getCurrentDateAndTime());

		} 
	}

	// 2b. delete expired coupons (in the coupons table and in the customer_coupons
	// table.
//	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteExpiredCoupons() throws ApplicationException {

		String currentDate = DateUtils.getCurrentDate();
		CouponEntity coupon = null;
//		List<CouponEntity> expiredCoupons = new ArrayList<CouponEntity>();

		try {

			// putting all expired coupons in the resultSet in order to delete
			Query getExpiredCoupons = entityManager.createQuery("select c from CouponEntity c where c.couponEndDate<:endDate");
			getExpiredCoupons.setParameter("endDate", currentDate);
				
			if (getExpiredCoupons.getResultList().isEmpty()) {
				return;
			}
			else {
				List<CouponEntity> expiredCoupons  = getExpiredCoupons.getResultList();
				
				Iterator it = expiredCoupons.iterator();
				
				while (it.hasNext()) {
					coupon = (CouponEntity) it.next();
					this.deleteCoupon(coupon.getCouponId());
				}
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR,
					"couponsDao:deleteExpiredCoupons failed. " + DateUtils.getCurrentDateAndTime());
		} 
	}

	// 3. updating coupon's info
	@Transactional(propagation=Propagation.REQUIRED)
	public void updateCoupon (CouponEntity coupon) throws ApplicationException {


		try {
			entityManager.merge(coupon);
			
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR,
					"failed to update coupon " + DateUtils.getCurrentDateAndTime());
		}
	}

	// 9. get coupon: returning a full coupon object, based on a given ID
	@Transactional(propagation=Propagation.REQUIRED)
	public CouponEntity getCouponByCouponId(long couponId) throws ApplicationException {

		CouponEntity coupon = null;

		try {
			coupon = entityManager.find(CouponEntity.class, couponId);

		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR,
					"failed to get coupon by ID " + DateUtils.getCurrentDateAndTime());
		} 
		return coupon;
	}

	// 10. returning all active coupons of a certain type
	@Transactional(propagation=Propagation.REQUIRED)
	public List<CouponEntity> getCouponsByType(CouponType type) throws ApplicationException {

		CouponEntity coupon = null;
		List<CouponEntity> couponsList = new ArrayList<CouponEntity>();
		String couponType = type.name();

		try {
			Query getByType = entityManager.createQuery("SELECT c FROM CouponEntity c where  c.couponType=:couponType ");
			getByType.setParameter("couponType", couponType);
			
			if (getByType.getResultList().isEmpty()) {
				return null;
			}
			else {
				couponsList = getByType.getResultList();
				return couponsList;
			}
			
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR,
					"failed to get coupon by type " + DateUtils.getCurrentDateAndTime());
		} 

	}

	// 10b. get all active coupons by endDate
	@Transactional(propagation=Propagation.REQUIRED)
	public List<CouponEntity> getCouponsByEndDate(String endDate) throws ApplicationException {

		List<CouponEntity> couponsList = new ArrayList<CouponEntity>();
		CouponEntity coupon = null;

		try {

			Query getByDate = entityManager.createQuery("SELECT c FROM CouponEntity c where c.couponEndDate <:couponEndDate ");
			getByDate.setParameter("couponEndDate", endDate);
			
			if (getByDate.getResultList().isEmpty()) {
				return null;
			}
			else {
				couponsList = getByDate.getResultList();
				return couponsList;
			}

		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR,
					"couponsDao:getCouponsByEndDate failed. " + DateUtils.getCurrentDateAndTime());
		}

	}

	// 10c. get list ofall active coupons between a range of prices
	@Transactional(propagation=Propagation.REQUIRED)
	public List<CouponEntity> getCouponsByPrices(double minPrice, double maxPrice) throws ApplicationException {

		List<CouponEntity> couponsList = new ArrayList<CouponEntity>();
		CouponEntity coupon = null;

		try {
			Query getByprices = entityManager.createQuery("SELECT c FROM CouponEntity c where c.couponPrice>=:minPrice and c.couponPrice<=:maxprice");
			getByprices.setParameter("minPrice", minPrice);
			getByprices.setParameter("maxprice", maxPrice);
			
			if (getByprices.getResultList().isEmpty()) {
				return null;
			}
			else {
				couponsList = getByprices.getResultList();
				return couponsList;
			}
			
			
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR,
					"couponsDao:getCouponsByPrices failed. " + DateUtils.getCurrentDateAndTime());
		}
	}

	

	// 11. returning a list of all active coupons in the system
	@Transactional(propagation=Propagation.REQUIRED)
	public List<CouponEntity> getAllCoupons() throws ApplicationException {
		
		List<CouponEntity> couponsList = new ArrayList<CouponEntity>();

		try {

			Query getAll = entityManager.createQuery("SELECT c FROM CouponEntity c");
			
			if (getAll.getResultList().isEmpty()) {
				return null;
			}
			else {
				couponsList = getAll.getResultList();
				return couponsList;
			}
			

		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR,
					"failed to creat a list of all coupons " + DateUtils.getCurrentDateAndTime());
		}

	}

	

	// 12. returning a list of all active coupons of a certain company
	@Transactional(propagation=Propagation.REQUIRED)
	public List<CouponEntity> getCouponsByCompany(long companyId) throws ApplicationException {

		List<CouponEntity> couponsList = new ArrayList<CouponEntity>();

		try {

			Query getCompanyCoupons = entityManager.createQuery("SELECT c FROM CouponEntity c where c.companyId =:companyId ");
			getCompanyCoupons.setParameter("companyId", companyId);
			
			if (getCompanyCoupons.getResultList().isEmpty()) {
				return null;
			}
			else {
				couponsList = getCompanyCoupons.getResultList();
				return couponsList;
			}

		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR,
					"failed to create a list of all coupons of a company " + DateUtils.getCurrentDateAndTime());
		}
	}

	
//************************************** write again, need to add a join table ******************************************************************
	// 13. returning a list of all active coupons of a certain customer
	@Transactional(propagation=Propagation.REQUIRED)
	public List<PurchaseEntity> getPurchasesByCustomer(long customerId) throws ApplicationException {

		List<PurchaseEntity> purchasesList = new ArrayList<PurchaseEntity>();

		try {
			Query getCustomerCoupons = entityManager.createQuery("SELECT p FROM PurchaseEntity p where p.customer_customerId=:customerId ");
			getCustomerCoupons.setParameter("customerId", customerId);
			
			if (getCustomerCoupons.getResultList().isEmpty()) {
				return null;
			}
			else {
				purchasesList = getCustomerCoupons.getResultList();
				return purchasesList;
			}


		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR,
					"failed to create a list of all active coupons of a customer " + DateUtils.getCurrentDateAndTime());
		}

	}


	// 14. updating the customer_coupon table when a customer purchased a new
	// coupon.
	@Transactional(propagation=Propagation.REQUIRED)
	public void purchaseCoupon(long customerId, long couponId, int purchasedAmount) throws ApplicationException {

		CouponEntity coupon = this.getCouponByCouponId(couponId);
		int PreviousCouponAmount = coupon.getCouponAmount(); 
		CompanyEntity company = coupon.getCompany();
		CustomerEntity customer = null;

		try {

			// subtracting the purchased coupons from the remaining amount in the coupons table
			coupon.setCouponAmount(PreviousCouponAmount - purchasedAmount);
			this.updateCoupon(coupon);

			//getting the customerEntity
			customer = entityManager.find(CustomerEntity.class, customerId);

			//creating a new PurchaseEntity Object
			PurchaseEntity purchase = new PurchaseEntity(coupon,customer,company, purchasedAmount);
			
			// inserting a new row to the customer_coupon table , when a new purchase has been made
			entityManager.persist(purchase);


		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR,
					"failed to activate the purchase method " + DateUtils.getCurrentDateAndTime());
		}
	}

	// 15. checking if coupon exists by its name and doesn't repeat itself in the
	// rest of the coupons of the company
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean doesCouponTitleExist(String title, long companyId) throws ApplicationException {

		CouponEntity coupon = null;
		
		try {

			String sql = "select * from coupon where couponTitle=? and companyId=? ";
			Query checkTitleQuery = entityManager.createQuery("SELECT c FROM CouponEntity c where c.couponTitle=:title and c.company_companyId=:companyId");
			checkTitleQuery.setParameter("title", title);
			checkTitleQuery.setParameter("companyId", companyId);
			
			try {
				coupon = (CouponEntity) checkTitleQuery.getSingleResult();
			} catch (NoResultException e) {
				return false;
			}
			
			return true;
			

		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR,
					"error in CouponsDao:doesCouponExistByName Failed.");
		}
	}

	// 16. checking if a coupon exists by its id
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean doesCouponExistById(long couponId) throws ApplicationException {    

		CouponEntity coupon = null;
		try {

			coupon =entityManager.find(CouponEntity.class, couponId);
			if (coupon==null) {
				return false;
			}
			else {
				return true;
			}
			

		
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR,
					"error in CouponsDao:doesCouponExistById Failed.");
		}
	}
}

	