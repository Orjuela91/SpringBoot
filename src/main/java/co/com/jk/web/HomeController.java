/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.jk.web;

import co.com.jk.domain.Rol;
import co.com.jk.domain.Usuario;
import co.com.jk.service.RolService;
import co.com.jk.service.UsuarioService;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author orjue
 */
@Controller
@Slf4j
public class HomeController {

    @Autowired
    private RolService rolService;
    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping("/")
    public String index(@AuthenticationPrincipal User user) {
        return user == null ? "welcome" : "redirect:/dashboard";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @GetMapping("/register")
    public String register(@Valid Usuario usuario, Model model) {
        model.addAttribute("user", usuario);
        return "register";
    }

    @PostMapping("/create-user")
    public String createUser(@Valid Usuario usuario, Model model){
        
        HashMap<String, String> errors = new HashMap<String, String>();
        model.addAttribute("user", usuario);
        
        if(!usuario.getPassword().equals(usuario.getConfirmPassword())){
            
            errors.put("password", "passwords must be equals");
            model.addAttribute("errors", errors);
            
            return "register";
        }
        
        usuario.setPassword(new BCryptPasswordEncoder().encode(usuario.getPassword()));
        
        List<Rol> roles = new ArrayList<Rol>();
        
        roles.add(rolService.findRolByName("ROLE_USER"));
        
        usuario.setRoles(roles);
        
        try {
            usuarioService.saveUser(usuario);
        } catch (SQLIntegrityConstraintViolationException  ex) {
            
            errors.put("email", ex.getMessage());
            model.addAttribute("errors", errors);
            
            return "register";
        }
         
        return "redirect:/login";
    }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal User user) {
        return "/layout/dashboard";
    }

    @GetMapping("/dashboard/buttons")
    public String buttons(@AuthenticationPrincipal User user) {
        return "/layout/buttons";
    }

    @GetMapping("/dashboard/cards")
    public String cards(@AuthenticationPrincipal User user) {
        return "/layout/cards";
    }

    @GetMapping("/dashboard/utilities-color")
    public String utilitiesColor(@AuthenticationPrincipal User user) {
        return "/layout/utilities-color";
    }

    @GetMapping("/dashboard/utilities-border")
    public String utilitiesBorder(@AuthenticationPrincipal User user) {
        return "/layout/utilities-border";
    }

    @GetMapping("/dashboard/utilities-animation")
    public String utilitiesAnimation(@AuthenticationPrincipal User user) {
        return "/layout/utilities-animation";
    }

    @GetMapping("/dashboard/utilities-other")
    public String utilitiesOther(@AuthenticationPrincipal User user) {
        return "/layout/utilities-other";
    }

    @GetMapping("/dashboard/page-login")
    public String pageLogin(@AuthenticationPrincipal User user) {
        return "/layout/page-login";
    }

    @GetMapping("/dashboard/page-register")
    public String pageRegister(@AuthenticationPrincipal User user) {
        return "/layout/page-register";
    }

    @GetMapping("/dashboard/page-forgot-password")
    public String pageForgotPassword(@AuthenticationPrincipal User user) {
        return "/layout/page-forgot-password";
    }

    @GetMapping("/dashboard/page-404")
    public String page404(@AuthenticationPrincipal User user) {
        return "/layout/page-404";
    }

    @GetMapping("/dashboard/page-blank")
    public String pageBlank(@AuthenticationPrincipal User user) {
        return "/layout/page-blank";
    }

    @GetMapping("/dashboard/charts")
    public String pageCharts(@AuthenticationPrincipal User user) {
        return "/layout/charts";
    }

    @GetMapping("/dashboard/tables")
    public String pageTables(@AuthenticationPrincipal User user) {
        return "/layout/tables";
    }
}
