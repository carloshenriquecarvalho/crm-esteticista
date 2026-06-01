package br.com.pimentaestetica.crm.repository;

import br.com.pimentaestetica.crm.model.beautician.Beautician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BeauticianRepository extends JpaRepository<Beautician, UUID> {

}
