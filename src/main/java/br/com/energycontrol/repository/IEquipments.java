package br.com.energycontrol.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.energycontrol.model.Equipments;

public interface IEquipments extends JpaRepository<Equipments, Long> {
    
    List<Equipments> findByUserIdUser(Long idUser);
}