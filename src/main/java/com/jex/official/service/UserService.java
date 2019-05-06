package com.jex.official.service;

import com.jex.official.entity.db.AdminUser;
import com.jex.official.repository.AdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private  final AdminUserRepository adminUserRepository;

    @Autowired
    public UserService(AdminUserRepository adminUserRepository){
        this.adminUserRepository = adminUserRepository;
    }

    public AdminUser findByUsernameAndPassword(String username, String password){
        Optional<AdminUser> optionalUser = this.adminUserRepository.findByUsernameAndPassword(username,password);
        return  optionalUser.isPresent() ? optionalUser.get() : null;
    }

}
