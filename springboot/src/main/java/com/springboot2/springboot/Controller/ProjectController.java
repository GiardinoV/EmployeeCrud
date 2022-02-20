package com.springboot2.springboot.Controller;

import com.springboot2.springboot.Models.Project;
import com.springboot2.springboot.Repositories.IProjectJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProjectController {
    @Autowired
    IProjectJpaRepository projectJpaRepository;

    //Obtener todos los proyectos
    @GetMapping("/")
    public ResponseEntity<List<Project>> getProjects(){
        try {
            List<Project> project1 = new ArrayList<Project>();
            projectJpaRepository.findAll().forEach(project1::add);
            if (project1.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(project1, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Obyener proyecto por id
    @GetMapping("{id}")
    public ResponseEntity<Project> getProjectsById(@PathVariable("id") long id){
        Optional<Project> projectData = projectJpaRepository.findById(id);
        if (projectData.isPresent()) {
            return new ResponseEntity<>(projectData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Creo un nuevo proyecto
    @PostMapping("/add")
    public ResponseEntity<Project> addNewProject(@RequestBody Project project){
        try{
            Project proyect1 = projectJpaRepository.save(new Project(project.getName()));
            return new ResponseEntity<>(proyect1, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    //Actualizar datos de proyecto
    @PutMapping("/update/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable("id") long id, @RequestBody Project project) {
        Optional<Project> project1 = projectJpaRepository.findById(id);

        if (project1.isPresent()) {
            Project project_ = project1.get();
            project_.setName(project.getName());
            return new ResponseEntity<>(projectJpaRepository.save(project_), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Eliminar proyecto por id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable("id") long id) {
        try {
            projectJpaRepository.deleteById(id);
            return new ResponseEntity<>("Project delete",HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
}
