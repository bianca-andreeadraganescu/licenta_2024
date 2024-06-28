package com.madmin.policies.controller;

import com.madmin.policies.object.UserGroup;
import com.madmin.policies.services.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usergroups")
public class UserGroupController {
    @Autowired
    private UserGroupService service;

    @PostMapping
    public ResponseEntity<UserGroup> createUserGroup(@RequestBody UserGroup userGroup) {
        UserGroup savedUserGroup = service.saveUserGroup(userGroup);
        return ResponseEntity.ok(savedUserGroup);
    }

    @GetMapping
    public ResponseEntity<List<UserGroup>> getAllUserGroups() {
        List<UserGroup> userGroups = service.getAllUserGroups();
        return ResponseEntity.ok(userGroups);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserGroup(@PathVariable Long id) {
        service.deleteUserGroup(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserGroup> getUserGroupById(@PathVariable Long id) {
        UserGroup userGroup = service.getUserGroupById(id);
        if (userGroup == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userGroup);
    }
}
