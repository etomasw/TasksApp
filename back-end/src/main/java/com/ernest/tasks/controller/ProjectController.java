package com.ernest.tasks.controller;

import com.ernest.tasks.model.Project;
import com.ernest.tasks.service.MapValidationErrorService;
import com.ernest.tasks.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    private ProjectService projectService;
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    public ProjectController(ProjectService projectService, MapValidationErrorService mapValidationErrorService) {
        this.projectService = projectService;
        this.mapValidationErrorService = mapValidationErrorService;
    }

    @PostMapping("")
    public ResponseEntity<?> createProject(@Valid @RequestBody Project project, BindingResult result) {
        ResponseEntity<?> errorMap = mapValidationErrorService.validationService(result);
        if(errorMap != null) return errorMap;

        return new ResponseEntity<>(projectService.saveProject(project), CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProject(@RequestBody Project project) {
        Project p = projectService.findByProjectIdentifier(project.getProjectIdentifier());
        p.setProjectName(project.getProjectName());
        p.setProjectName(project.getDescription());

        return new ResponseEntity<>(projectService.updateProject(p), OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProjectById(@PathVariable("id") String id) {
        return new ResponseEntity<>(projectService.findByProjectIdentifier(id), CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<Project>> getAllProjects() {
        return new ResponseEntity<>(projectService.findAllProjects(), OK);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable("projectId") String projectId) {
        projectService.deleteProjectByProjectId(projectId);
        return new ResponseEntity<>("Project with ID: " + projectId + " was deleted.", OK);
    }

}
