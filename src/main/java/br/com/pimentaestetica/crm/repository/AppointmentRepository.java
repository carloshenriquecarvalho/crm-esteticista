package br.com.pimentaestetica.crm.repository;

import br.com.pimentaestetica.crm.model.appointment.Appointment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    List<Appointment> findAllByUserId(UUID id, Sort sort);
}
