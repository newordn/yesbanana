package com.derteuffel.service;

import com.derteuffel.appInterface.UserInterface;
import com.derteuffel.config.EncryptionService;
import com.derteuffel.data.User;
import com.derteuffel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by derteuffel on 10/10/2018.
 */
@Service
public class UserService implements UserInterface {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder strongPasswordEncryptor;






    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> listAll() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add); //fun with Java 8
        return users;
    }

    @Override
    public User getById(Long userId) {
      Optional<User> user= userRepository.findById(userId);
        return user.get();
    }

    @Override
    public User saveOrUpdate(User user) {
        if(user.getPassword() != null){
            user.setPassword(strongPasswordEncryptor.encode(user.getPassword()));
        }
        return userRepository.save(user);
    }

    @Override
    public User update(User user){
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(Long userId) {

        userRepository.deleteById(userId);
    }


}
