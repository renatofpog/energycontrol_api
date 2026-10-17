package br.com.energycontrol.service;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.energycontrol.model.User;
import br.com.energycontrol.repository.IUser;

@Service 
public class UserService {
    private IUser repository;
    private PasswordEncoder passwordEncoder;

    public UserService(IUser repository) {
        this.repository = repository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public List<User> listUsers() {         
        return repository.findAll();
    }

    public User createUser(User user) {
        String encoder = this.passwordEncoder.encode(user.getPasswordHash());
        user.setPasswordHash(encoder);
        return repository.save(user);
    }

    public User editUser(User user) {
        User existingUser = repository.findById(user.getIdUser()).orElse(null);
        if (existingUser == null) {
            throw new RuntimeException("Utilizador não encontrado!");
        }

        if (user.getPasswordHash() != null && !user.getPasswordHash().equals(existingUser.getPasswordHash())) {
            if (!user.getPasswordHash().startsWith("$2a$")) {
                user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
            }
        } else {
            user.setPasswordHash(existingUser.getPasswordHash());
        }

        return repository.save(user);
    }

    public Boolean deleteUser(Long id) {
        repository.deleteById(id);
        return true;
    }

    public User autenticar(User userLogin) {
        User userFound = repository.findByEmail(userLogin.getEmail());
        if (userFound != null) {
            boolean senhaOk = passwordEncoder.matches(userLogin.getPasswordHash(), userFound.getPasswordHash());
            if (senhaOk) {
                return userFound;
            }
        }
        return null;
    }
}