package br.com.pimentaestetica.crm.model.beautician;

import br.com.pimentaestetica.crm.model.appointment.Appointment;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "beauticians")
public class Beautician {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToMany(mappedBy = "beautician", fetch = FetchType.LAZY)
    private List<Appointment> appointments = new ArrayList<>();

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BeauticianStatus status;

    @Column(name = "active")
    private Boolean active = true;

    public Beautician(){}

    public Beautician(UUID id, String name, String email, BeauticianStatus status, ArrayList<Appointment> appointments, Boolean active) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.status = status;
        this.appointments = appointments;
        this.active = active;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BeauticianStatus getStatus() {
        return status;
    }

    public void setStatus(BeauticianStatus status) {
        this.status = status;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
