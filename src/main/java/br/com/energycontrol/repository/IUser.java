package br.com.energycontrol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.energycontrol.model.User;

public interface IUser extends JpaRepository<User, Long> {
    User findByEmail(String email);
}