package com.springboot2.springboot.Controller;

import com.springboot2.springboot.Models.Role;
import com.springboot2.springboot.Repositories.IRoleJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoleController {
    @Autowired
    IRoleJpaRepository roleJpaRepository;

    //Obtener todos los roles
    @GetMapping("/")
    public ResponseEntity<List<Role>> getRoles(){
        try {
            List<Role> role1 = new ArrayList<Role>();
            roleJpaRepository.findAll().forEach(role1::add);
            if (role1.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(role1, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Obtener rol por id
    @GetMapping("{id}")
    public ResponseEntity<Role> getRolesById(@PathVariable("id") long id){
        Optional<Role> roleData = roleJpaRepository.findById(id);
        if (roleData.isPresent()) {
            return new ResponseEntity<>(roleData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Creo un nuevo rol
    @PostMapping("/add")
    public ResponseEntity<Role> addNewProject(@RequestBody Role role){
        try{
            Role role1 = roleJpaRepository.save(new Role(role.getName()));
            return new ResponseEntity<>(role1, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    //Actualizar datos de role
    @PutMapping("/update/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable("id") long id, @RequestBody Role role) {
        Optional<Role> role1 = roleJpaRepository.findById(id);

        if (role1.isPresent()) {
            Role role_ = role1.get();
            role_.setName(role.getName());
            return new ResponseEntity<>(roleJpaRepository.save(role_), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Eliminar rol por id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable("id") long id) {
        try {
            roleJpaRepository.deleteById(id);
            return new ResponseEntity<>("Role delete",HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
}
