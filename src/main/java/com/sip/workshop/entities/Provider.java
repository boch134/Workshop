package com.sip.workshop.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

@Entity
public class Provider {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private int id ;
	
	@NotBlank(message = "Name is mandatory")
	@Column(name = "name")
	private String name;
	
	@NotBlank(message = "Email is mandatory")
	@Column(name = "email")
	private String email;
	
	@NotBlank(message = "Address is mandatory")
	@Column(name = "address")
	private String adress;
	
	public Provider(int id, String name, String email, String adress) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.adress = adress;
	}
	
	
	
	@Override
	public String toString() {
		return "Provider [id=" + id + ", name=" + name + ", email=" + email + ", adress=" + adress + "]";
	}



	public Provider() {
		super();
	}



	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy = "provider")
    private List<Article> articles;

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	

}
