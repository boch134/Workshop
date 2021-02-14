package com.sip.workshop.entities;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity

public class Article {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotBlank(message = "Label is mandatory")
    @Column(name = "label")
    private String label;
    
    
 
    @Column(name = "price")
    private float price;
    
    @Column(name = "pictures")
    private ArrayList<String> pictures;


    public ArrayList<String> getPictures() {
		return pictures;
	}

	public void setPictures(ArrayList<String> pictures) {
		this.pictures = pictures;
	}

	public Article() {}

    public Article(String label, float price) {
        this.price = price;
        this.label = label;
        }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "provider_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Provider provider;
	
	
	public Provider getProvider() {
    	return provider;
    }
    
    public void setProvider(Provider provider) {
    	this.provider=provider;
    }  
    
}
