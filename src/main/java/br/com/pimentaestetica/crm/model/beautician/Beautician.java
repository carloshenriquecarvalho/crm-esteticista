package br.com.pimentaestetica.crm.model.beautician;

import br.com.pimentaestetica.crm.model.appointment.Appointment;
import br.com.pimentaestetica.crm.model.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @JsonBackReference(value = "user-beauticians") // Nome pareado com o User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonManagedReference(value = "beautician-appointments") // Nome pareado com o Appointment
    @OneToMany(mappedBy = "beautician", fetch = FetchType.LAZY)
    private List<Appointment> appointments = new ArrayList<>();

    // ... Restante do código mantido sem alterações
    @Column(name = "name", nullable = false) private String name;
    @Column(name = "email", nullable = false, unique = true) private String email;
    @Enumerated(EnumType.STRING) @Column(name = "status", nullable = false) private BeauticianStatus status;
    @Column(name = "active") private Boolean active = true;
    public Beautician(){}
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public BeauticianStatus getStatus() { return status; }
    public void setStatus(BeauticianStatus status) { this.status = status; }
    public List<Appointment> getAppointments() { return appointments; }
    public void setAppointments(List<Appointment> appointments) { this.appointments = appointments; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}