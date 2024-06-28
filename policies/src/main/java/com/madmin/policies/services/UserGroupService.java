package com.madmin.policies.services;

import com.madmin.policies.object.UserGroup;
import com.madmin.policies.repository.UserGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserGroupService {
    @Autowired
    private UserGroupRepository repository;

    public UserGroup saveUserGroup(UserGroup userGroup) {
        return repository.save(userGroup);
    }

    public List<UserGroup> getAllUserGroups() {
        return repository.findAll();
    }

    public void deleteUserGroup(Long id) {
        repository.deleteById(id);
    }

    public UserGroup getUserGroupById(Long id) {
        return repository.findById(id).orElse(null);
    }
}
