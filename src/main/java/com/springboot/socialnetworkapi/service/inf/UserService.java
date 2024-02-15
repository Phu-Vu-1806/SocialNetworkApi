package com.springboot.socialnetworkapi.service.inf;

import com.springboot.socialnetworkapi.entity.User;
import com.springboot.socialnetworkapi.payload.request.CreateNewUser;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(CreateNewUser createNewUser);
    List<User> getAllUserByFilter(String keyword, Integer pageNo, Integer pageSize, String sortBy);
}
