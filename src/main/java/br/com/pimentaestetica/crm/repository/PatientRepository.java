package br.com.pimentaestetica.crm.repository;

import br.com.pimentaestetica.crm.model.patient.Patient;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {
    List<Patient> findAllByUserId(Sort sort, UUID userId);

    Optional<Patient> findByIdAndUserId(UUID id, UUID userId);
}
