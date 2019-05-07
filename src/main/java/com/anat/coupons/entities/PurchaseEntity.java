package com.anat.coupons.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="purchases")
public class PurchaseEntity {

	@GeneratedValue
	@Id
	private long purchaseId;
	
	@ManyToOne 
	private CouponEntity coupon;
	
	@ManyToOne
	private CustomerEntity customer;
	
	@ManyToOne
	private CompanyEntity company;
	
	@Column(name="amount", nullable=false)
	private int amount;

//	@ManyToMany(mappedBy="customersPurchased")
//	private List<CustomerEntity> customersPurchased;

	
	public PurchaseEntity() {
		super();
	}

	public PurchaseEntity( CouponEntity coupon, CustomerEntity customer, CompanyEntity company,
			int amount) {
		super();
		this.coupon = coupon;
		this.customer = customer;
		this.company = company;
		this.amount = amount;
	}


	public long getPurchaseId() {
		return purchaseId;
	}


	public void setPurchaseId(long purchaseId) {
		this.purchaseId = purchaseId;
	}


	public CouponEntity getCoupon() {
		return coupon;
	}


	public void setCoupon(CouponEntity coupon) {
		this.coupon = coupon;
	}


	public CustomerEntity getCustomer() {
		return customer;
	}


	public void setCustomer(CustomerEntity customer) {
		this.customer = customer;
	}


	public CompanyEntity getCompany() {
		return company;
	}


	public void setCompany(CompanyEntity company) {
		this.company = company;
	}


	public int getAmount() {
		return amount;
	}


	public void setAmount(int amount) {
		this.amount = amount;
	}


	@Override
	public String toString() {
		return "PurchaseEntity [purchaseId=" + purchaseId + ", coupon=" + coupon + ", customer=" + customer
				+ ", company=" + company + ", amount=" + amount + "]";
	}

	
}

