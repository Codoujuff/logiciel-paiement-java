package com.company.payment.controller;

import com.company.payment.dto.UserRequest;
import com.company.payment.dto.UserResponse;
import com.company.payment.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String usersPage(Model model) {
        return "users";
    }

    @GetMapping("/api")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public List<UserResponse> listAll() {
        return userService.findAll();
    }

    @GetMapping("/api/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public UserResponse getById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PostMapping("/api")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<UserResponse> create(@RequestBody UserRequest request) {
        UserResponse created = userService.create(request);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/api/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public UserResponse update(@PathVariable Long id, @RequestBody UserRequest request) {
        return userService.update(id, request);
    }

    @DeleteMapping("/api/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
