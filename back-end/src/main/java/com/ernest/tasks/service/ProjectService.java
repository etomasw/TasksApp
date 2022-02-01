package com.ernest.tasks.service;

import com.ernest.tasks.exception.ProjectIdException;
import com.ernest.tasks.model.Project;
import com.ernest.tasks.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveProject(Project project) {
        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        } catch (Exception e) {
            throw new ProjectIdException("Project ID " + project.getId() + " must be unique.");
        }

    }

    public Project updateProject(Project project) {
        return projectRepository.save(project);
    }

    public Project findByProjectIdentifier(String projectIdentifier) {
        Project project = projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());
        if(project == null) {
            throw new ProjectIdException("Project ID " + projectIdentifier + " doesnt exist.");
        }
        return project;
    }

    public Iterable<Project> findAllProjects() {
        return projectRepository.findAll();
    }

    public void deleteProjectByProjectId(String projectId) {
        Project project = findByProjectIdentifier(projectId);
        if(project == null) {
            throw new ProjectIdException("Project ID " + projectId + " doesnt exist.");
        }

        projectRepository.deleteById(project.getId());
    }
}
