package com.anat.coupons.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Companies")
public class CompanyEntity {

		@GeneratedValue
		@Id
		private long companyId;
		
		@Column(name="companyName", nullable=true)
		private String companyName;
		
		@Column(name="companyPassword", nullable=true)
		private String companyPassword;
		
		@Column(name="companyEmail", nullable=true)
		private String companyEmail;

//		@ManyToOne 
//		private CompanyEntity company;
		
		@OneToMany(cascade= {CascadeType.REMOVE},mappedBy="company")
		private List<CouponEntity> companyCoupons;

		public CompanyEntity() {
			super();
		}

		public CompanyEntity(String companyName, String companyPassword, String companyEmail) {
			super();
			this.companyName = companyName;
			this.companyPassword = companyPassword;
			this.companyEmail = companyEmail;
		}

		public CompanyEntity(long companiId, String companyName, String companyPassword, String companyEmail) {
			super();
			this.companyId = companiId;
			this.companyName = companyName;
			this.companyPassword = companyPassword;
			this.companyEmail = companyEmail;
		}
		
		public long getCompanyId() {
			return companyId;
		}

		public void setCompanyId(long companiId) {
			this.companyId = companiId;
		}

		public String getCompanyName() {
			return companyName;
		}

		public void setCompanyName(String companyName) {
			this.companyName = companyName;
		}

		public String getCompanyPassword() {
			return companyPassword;
		}

		public void setCompanyPassword(String companyPassword) {
			this.companyPassword = companyPassword;
		}

		public String getCompanyEmail() {
			return companyEmail;
		}

		public void setCompanyEmail(String companyEmail) {
			this.companyEmail = companyEmail;
		}

		@Override
		public String toString() {
			return "\n Company [companyId=" + companyId + ", companyName=" + companyName + ", companyPassword="
					+ companyPassword + ", companyEmail=" + companyEmail + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((companyEmail == null) ? 0 : companyEmail.hashCode());
			result = prime * result + (int) (companyId ^ (companyId >>> 32));
			result = prime * result + ((companyName == null) ? 0 : companyName.hashCode());
			result = prime * result + ((companyPassword == null) ? 0 : companyPassword.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			CompanyEntity other = (CompanyEntity) obj;
			if (companyEmail == null) {
				if (other.companyEmail != null)
					return false;
			} else if (!companyEmail.equals(other.companyEmail))
				return false;
			if (companyId != other.companyId)
				return false;
			if (companyName == null) {
				if (other.companyName != null)
					return false;
			} else if (!companyName.equals(other.companyName))
				return false;
			if (companyPassword == null) {
				if (other.companyPassword != null)
					return false;
			} else if (!companyPassword.equals(other.companyPassword))
				return false;
			return true;
		}

	}



