package com.ganesh.blog.services;

import com.ganesh.blog.entities.User;
import com.ganesh.blog.payload.UserDto;

import java.util.List;

public interface UserService {
  UserDto registerNewUser(UserDto user);

  UserDto createUser(UserDto userDto);

  UserDto createUser(User user);

  UserDto updateUser(UserDto user, Long userId);

  UserDto getUserById(Long userId);

  List<UserDto> getAllUsers();

  void deleteUser(Long userId);

  User getUserByEmail(String email);  // <- Correct method declaration
}
