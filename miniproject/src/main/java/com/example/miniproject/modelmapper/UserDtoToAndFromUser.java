package com.example.miniproject.modelmapper;

import com.example.miniproject.dto.UserDto;
import com.example.miniproject.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class UserDtoToAndFromUser {
    public UserDto toUserDto(User user) {
        Long id = user.getId();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        LocalDate dateOfBirth = user.getDateOfBirth();
        String sex = user.getSex();
        String highestEducation = user.getHighestEducation();
        String address = user.getAddress();
        String country = user.getCountry();
        String state = user.getState();
        String city = user.getCity();
        return new UserDto(id, firstName, lastName, dateOfBirth, sex, highestEducation, address, country, state,city);
    }

    public User toUser(UserDto userDto) {
        Long id = userDto.getId();
        String firstName = userDto.getFirstName();
        String lastName = userDto.getLastName();
        LocalDate dateOfBirth = userDto.getDateOfBirth();
        String sex = userDto.getSex();
        String highestEducation = userDto.getHighestEducation();
        String address = userDto.getAddress();
        String country = userDto.getCountry();
        String state = userDto.getState();
        String city = userDto.getCity();
        return new User(id, firstName, lastName, dateOfBirth, sex, highestEducation, address, country, state,city);
    }




}
