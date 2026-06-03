package br.com.pimentaestetica.crm.service;

import br.com.pimentaestetica.crm.dto.request.AppointmentRequest;
import br.com.pimentaestetica.crm.model.appointment.Appointment;
import br.com.pimentaestetica.crm.model.appointment.AppointmentAvailability;
import br.com.pimentaestetica.crm.model.beautician.Beautician;
import br.com.pimentaestetica.crm.model.patient.Patient;
import br.com.pimentaestetica.crm.model.procedure.Procedure;
import br.com.pimentaestetica.crm.model.user.User;
import br.com.pimentaestetica.crm.repository.*;
import jakarta.transaction.Transactional;
import org.jspecify.annotations.NonNull;
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
    public Appointment createAppointment(AppointmentRequest appointmentRequest, UUID userId, UUID patientId, UUID beauticianId, UUID procedureId){

        Appointment appointment = new Appointment();

        AppointmentAvailability appointmentAvailability = AppointmentAvailability.valueOf(appointmentRequest.availability());

        appointment.setTitle(appointmentRequest.title());
        appointment.setAppointmentAvailability(appointmentAvailability);

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
    public Optional<Appointment> getAppointmentById(UUID patientId, UUID userId) {
        if(userId == null) {
            throw new RuntimeException("Usuário não existente");
        }
        return Optional.of(appointmentRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado")));
    }

    // Update by id
    @Transactional
    public Appointment updateAppointmentById(@NonNull UUID userId, UUID appointmentId, Appointment appointmentData, UUID patientId, UUID beauticianId, UUID procedureId) {
        Appointment appointment = appointmentRepository.findByIdAndUserId(appointmentId, userId)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));

        Patient patient = patientRepository.findByIdAndUserId(patientId, userId)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
        Beautician beautician = beauticianRepository.findByIdAndUserId(beauticianId, userId)
                .orElseThrow(() -> new RuntimeException("Esteticista não encontrada"));
        Procedure procedure = procedureRepository.findByIdAndUserId(procedureId, userId)
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
    public void deleteAppointmentById(UUID appointmentId, UUID userId) {
        Appointment appointment = appointmentRepository.findByIdAndUserId(appointmentId, userId).orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));
        appointmentRepository.delete(appointment);
    }
}
