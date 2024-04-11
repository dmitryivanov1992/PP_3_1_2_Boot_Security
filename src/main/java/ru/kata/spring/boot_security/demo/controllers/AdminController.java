package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Arrays;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String indexPage(Model model) {
        model.addAttribute("users", userService.listUsers());
        return "admin/index";
    }

    @GetMapping("/edit")
    public String editUser(@RequestParam int id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("rolesList", roleService.listRoles());
        return "admin/edit";
    }

    @GetMapping("/remove")
    public String removeUser(@RequestParam int id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("rolesList", roleService.listRoles());
        return "admin/new";
    }

    @PostMapping("/createOrEditUser")
    public String createOrEditUser(@ModelAttribute("user") User user,
                                   @RequestParam(value = "roles", required = false) Role[] roles,
                                   @RequestParam(value = "passwordInput", required = false) String password) {
        if (roles != null) {
            Arrays.stream(roles).forEach(user::setRole);
        }
        if (password != null && !password.equals("a7eUqmEr=WvKfYcWA@?k7)?08=*mVYah8)FYz+hohuMBt]zZGusQs^h^7dt6KehG9yC_kr6Z_qkHTUn>")) {
            user.setPassword(password);
        }
        userService.addOrEditUser(user);
        return "redirect:/admin";
    }

}
