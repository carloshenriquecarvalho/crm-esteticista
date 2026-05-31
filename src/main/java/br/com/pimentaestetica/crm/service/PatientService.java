package br.com.pimentaestetica.crm.service;

import br.com.pimentaestetica.crm.model.patient.Patient;
import br.com.pimentaestetica.crm.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    @Autowired
    PatientRepository patientRepository;

    public Patient createPatient(Patient patient){
        patientRepository.save(patient);
        return patient;
    }
}
