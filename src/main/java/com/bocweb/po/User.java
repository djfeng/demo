package com.bocweb.po;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bocweb.core.entity.LongPKEntity;
import com.bocweb.core.enums.SexFlag;

@Table(name = "t_user")
@Entity
public class User extends LongPKEntity{
	
	private String username;
	private String password;
	private SexFlag sexFlag; 
	private Date birthday;
	private Double score;
	

	@Column(name = "UNAME", nullable = false, length = 20)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public SexFlag getSexFlag() {
		return sexFlag;
	}

	public void setSexFlag(SexFlag sexFlag) {
		this.sexFlag = sexFlag;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	@Column(name = "UPASSWORD", nullable = false, length = 32)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	

}
