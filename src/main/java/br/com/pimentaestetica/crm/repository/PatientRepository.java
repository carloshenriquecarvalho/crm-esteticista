package br.com.pimentaestetica.crm.repository;

import br.com.pimentaestetica.crm.model.patient.Patient;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {
    List<Patient> findAllByUserId(UUID id, Sort sort);
}
