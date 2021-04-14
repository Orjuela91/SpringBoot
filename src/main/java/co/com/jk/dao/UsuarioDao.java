/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.jk.dao;

import co.com.jk.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author orjue
 */
public interface UsuarioDao extends JpaRepository<Usuario, Long>{
    Usuario findByUsername(String username);
}
