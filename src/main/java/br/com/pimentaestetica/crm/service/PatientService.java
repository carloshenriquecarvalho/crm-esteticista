package br.com.pimentaestetica.crm.service;

import br.com.pimentaestetica.crm.model.patient.Patient;
import br.com.pimentaestetica.crm.model.user.User;
import br.com.pimentaestetica.crm.repository.PatientRepository;
import br.com.pimentaestetica.crm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
    public Patient createPatient(Patient patient, UUID userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        user.addPatient(patient);

        patientRepository.save(patient);
        return patient;
    }

    // Read
    // Get all
    public List<Patient> getAllPatients(UUID userId){
        return patientRepository.findAllByUserId(userId, Sort.by("name").ascending());
    }

    // Get By Id
    public Optional<Patient> getPatientById(UUID id){
        return patientRepository.findById(id);
    }

    // Update by Id
    public Patient updatePatientById(UUID id, Patient patientData) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new RuntimeException("Paciente nao " +
                "encontrado"));

        patient.setActive(true);
        patient.setEmail(patientData.getEmail());
        patient.setName(patientData.getName());
        patient.setPhoneNumber(patientData.getPhoneNumber());

        return patientRepository.save(patient);
    }

    // Delete by Id
    public boolean deletePatientById(UUID id){
        if(!patientRepository.existsById(id)){
            throw new RuntimeException("Paciente nao encontrado");
        }
       patientRepository.deleteById(id);
        return true;
    }

}
