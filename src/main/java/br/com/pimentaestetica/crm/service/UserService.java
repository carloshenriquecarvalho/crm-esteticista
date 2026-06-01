package br.com.pimentaestetica.crm.service;

import br.com.pimentaestetica.crm.model.user.User;
import br.com.pimentaestetica.crm.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User createUser(User user) {
        userRepository.save(user);
        return user;
    }
}
