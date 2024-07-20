package ru.gb.spring.page;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.spring.service.ProjectPageService;
import ru.gb.spring.service.TimesheetPageService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
@RequestMapping("/home/projects")
public class ProjectPageController {

    private final ProjectPageService projectPageService;

    public ProjectPageController(ProjectPageService projectPageService) {
        this.projectPageService = projectPageService;
    }
    @GetMapping
    public String getAllProjects (Model model){
        List<ProjectPageDto> projects = projectPageService.findAll();
        model.addAttribute("projects", projects);
        return "projects-page.html";
    }

    @GetMapping ("/{id}")
    public String getProjectsPage(@PathVariable Long id, Model model){
        Optional<ProjectPageDto> projectOpt = projectPageService.findById(id);
        if (projectOpt.isEmpty()){
            throw new NoSuchElementException();
        }
        model.addAttribute("project" , projectOpt.get());
        return "project-page.html";
    }

}
