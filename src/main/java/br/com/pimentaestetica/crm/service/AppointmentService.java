package br.com.pimentaestetica.crm.service;

import br.com.pimentaestetica.crm.model.appointment.Appointment;
import br.com.pimentaestetica.crm.model.beautician.Beautician;
import br.com.pimentaestetica.crm.model.patient.Patient;
import br.com.pimentaestetica.crm.model.procedure.Procedure;
import br.com.pimentaestetica.crm.model.user.User;
import br.com.pimentaestetica.crm.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private BeauticianRepository beauticianRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProcedureRepository procedureRepository;

    // CRUD
    // Create by user id
    @Transactional
    public Appointment createAppointment(Appointment appointment, UUID userId, UUID patientId, UUID beauticianId, UUID procedureId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
        Beautician beautician = beauticianRepository.findById(beauticianId)
                .orElseThrow(() -> new RuntimeException("Esteticista não encontrada"));
        Procedure procedure = procedureRepository.findById(procedureId)
                .orElseThrow(() -> new RuntimeException("Procedimento não encontrada"));

        user.addAppointment(appointment);

        appointment.setPatient(patient);
        appointment.setBeautician(beautician);
        appointment.setProcedure(procedure);

        return appointmentRepository.save(appointment);
    }

    // Read
    // Get all
    public List<Appointment> getAllAppointments(UUID userId){
        return appointmentRepository.findAllByUserId(userId, Sort.by("dateTimeStart").descending());
    }

    // Get one by id
    public Optional<Appointment> getAppointmentById(UUID id) {
        return Optional.of(appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado")));
    }

    // Update by id
    @Transactional
    public Appointment updateAppointmentById(UUID id, Appointment appointmentData, UUID patientId, UUID beauticianId, UUID procedureId) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
        Beautician beautician = beauticianRepository.findById(beauticianId)
                .orElseThrow(() -> new RuntimeException("Esteticista não encontrada"));
        Procedure procedure = procedureRepository.findById(procedureId)
                .orElseThrow(() -> new RuntimeException("Procedimento não encontrado"));

        appointment.setPatient(patient);
        appointment.setBeautician(beautician);
        appointment.setProcedure(procedure);

        appointment.setAppointmentAvailability(appointmentData.getAppointmentAvailability());
        appointment.setDateTimeStart(appointmentData.getDateTimeStart());
        appointment.setTitle(appointmentData.getTitle());

        return appointmentRepository.save(appointment);
    }

    // Delete by id
    @Transactional
    public boolean deleteAppointmentById(UUID id) {
        if(!appointmentRepository.existsById(id)){
            throw new RuntimeException("Agendamento não existe");
        }
        appointmentRepository.deleteById(id);
        return true;
    }
}
