package br.com.pimentaestetica.crm.repository;

import br.com.pimentaestetica.crm.model.patient.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PatientRepository extends JpaRepository<Patient, UUID> {
}
