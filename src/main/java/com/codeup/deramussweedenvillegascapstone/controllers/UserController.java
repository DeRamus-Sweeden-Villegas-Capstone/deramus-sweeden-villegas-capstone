package com.codeup.deramussweedenvillegascapstone.controllers;

import com.codeup.deramussweedenvillegascapstone.models.User;
import com.codeup.deramussweedenvillegascapstone.repositories.NoteRepository;
import com.codeup.deramussweedenvillegascapstone.repositories.PropertyRepository;
import com.codeup.deramussweedenvillegascapstone.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    private final UserRepository userDao;
    private final NoteRepository noteDao;
    private final PropertyRepository propDao;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userDao, NoteRepository noteDao, PropertyRepository repoDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.noteDao = noteDao;
        this.propDao = repoDao;
        this.passwordEncoder = passwordEncoder;
    }
    @GetMapping("/register")
    public String showSignupForm(Model model){
        model.addAttribute("user", new User());
        return "users/register";
    }
    @PostMapping("/register")
    public String saveUser(@ModelAttribute Model model, @Validated User user, Errors validation ) {
        if (userDao.findByUsername(user.getUsername()) != null) {
            validation.rejectValue(
                    "username",
                    "user.username",
                    "This username is already taken."
            );
        }
        if (user.getUsername().length() > 17 || user.getUsername().length() < 3) {
            validation.rejectValue(
                    "username",
                    "user.username",
                    "Enter a username between 3 and 17 characters long."
            );
        }
        if (userDao.findByEmail(user.getEmail()) != null) {
            validation.rejectValue(
                    "email",
                    "user.email",
                    "This email already exists."
            );
        }
        if (validation.hasErrors()) {
            model.addAttribute("user", user);
            model.addAttribute("errors", validation);

            return "user/register";
        } else {
            String hash = passwordEncoder.encode(user.getPassword());
            user.setPassword(hash);
            userDao.save(user);
            return "redirect:/login";
        }
    }

    @GetMapping("/profile")
    public String showProfile(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//        Property prop = propDao.findById(user.getId());
//        Property prop = propDao.findAll();
//        model.addAttribute("props", propDao.findByUser_Id(user.getId()));
//        System.out.println("propDao.searchByPropertyLike(user.getId()) = " + propDao.searchByPropertyLike(user.getId()));
//        model.addAttribute("props", propDao.searchByPropertyLike(user.getId()));
            model.addAttribute("props", propDao.findAll());
            return "users/profile";
        }

    public String showProfile()
    {return "users/profile";}



//    the following code is for editing user's profile details and deleting their account:
    @GetMapping("/profile/edit")
    public String showEditProfileForm(Model model) {
        return "users/editProfile"; //this is the html name
    }

    @PostMapping("/profile/edit")
    public String saveEditedProfile(@RequestParam(name="name") String username, @RequestParam(name="email") String email) {
        User user = userDao.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        user.setUsername(username);
        user.setEmail(email);
        userDao.save(user);
        return "redirect:/profile";
    }

//    not using this functionality currently?
    @PostMapping("/profile/delete")
    public String deleteProfile() {
        User user = userDao.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        userDao.delete(user);
        SecurityContextHolder.clearContext();
        return "redirect:/";
    }

    }




