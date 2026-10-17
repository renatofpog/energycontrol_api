package br.com.energycontrol.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.energycontrol.model.Equipments;
import br.com.energycontrol.repository.IEquipments;

@Service
public class EquipmentsService {

    private IEquipments repository;
    public EquipmentsService(IEquipments repository) {
        this.repository = repository;
    }

    public List<Equipments> listEquipmentsByUserId(Long idUser) {
        return repository.findByUserIdUser(idUser);
    }

    public Equipments createEquipments(Equipments equipments) {
        if (equipments.getUser() == null || equipments.getUser().getIdUser() == null) {
            throw new RuntimeException("O equipamento tem de estar associado a um utilizador válido!");
        }

        return repository.save(equipments);
    }

    public Equipments updateEquipments(Equipments equipments) {
        if (equipments.getIdEquipments() == null) {
            throw new RuntimeException("ID do equipamento é obrigatório para atualização!");
        }

        if (!repository.existsById(equipments.getIdEquipments())) {
            throw new RuntimeException("Equipamento não encontrado!");
        }

        return repository.save(equipments);
    }

    public void deleteEquipments(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Equipamento não encontrado!");
        }
        repository.deleteById(id);
    }
}