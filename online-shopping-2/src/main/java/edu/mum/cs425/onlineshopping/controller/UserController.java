package edu.mum.cs425.onlineshopping.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.mum.cs425.onlineshopping.entity.User;
import edu.mum.cs425.onlineshopping.repository.UserRepository;
import edu.mum.cs425.onlineshopping.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;


@Controller
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String Handler(Authentication authentication) {
        if (authentication == null || authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CUSTOMER"))) {
            return "forward:" + "/product";
        } else {
            return "forward:" + "/seller";
        }

    }

    @GetMapping("/register")
    public String showForm(User user) {
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        User userWithEnteredEmailExists = userService.findOne(user.getEmail());
        if (userWithEnteredEmailExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }

        // BindingResult
        if (bindingResult.hasErrors()) {
            
            redirectAttributes.addFlashAttribute("user", user);
            return "register";
        }
        userService.save(user);
        return "redirect:" + "/login";
    }

    @GetMapping("/profiles")
    public String showUser(User user) {
        return "/user/show";
    }

    @PostMapping("/profiles")
    public String editUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal, Model model) {
        // BindingResult
        if (bindingResult.hasErrors()) {           
            redirectAttributes.addFlashAttribute("user", user);
            return "/user/show";
        }
        //Access deny
        if (!principal.getName().equals(user.getEmail())) {
            return "redirect:" + "/403";
        }
        userService.update(user);
        model.addAttribute("msg", "Profils is updated!");
        model.addAttribute("url", "/profiles");
        return "common/success";
    }

    @GetMapping("/403")
    public String accessDeney(Model model) {
        model.addAttribute("msg", "Access denied!");
        return "/common/error";
    }
    @GetMapping("/404")
    public String notFound(Model model) {
        model.addAttribute("msg", "Page not found!");
        return "/common/error";
    }


//    @GetMapping("/music")
//    public String qqMusic(Model model) throws IOException {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
//        map.add("TransCode", "020337");
//        map.add("OpenId", "Test");
//
//        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
//
//        String url = "https://api.hibai.cn/api/index/index";
//
//        ResponseEntity<?> response = new RestTemplate().postForEntity(url, request, String.class);
//        String jsonString = (String) response.getBody();
//        ObjectMapper mapper = new ObjectMapper();
//        JsonNode json = mapper.readTree(jsonString);
//        JsonNode songs = json.get("Body");
//
//        model.addAttribute("songs", songs);
//
//        return "music";
//    }
}
