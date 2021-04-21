/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.jk.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

/**
 *
 * @author orjue
 */
@Entity
@Data
@Table(name="usuario")
public class Usuario implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;
    
    @NotEmpty
    @Column(name = "first_name")
    private String firstName;
    
    @NotEmpty
    @Column(name = "last_name")
    private String lastName;
    
    @NotEmpty
    @Email(message = "Email should be valid")
    @Column(unique = true)
    private String username;
    
    @NotEmpty
    private String password;
    
    @Transient//no mapea el atributo en la tabla
    private String confirmPassword;
    
    @ManyToMany
    @JoinTable(
    name = "user_has_role", 
    joinColumns = @JoinColumn(name = "id_usuario"), 
    inverseJoinColumns = @JoinColumn(name = "id_rol"))
    private List<Rol> roles;
}
