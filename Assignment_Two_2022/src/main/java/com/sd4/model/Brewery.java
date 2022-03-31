/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sd4.model;

import java.io.Serializable;
import java.sql.Clob;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Alan.Ryan
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Brewery  extends RepresentationModel<Brewery> implements Serializable  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String code;
    private String country;
    private String phone;
    private String website;
    private String image;
    
    @Lob
    private String description;
    
    private Integer add_user;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date last_mod;
    
    private Double credit_limit;
    private String email;

    public String getName(){
        return name;
    }
    public String getWebsite() {
        return website;
    }
    public String getAddress(){
        return address1+"\n"+address2+"\n";
    }
    public String getCity(){
        return city;
    }

    public String getPhone(){
        return phone;
    }

    public long getID(){
        return id;
    }
    public String getCode(){
        return code;
    }
    public String getEmail() {
        return email;
    }

    public String getCountry(){
        return country;
    }
}
