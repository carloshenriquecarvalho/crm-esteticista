package br.com.pimentaestetica.crm.model.user;

import br.com.pimentaestetica.crm.model.appointment.Appointment;
import br.com.pimentaestetica.crm.model.beautician.Beautician;
import br.com.pimentaestetica.crm.model.patient.Patient;
import br.com.pimentaestetica.crm.model.procedure.Procedure;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @JsonManagedReference(value = "user-patients")
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Patient> patients = new ArrayList<>();

    @JsonManagedReference(value = "user-appointments")
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Appointment> appointments = new ArrayList<>();

    @JsonManagedReference(value = "user-beauticians")
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Beautician> beauticians = new ArrayList<>();

    @JsonManagedReference(value = "user-procedures")
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Procedure> procedures = new ArrayList<>();

    // ... Restante dos atributos, Construtores, Getters, Setters e Métodos Helpers originais idênticos
    @Column(name = "name", nullable = false, length = 180) private String name;
    @Column(name = "email", nullable = false, unique = true) private String email;
    @Column(name = "password", nullable = false) private String password;
    @Column(name = "enabled", nullable = false) private boolean enabled = true;
    @Column(name = "account_non_locked", nullable = false) private boolean accountNonLocked = true;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "roles")
    private Set<UserRole> roles = new HashSet<>();

    public User(){}
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public boolean isAccountNonLocked() { return accountNonLocked; }
    public void setAccountNonLocked(boolean accountNonLocked) { this.accountNonLocked = accountNonLocked; }
    public List<Appointment> getAppointments() { return appointments; }
    public void setAppointments(List<Appointment> appointments) { this.appointments = appointments; }
    public Set<UserRole> getRoles() { return roles; }
    public void setRoles(Set<UserRole> roles) { this.roles = roles; }
    public List<Patient> getPatients() { return patients; }
    public List<Beautician> getBeauticians() { return beauticians; }
    public List<Procedure> getProcedures() { return procedures; }
    public void addPatient(Patient patient) { this.patients.add(patient); patient.setUser(this); }
    public void addBeautician(Beautician beautician) { this.beauticians.add(beautician); beautician.setUser(this); }
    public void addProcedure(Procedure procedure) { this.procedures.add(procedure); procedure.setUser(this); }
    public void addAppointment(Appointment appointment) { this.appointments.add(appointment); appointment.setUser(this); }
}