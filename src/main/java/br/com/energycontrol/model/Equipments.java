package br.com.energycontrol.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "equipments")
public class Equipments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_equipment")
    private Long idEquipments;

    @JsonProperty("name_equipment")
    @Column(name = "name_equipment", nullable = false, length = 100)
    private String nameEquipments;

    @JsonProperty("power_watts")
    @Column(name = "power_watts", nullable = false)
    private Double powerWatts;

    @JsonProperty("hours_per_day")
    @Column(name = "hours_per_day", nullable = false)
    private Double hoursDays;

    @JsonProperty("days_per_week")
    @Column(name = "days_per_week", nullable = false)
    private Integer daysWeek;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Getters e Setters continuam iguais...
    public Long getIdEquipments() {
        return idEquipments;
    }

    public void setIdEquipments(Long idEquipments) {
        this.idEquipments = idEquipments;
    }

    public String getNameEquipments() {
        return nameEquipments;
    }

    public void setNameEquipments(String nameEquipments) {
        this.nameEquipments = nameEquipments;
    }

    public Double getPowerWatts() {
        return powerWatts;
    }

    public void setPowerWatts(Double powerWatts) {
        this.powerWatts = powerWatts;
    }

    public Double getHoursDays() {
        return hoursDays;
    }

    public void setHoursDays(Double hoursDays) {
        this.hoursDays = hoursDays;
    }

    public Integer getDaysWeek() {
        return daysWeek;
    }

    public void setDaysWeek(Integer daysWeek) {
        this.daysWeek = daysWeek;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}