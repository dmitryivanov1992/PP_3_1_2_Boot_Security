package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.management.relation.RoleNotFoundException;


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

    @PostMapping("/new")
    public String createUser(@ModelAttribute("user") User user) {
        if (!user.getRoles().isEmpty()) {
            user.getRoles().forEach(role -> {
                try {
                    role.setId(roleService.findRoleByName(role.getRoleName()).getId());
                } catch (RoleNotFoundException e) {
                }
            });
        }
        userService.addUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/edit")
    public String editUser(@ModelAttribute("user") User user) {
        if (!user.getRoles().isEmpty()) {
            user.getRoles().forEach(role -> {
                try {
                    role.setId(roleService.findRoleByName(role.getRoleName()).getId());
                } catch (RoleNotFoundException e) {
                }
            });
        }
        userService.editUser(user);
        return "redirect:/admin";
    }
}
