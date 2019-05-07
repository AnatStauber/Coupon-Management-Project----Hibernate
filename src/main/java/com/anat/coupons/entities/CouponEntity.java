package com.anat.coupons.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.anat.coupons.enums.CouponType;

@Entity
@Table(name="Coupons")
public class CouponEntity {
	
	@GeneratedValue
	@Id
	private long couponId;
	
	@Column(name="couponTitle", nullable=true)
	private String couponTitle;
	
	@Column(name="couponStartDate", nullable=true)
	private String couponStartDate;
	
	@Column(name="couponEndDate", nullable=true)
	private String couponEndDate;
	
	@Column(name="couponAmount", nullable=true)
	private int couponAmount;
	
	@Column(name="couponType", nullable=true)
	private String type; 
	
	@Column(name="couponMessage", nullable=true)
	private String couponMessage;
	
	@Column(name="couponPrice", nullable=true)
	private double couponPrice;
	
	@Column(name="couponImage", nullable=true)
	private String couponImage;
	
	@ManyToOne
	//@joinColumn
//	@Column(name="comapnyId", nullable=false)
	private CompanyEntity company;
	
	
	@Column(name="couponStatus", nullable=true)
	private String couponStatus;

	@OneToMany (cascade= {CascadeType.REMOVE},mappedBy="coupon")
	private List<PurchaseEntity> couponsPurchases;
	
//	@ManyToMany
//	private List<CustomerEntity> customersPurchased;

	private long  companyId;
	
	// constructors
	public CouponEntity() {
		super();
	}

	public CouponEntity(String couponTitle, String couponStartDate, String couponEndDate, int couponAmount,
			String couponMessage, double couponPrice, String couponImage, String type, long companyId) {

		super();

		this.couponTitle = couponTitle;
		this.couponStartDate = couponStartDate;
		this.couponEndDate = couponEndDate;
		this.couponAmount = couponAmount;
		this.couponMessage = couponMessage;
		this.couponPrice = couponPrice;
		this.couponImage = couponImage;
		this.type = type;
		this.companyId = companyId;
	}

	// getters&setters
	public long getCouponId() {
		return couponId;
	}

	public void setCouponId(long couponId) {
		this.couponId = couponId;
	}

	public String getCouponTitle() {
		return couponTitle;
	}

	public void setCouponTitle(String couponTitle) {
		this.couponTitle = couponTitle;
	}

	public String getCouponStartDate() {
		return couponStartDate;
	}

	public void setCouponStartDate(String couponStartDate) {
		this.couponStartDate = couponStartDate;
	}

	public String getCouponEndDate() {
		return couponEndDate;
	}

	public void setCouponEndDate(String couponEndDate) {
		this.couponEndDate = couponEndDate;
	}

	public int getCouponAmount() {
		return couponAmount;
	}

	public void setCouponAmount(int couponAmount) {
		this.couponAmount = couponAmount;
	}

	public String getCouponMessage() {
		return couponMessage;
	}

	public void setCouponMessage(String couponMessage) {
		this.couponMessage = couponMessage;
	}

	public double getCouponPrice() {
		return couponPrice;
	}

	public void setCouponPrice(double couponPrice) {
		this.couponPrice = couponPrice;
	}

	public String getCouponImage() {
		return couponImage;
	}

	public void setCouponImage(String couponImage) {
		this.couponImage = couponImage;
	}

	public CouponType getType() {
		return CouponType.valueOf(this.type);
	}

	public void setType(CouponType type) {
		this.type = type.name();
	}

	public CompanyEntity getCompany() {
		return company;
	}

	public void setCompany(CompanyEntity company) {
		this.company = company;
	}


	public String getCouponStatus() {
		return couponStatus;
	}

	public void setCouponStatus(String couponStatus) {
		this.couponStatus = couponStatus;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	@Override
	public String toString() {
		return "CouponEntity [couponId=" + couponId + ", couponTitle=" + couponTitle + ", couponStartDate="
				+ couponStartDate + ", couponEndDate=" + couponEndDate + ", couponAmount=" + couponAmount + ", type="
				+ type + ", couponMessage=" + couponMessage + ", couponPrice=" + couponPrice + ", couponImage="
				+ couponImage + ", company=" + company + ", couponStatus=" + couponStatus + "]";
	}

	

	
}

	
	
	
	