package br.com.pimentaestetica.crm.service;

import br.com.pimentaestetica.crm.model.patient.Patient;
import br.com.pimentaestetica.crm.model.user.User;
import br.com.pimentaestetica.crm.repository.PatientRepository;
import br.com.pimentaestetica.crm.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PatientService {

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    UserRepository userRepository;

    // Create
    @Transactional
    public Patient createPatient(Patient patient, UUID userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        user.addPatient(patient);

        patientRepository.save(patient);
        return patient;
    }

    // Read
    // Get all
    public List<Patient> getAllPatients(@AuthenticationPrincipal User user){
        return patientRepository.findAllByUserId( Sort.by("name").ascending(), user.getId());
    }

    // Get By Id
    public Optional<Patient> getPatientById(UUID userId, UUID patientId){
        return Optional.of(patientRepository.findByIdAndUserId(patientId, userId)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado")));
    }

    // Update by Id
    @Transactional
    public Patient updatePatientById(UUID patientId, UUID userId, Patient patientData) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new RuntimeException("Paciente nao " +
                "encontrado"));
        User user= userRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        patient.setUser(user);
        patient.setActive(true);
        patient.setEmail(patientData.getEmail());
        patient.setName(patientData.getName());
        patient.setPhoneNumber(patientData.getPhoneNumber());

        return patientRepository.save(patient);
    }

    // Delete by Id
    @Transactional
    public boolean deletePatientById(UUID id){
        if(!patientRepository.existsById(id)){
            throw new RuntimeException("Paciente nao encontrado");
        }
       patientRepository.deleteById(id);
        return true;
    }

}
