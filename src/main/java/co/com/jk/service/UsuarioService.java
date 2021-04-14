/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.jk.service;

import co.com.jk.dao.UsuarioDao;
import co.com.jk.domain.Rol;
import co.com.jk.domain.Usuario;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author orjue
 */
@Service("userDetailsService")
@Slf4j
public class UsuarioService implements UserDetailsService{

    @Autowired
    private UsuarioDao usuarioDao;
    
    @Override
    @Transactional(readOnly=true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioDao.findByUsername(email);
        
        if(usuario == null){
            throw new UsernameNotFoundException(email);
        }
        
        List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
        
        for(Rol rol: usuario.getRoles()){
            roles.add(new SimpleGrantedAuthority(rol.getName()));
        }
        log.info("usuario login:" + usuario);
        return new User(usuario.getUsername(), usuario.getPassword(), roles);
    }
    
    @Transactional
    public void saveUser(Usuario usuario) throws SQLIntegrityConstraintViolationException{
        Usuario user = usuarioDao.findByUsername(usuario.getUsername());
        
        if(user != null){
            throw new SQLIntegrityConstraintViolationException("email is already registered");
        }
        usuarioDao.save(usuario);
    }
}