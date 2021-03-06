package com.derteuffel.service;

import com.derteuffel.appInterface.roleInterface;
import com.derteuffel.data.Role;
import com.derteuffel.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by derteuffel on 02/11/2018.
 */
@Service
public class RoleService implements roleInterface {

    @Autowired
   private RoleRepository roleRepository;
    @Override
    public List<Role> listAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role getById(Long roleId) {
        Optional<Role> role=roleRepository.findById(roleId);
        return role.get();
    }

    @Override
    public Set<Role> findByGroupe(Long userId) {
        return roleRepository.findByUsers_UserId(userId);
    }

    @Override
    public Role saveOrUpdate(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void delete(Long roleId) {
        roleRepository.deleteById(roleId);
    }

    @Override
    public Role findByRole(String role){
     return roleRepository.findByRole(role);
    }
}
