package br.com.pimentaestetica.crm.model.appointment;

import br.com.pimentaestetica.crm.model.beautician.Beautician;
import br.com.pimentaestetica.crm.model.patient.Patient;
import br.com.pimentaestetica.crm.model.procedure.Procedure;
import br.com.pimentaestetica.crm.model.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @JsonBackReference(value = "user-appointments") // Nome único
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonBackReference(value = "beautician-appointments") // Nome único
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beautician_id", nullable = false)
    private Beautician beautician;

    @JsonBackReference(value = "patient-appointments") // Nome único
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @JsonBackReference(value = "procedure-appointments") // Faltava tratar o loop aqui também
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "procedure_id")
    private Procedure procedure;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private AppointmentAvailability appointmentAvailability;

    @Column(name = "date_hour_start", nullable = false)
    private LocalDateTime dateTimeStart;

    public Appointment(){}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public AppointmentAvailability getAppointmentAvailability() { return appointmentAvailability; }
    public void setAppointmentAvailability(AppointmentAvailability appointmentAvailability) { this.appointmentAvailability = appointmentAvailability; }
    public Beautician getBeautician() { return beautician; }
    public void setBeautician(Beautician beautician) { this.beautician = beautician; }
    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }
    public Procedure getProcedure() { return procedure; }
    public void setProcedure(Procedure procedure) { this.procedure = procedure; }
    public LocalDateTime getDateTimeStart() { return dateTimeStart; }
    public void setDateTimeStart(LocalDateTime dateTimeStart) { this.dateTimeStart = dateTimeStart; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}