package br.com.energycontrol.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.energycontrol.DAO.IUser;
import br.com.energycontrol.model.User;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUser dao;

    @GetMapping
    public List<User> listaUsuario() {
        return (List<User>) dao.findAll();
    }

    @PostMapping
    public User criarUsuario(@RequestBody User usuario) {
        User usuarioNovo = dao.save(usuario);
        return usuarioNovo;
    }

    @PutMapping 
    public User editarUsuario(@RequestBody User usuario) {
        User usuarioNovo = dao.save(usuario); 
        return usuarioNovo;
    }

    @DeleteMapping("/{id}")
    public Optional<User> excluirUsuario(@PathVariable Long id) { 
         Optional<User> usuario = dao.findById(id);
         dao.deleteById(id);
         return usuario; 
    }
}