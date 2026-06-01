package br.com.pimentaestetica.crm.model.procedure;

import br.com.pimentaestetica.crm.model.appointment.Appointment;
import br.com.pimentaestetica.crm.model.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "procedures")
public class Procedure {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @JsonManagedReference(value = "procedure-appointments") // Corrigido: adicionado nome pareado com o Appointment
    @OneToMany(mappedBy = "procedure", fetch = FetchType.LAZY)
    private List<Appointment> appointments = new ArrayList<>();

    @JsonBackReference(value = "user-procedures") // Nome pareado com o User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "value", precision = 10, scale = 2, nullable = false)
    private BigDecimal value;

    @Column(name = "duration_minutes", nullable = false)
    private Integer durationMinutes;

    @Column(name = "availability", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProcedureAvailability procedureAvailability;

    @Column(name = "active")
    private Boolean active = true;

    public Procedure(){}

    public UUID getId() { return id; }

    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public BigDecimal getValue() { return value; }

    public void setValue(BigDecimal value) { this.value = value; }

    public Integer getDurationMinutes() { return durationMinutes; }

    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }

    public ProcedureAvailability getProcedureAvailability() { return procedureAvailability; }

    public void setProcedureAvailability(ProcedureAvailability procedureAvailability) { this.procedureAvailability = procedureAvailability; }

    public List<Appointment> getAppointments() { return appointments; }

    public void setAppointments(List<Appointment> appointments) { this.appointments = appointments; }

    public Boolean getActive() { return active; }

    public void setActive(Boolean active) { this.active = active; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }
}