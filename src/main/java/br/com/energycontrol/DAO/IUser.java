package br.com.energycontrol.DAO;

import org.springframework.data.repository.CrudRepository;
import br.com.energycontrol.model.User;

public interface IUser extends CrudRepository<User, Long> {
}