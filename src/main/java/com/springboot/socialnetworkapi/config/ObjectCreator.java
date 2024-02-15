package com.springboot.socialnetworkapi.config;

import com.springboot.socialnetworkapi.entity.enums.ERole;
import com.springboot.socialnetworkapi.entity.Role;
import com.springboot.socialnetworkapi.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ObjectCreator implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        roleCreator();
    }

    private void roleCreator(){
        Arrays.stream(ERole.values()).forEach(roleName -> {
            if(!roleRepository.existsByRole(roleName)){
                roleRepository.save(new Role(roleName));
            }
        } );
    }
}
