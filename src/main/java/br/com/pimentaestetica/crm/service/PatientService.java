package br.com.pimentaestetica.crm.service;

import br.com.pimentaestetica.crm.model.patient.Patient;
import br.com.pimentaestetica.crm.repository.PatientRepository;
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

    // Create
    public Patient createPatient(Patient patient){
        patientRepository.save(patient);
        return patient;
    }

    // Read
    // Get all
    public List<Patient> getAllPatients(){
        return patientRepository.findAll(Sort.by("name").ascending());
    }

    // Get By Id
    public Optional<Patient> getPatientById(UUID id){
        return patientRepository.findById(id);
    }
}
