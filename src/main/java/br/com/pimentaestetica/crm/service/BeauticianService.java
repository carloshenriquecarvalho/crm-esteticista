package br.com.pimentaestetica.crm.service;

import br.com.pimentaestetica.crm.model.beautician.Beautician;
import br.com.pimentaestetica.crm.model.user.User;
import br.com.pimentaestetica.crm.repository.BeauticianRepository;
import br.com.pimentaestetica.crm.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BeauticianService {

    @Autowired
    private BeauticianRepository beauticianRepository;

    @Autowired
    private UserRepository userRepository;

    // Create
    @Transactional
    public Beautician createBeautician(Beautician beautician, UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        user.addBeautician(beautician);
        return beauticianRepository.save(beautician);
    }

    // Read
    // Get All
    public List<Beautician> getAllBeauticians(UUID userId) {
        return beauticianRepository.findAllByUserId(userId, Sort.by("name"));
    }

    // Get one By Id
    public Optional<Beautician> getBeauticianById(UUID beauticianId, UUID userId){
        return Optional.of(beauticianRepository.findByIdAndUserId(beauticianId, userId)
                .orElseThrow(() -> new RuntimeException("Esteticista não encontrada")));
    }

    // Update by Id
    @Transactional
    public Beautician updateBeauticianById(UUID beauticianId, Beautician beauticianData, UUID userId) {
        Beautician beautician = beauticianRepository.findByIdAndUserId(beauticianId, userId)
                .orElseThrow(() -> new RuntimeException("Paciente nao encontrado"));

        beautician.setActive(true);
        beautician.setEmail(beauticianData.getEmail());
        beautician.setName(beauticianData.getName());
        beautician.setStatus(beauticianData.getStatus());

        return beauticianRepository.save(beautician);
    }

    // Delete by Id
    @Transactional
    public void deleteBeauticianById(UUID beauticianId, UUID userId){
        Beautician beautician = beauticianRepository.findByIdAndUserId(beauticianId, userId).orElseThrow(() -> new RuntimeException("Esteticista não encontrada"));
        beauticianRepository.delete(beautician);
    }


}
