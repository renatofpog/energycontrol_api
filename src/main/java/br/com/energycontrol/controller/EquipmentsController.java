package br.com.energycontrol.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.energycontrol.model.Equipments;
import br.com.energycontrol.model.User;
import br.com.energycontrol.repository.IUser;
import br.com.energycontrol.security.TokenService;
import br.com.energycontrol.service.EquipmentsService;

@RestController
@CrossOrigin("*")
@RequestMapping("/equipments")
public class EquipmentsController {

    private final EquipmentsService equipmentsService;
    private final TokenService tokenService;
    private final IUser userRepository;

    public EquipmentsController(EquipmentsService equipmentsService, TokenService tokenService, IUser userRepository) {
        this.equipmentsService = equipmentsService;
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    private User getAuthenticatedUser(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Token JWT não fornecido ou inválido!");
        }

        String token = authorizationHeader.replace("Bearer ", "");
        
        String email = tokenService.validarToken(token); 

        if (email == null || email.isEmpty()) {
            throw new RuntimeException("Token inválido ou expirado!");
        }

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("Utilizador não encontrado!");
        }

        return user;
    }

    @GetMapping
    public ResponseEntity<List<Equipments>> listMyEquipments(@RequestHeader("Authorization") String authHeader) {
        User loggedUser = getAuthenticatedUser(authHeader);

        List<Equipments> equipments = equipmentsService.listEquipmentsByUserId(loggedUser.getIdUser());

        return ResponseEntity.status(HttpStatus.OK).body(equipments);
    }

    @PostMapping
    public ResponseEntity<Equipments> createEquipment(
            @RequestHeader("Authorization") String authHeader, 
            @RequestBody Equipments equipments) {
        
        User loggedUser = getAuthenticatedUser(authHeader);

        equipments.setUser(loggedUser);

        Equipments savedEquipment = equipmentsService.createEquipments(equipments);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedEquipment);
    }

    @PutMapping
    public ResponseEntity<Equipments> updateEquipment(
            @RequestHeader("Authorization") String authHeader, 
            @RequestBody Equipments equipments) {
        
        User loggedUser = getAuthenticatedUser(authHeader);

        equipments.setUser(loggedUser);

        Equipments updatedEquipment = equipmentsService.updateEquipments(equipments);

        return ResponseEntity.status(HttpStatus.OK).body(updatedEquipment);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEquipment(
            @RequestHeader("Authorization") String authHeader, 
            @PathVariable Long id) {
        
        getAuthenticatedUser(authHeader);

        equipmentsService.deleteEquipments(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}