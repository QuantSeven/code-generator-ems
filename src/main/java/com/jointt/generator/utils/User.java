package com.jointt.generator.utils;

import java.io.Serializable;

public class User implements Serializable {
	private String userId;
	private String name;
	private String address;
	private String sex;

	public User(String userId, String name, String address, String sex) {
		super();
		this.userId = userId;
		this.name = name;
		this.address = address;
		this.sex = sex;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

}