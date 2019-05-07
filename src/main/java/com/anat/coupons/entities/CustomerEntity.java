package com.anat.coupons.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Customers")
public class CustomerEntity {

	@GeneratedValue
	@Id
	private long customerId;
	
	@Column(name="customerName", nullable=true)
	private String customerName;
	
	@Column(name="customerPassword", nullable=true)
	private String customerPassword;

	@OneToMany (cascade= {CascadeType.REMOVE},mappedBy="customer")
	@com.fasterxml.jackson.annotation.JsonIgnore
	private List<PurchaseEntity> customerPurchases;
	
		
	public CustomerEntity() {
		super();
	}

	public CustomerEntity(String customername, String customerPassword) {
		this.customerName = customername;
		this.customerPassword = customerPassword;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public List<PurchaseEntity> getCustomerPurchases() {
		return customerPurchases;
	}

	public void setCustomerPurchases(List<PurchaseEntity> customerPurchases) {
		this.customerPurchases = customerPurchases;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customername) {
		this.customerName = customername;
	}

	public String getCustomerPassword() {
		return customerPassword;
	}

	public void setCustomerPassword(String customerPassword) {
		this.customerPassword = customerPassword;
	}

	@Override
	public String toString() {
		return "CustomerEntity [customerId=" + customerId + ", customerName=" + customerName + ", customerPassword="
				+ customerPassword + "]";
	}

}

	
	
	

