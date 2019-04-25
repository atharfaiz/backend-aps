package org.apps.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class UserLogin {
	
	@Id
	private String userId;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="role_id")
	private UserRole userRole;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;
	
	private String password;
	
	private boolean active;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date effectiveDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLogin;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date expiredDate;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Date getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}
	
	

}
