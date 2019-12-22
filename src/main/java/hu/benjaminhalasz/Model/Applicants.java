/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.benjaminhalasz.Model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author benjaminhalasz
 */
public class Applicants implements Serializable, Cloneable {
    private Long id;
    private String surname;
    private String firstName;
    private String phone;
    private String email;
    private String country;
    private String birthDate;
    
    
    public Applicants(Long id, String surname, String firstName, String phone, String email, String country, String birthDate) {
        this.id = id;
        this.surname = surname;
        this.firstName = firstName;
        this.phone = phone;
        this.email = email;
        this.country = country;
        this.birthDate = birthDate;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public Long getId() {
        return id;
    }

    public String getSurname() {
        return surname;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getCountry() {
        return country;
    }

    public String getBirthDate() {
        return birthDate;
    }
    @Override
    public Applicants clone() throws CloneNotSupportedException {
        return (Applicants) super.clone();
    }
    @Override
    public String toString() {
        return firstName + " " + surname;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (this.id == null) {
            return false;
        }

        if (obj instanceof Applicants && obj.getClass().equals(getClass())) {
            return this.id.equals(((Applicants) obj).id);
        }

        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + Objects.hashCode(this.id);
        return hash;
    }
}
