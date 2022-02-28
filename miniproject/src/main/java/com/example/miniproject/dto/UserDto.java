package com.example.miniproject.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class UserDto implements Serializable {

    private final Long id;
    private final String firstName;
    private final String lastName;
    private final LocalDate dateOfBirth;
    private final String sex;
    private final String highestEducation;
    private final String address;
    private final String country;
    private final String state;
    private final String city;

    public UserDto(Long id, String firstName, String lastName, LocalDate dateOfBirth, String sex, String highestEducation, String address, String country, String state, String city) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.highestEducation = highestEducation;
        this.address = address;
        this.country = country;
        this.state = state;
        this.city = city;
    }



    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getSex() {
        return sex;
    }

    public String getHighestEducation() {
        return highestEducation;
    }

    public String getAddress() {
        return address;
    }

    public String getCountry() {
        return country;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto entity = (UserDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.firstName, entity.firstName) &&
                Objects.equals(this.lastName, entity.lastName) &&
                Objects.equals(this.dateOfBirth, entity.dateOfBirth) &&
                Objects.equals(this.sex, entity.sex) &&
                Objects.equals(this.highestEducation, entity.highestEducation) &&
                Objects.equals(this.address, entity.address) &&
                Objects.equals(this.country, entity.country) &&
                Objects.equals(this.state, entity.state) &&
                Objects.equals(this.city, entity.city);
    }


    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, dateOfBirth, sex, highestEducation, address, country, state, city);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "firstName = " + firstName + ", " +
                "lastName = " + lastName + ", " +
                "dateOfBirth = " + dateOfBirth + ", " +
                "sex = " + sex + ", " +
                "highestEducation = " + highestEducation + ", " +
                "address = " + address + ", " +
                "country = " + country + ", " +
                "state = " + state + ", " +
                "city = " + city + ")";
    }
}
