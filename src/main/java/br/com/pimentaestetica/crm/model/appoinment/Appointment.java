package br.com.pimentaestetica.crm.model.appoinment;

import br.com.pimentaestetica.crm.model.beautician.Beautician;
import br.com.pimentaestetica.crm.model.patient.Patient;
import br.com.pimentaestetica.crm.model.procedure.Procedure;
import br.com.pimentaestetica.crm.model.user.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beautician_id", nullable = false)
    private Beautician beautician;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

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

    public Appointment(UUID id, String title, AppointmentAvailability appointmentAvailability, LocalDateTime dateTimeStart, Patient patient, Procedure procedure, Beautician beautician, User user) {
        this.id = id;
        this.title = title;
        this.appointmentAvailability = appointmentAvailability;
        this.dateTimeStart = dateTimeStart;
        this.patient = patient;
        this.procedure = procedure;
        this.beautician = beautician;
        this.user = user;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public AppointmentAvailability getAppointmentAvailability() {
        return appointmentAvailability;
    }

    public void setAppointmentAvailability(AppointmentAvailability appointmentAvailability) {
        this.appointmentAvailability = appointmentAvailability;
    }

    public Beautician getBeautician() {
        return beautician;
    }

    public void setBeautician(Beautician beautician) {
        this.beautician = beautician;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Procedure getProcedure() {
        return procedure;
    }

    public void setProcedure(Procedure procedure) {
        this.procedure = procedure;
    }

    public LocalDateTime getDateTimeStart() {
        return dateTimeStart;
    }

    public void setDateTimeStart(LocalDateTime dateTimeStart) {
        this.dateTimeStart = dateTimeStart;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
