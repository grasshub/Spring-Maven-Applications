package com.greenfield.springmvc.form.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
	// form:hidden - hidden value
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	// form:input - textbox
	private String name;

	// form:input - textbox
	private String email;

	// form:textarea - textarea
	private String address;

	// form:input - password
	private String password;

	// form:input - password
	private String confirmPassword;

	// form:checkbox - single checkbox
	private boolean newsletter;

	// form:checkboxes - multiple checkboxes
	// Need to annotation collection of basic/embeddable type field with ElementCollection in order to generate second reference table for items
	// You could also annotate with @CollcetionTable to specify the second reference table name.
	@ElementCollection
	private List<String> framework = new ArrayList<>();

	// form:radiobutton - radio button
	private String sex;

	// form:radiobuttons - radio button
	private Integer number;

	// form:select - form:option - dropdown - single select
	private String country;

	// form:select - multiple=true - dropdown - multiple select
	@ElementCollection
	private List<String> skill = new ArrayList<>();
	
	public User() {}

	// Check if this is for New of Update
	public boolean isNew() {
		return (this.id == null);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public boolean isNewsletter() {
		return newsletter;
	}

	public void setNewsletter(boolean newsletter) {
		this.newsletter = newsletter;
	}

	public List<String> getFramework() {
		return framework;
	}

	public void setFramework(List<String> framework) {
		this.framework = framework;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public List<String> getSkill() {
		return skill;
	}

	public void setSkill(List<String> skill) {
		this.skill = skill;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", address=" + address + ", password="
				+ password + ", confirmPassword=" + confirmPassword + ", newsletter=" + newsletter + ", framework="
				+ framework + ", sex=" + sex + ", number=" + number + ", country=" + country + ", skill=" + skill + "]"
				+ isNew();
	}

}
