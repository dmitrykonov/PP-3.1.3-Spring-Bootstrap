package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.security.MyUserDetails;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminsController {

    private RoleService roleService;

    private UserService userService;

    @Autowired
    public AdminsController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping
    public String getAllUsers(User user, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
        model.addAttribute("userdetails", myUserDetails.getUser());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("listRoles", roleService.roleList());
        return "admin/admin-panel";
    }

    @GetMapping("/{id}")
    public String getUserById(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "admin/show";
    }

    @GetMapping("/new")
    public String createUserForm(User user, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
        model.addAttribute("userdetails", myUserDetails.getUser());
        model.addAttribute("listRoles", roleService.roleList());
        return "admin/new";
    }

    @PostMapping
    public String addUser(@Valid User user) {
//        if (bindingResult.hasErrors()) {
//            return "admin/new";
//        }

        userService.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String updateUserForm(@PathVariable("id") int id, Model model) {

        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("listRoles", roleService.roleList());

        return "admin/edit";
    }

    @PatchMapping("/{id}/edit")
    public String updateUser(@Valid User user, BindingResult bindingResult, @PathVariable("id") int id) {
//        if (bindingResult.hasErrors()) {
//            return "admin/edit";
//        }

        userService.addUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

}
