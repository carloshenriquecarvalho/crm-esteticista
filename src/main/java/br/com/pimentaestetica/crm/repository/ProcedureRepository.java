package br.com.pimentaestetica.crm.repository;

import br.com.pimentaestetica.crm.model.patient.Patient;
import br.com.pimentaestetica.crm.model.procedure.Procedure;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProcedureRepository extends JpaRepository<Procedure, UUID> {
    List<Procedure> findAllByUserId(UUID id, Sort sort);
}
