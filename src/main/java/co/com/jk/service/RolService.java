/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.jk.service;

import co.com.jk.dao.RolDao;
import co.com.jk.domain.Rol;
import co.com.jk.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author orjue
 */
@Service
public class RolService {
    @Autowired
    private RolDao rolDao;
    
    @Transactional(readOnly = true)
    public Rol findRolByName(String name) {
        return rolDao.findByName(name);
    }
    
}
