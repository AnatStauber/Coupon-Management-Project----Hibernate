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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anat.coupons.beans.Coupon;
import com.anat.coupons.beans.Purchase;
import com.anat.coupons.controllers.CouponController;
import com.anat.coupons.entities.CouponEntity;
import com.anat.coupons.entities.PurchaseEntity;
import com.anat.coupons.enums.CouponType;
import com.anat.coupons.exceptions.ApplicationException;


@RestController
@CrossOrigin(origins="*", allowedHeaders = "*" )
@RequestMapping(value = "/coupons")
public class CouponApi {

	@Autowired
	CouponController couponController;

	@PostMapping ("/createCoupon")
	public CouponEntity createCoupon(@RequestBody CouponEntity coupon) throws ApplicationException {
		long id = this.couponController.createCoupon(coupon);
		System.out.println("coupon #" + id + " created");
		return this.couponController.getCoupon(id);
//		http://localhost:8080/CouponProjectApisV2/rest/coupons/createCoupon
	}
	
	@DeleteMapping ("/{couponId}")
	public void deleteCoupon(@PathVariable("couponId") long couponId) throws ApplicationException {
		this.couponController.deleteCoupon(couponId);
//		 http://localhost:8080/CouponProjectApisV2/rest/coupons/76
	}

	@PutMapping ("/update")
	public void updateCoupon(@RequestBody CouponEntity coupon) throws ApplicationException {
		this.couponController.updateCoupon(coupon);
		long id = coupon.getCouponId();
		System.out.println("coupon #" + id + " was updated");
//		 http://localhost:8080/CouponProjectApisV2/rest/coupons/update
	}

	@GetMapping ("/{couponId}")
	public CouponEntity getCoupon(@PathVariable("couponId") long couponId) throws ApplicationException {
		return (couponController.getCoupon(couponId));
//		 http://localhost:8080/CouponProjectApisV2/rest/coupons/40/byId
	}

	@GetMapping ("/byType")
	public List<CouponEntity> getCouponsByType(@RequestParam("type") String type) throws ApplicationException {
		CouponType couponType = CouponType.valueOf(type);
		return (this.couponController.getCouponsByType(couponType));
//		 http://localhost:8080/CouponProjectApisV2/rest/coupons/FOOD/byType		 
	}

	@GetMapping ("/b4EndDate")
	public List<CouponEntity> getCouponsBeforeEndDate(@RequestParam("endDate") String endDate) throws ApplicationException {
		return (this.couponController.getCouponsByEndDate(endDate));
//		 http://localhost:8080/CouponProjectApisV2/rest/coupons/b4EndDate?endDate=2019-02-01
	}
	
	@GetMapping ("/byPrices")
	public List<CouponEntity> getCouponsByPrices(@RequestParam("minPrice") double minPrice,
			@RequestParam("maxPrice") double maxPrice) throws ApplicationException {
		return (this.couponController.getCouponsByPrices(minPrice, maxPrice));
//		 http://localhost:8080/CouponProjectApisV2/rest/coupons/byPrices?minPrice=20&maxPrice=100
	}
	
//	@GetMapping ("/byFilters")
//	public List<CouponEntity> getCouponByFilters(@RequestParam("type") String type, @RequestParam("endDate") String endDate, @RequestParam("minPrice") String minPrice,
//			@RequestParam("maxPrice") String maxPrice) throws ApplicationException {
//		return (this.couponController.getCouponsByFilters(type,endDate, minPrice, maxPrice));
//	}

	@GetMapping ("/getAll")
	public List<CouponEntity> getAllCoupons() throws ApplicationException {
		return this.couponController.getAllCoupons();
	}


	@GetMapping ("/companyCoupons/{companyId}")
	public List<CouponEntity> getCouponsByCompany(@PathVariable("companyId") long companyId) throws ApplicationException {
		return this.couponController.getCouponsByCompany(companyId);
//		 http://localhost:8080/CouponProjectApisV2/rest/coupons/companyActiveCoupons/14
	}

	
	@GetMapping ("/customerPurchases/{customerId}")
	public List<PurchaseEntity> getPurchasesByCustomer(@PathVariable("customerId") long customerId)
			throws ApplicationException {
		return this.couponController.getPurchasesByCustomer(customerId);
//		 	http://localhost:8080/CouponProjectApisV2/rest/coupons/customerActiveCoupons/13
	}
	
	@PostMapping ("/purchaseCoupon")
	public void purchaseCoupon(@RequestBody Purchase purchase) throws ApplicationException {
		long couponId = purchase.getCouponId();
		long customerId = purchase.getCustomerId();
		int amount = purchase.getAmount();
		this.couponController.purchaseCoupon(customerId, couponId, amount);
	}
	
	@GetMapping ("/getTypes")
	public CouponType[] getTypesEnum () throws ApplicationException  {
		return CouponType.class.getEnumConstants();
	}
	

}
