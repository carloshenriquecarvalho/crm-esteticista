package br.com.pimentaestetica.crm.service;

import br.com.pimentaestetica.crm.model.user.User;
import br.com.pimentaestetica.crm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        userRepository.save(user);
        return user;
    }
}
