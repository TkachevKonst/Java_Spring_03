package ru.gb.service;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;
import ru.gb.client.ProjectsResponse;
import ru.gb.client.TimesheetsResponse;
import ru.gb.page.ProjectPageDto;
import ru.gb.page.TimesheetPageDto;


import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ProjectPageService {

    private final DiscoveryClient discoveryClient;


    public ProjectPageService(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    private RestClient restClient() {
        List<ServiceInstance> instances = discoveryClient.getInstances("TIMESHEET-REST");
        int instancesCount = instances.size();
        int instanceIndex = ThreadLocalRandom.current().nextInt(0, instancesCount);

        ServiceInstance instance = instances.get(instanceIndex);
        String uri = "http://" + instance.getHost() + ":" + instance.getPort();
        return RestClient.create(uri);
    }

    public List<ProjectPageDto> findAll() {


        List<ProjectsResponse> projectsResponses = null;
        int attempts = 5;
        while (attempts-- > 0) {
            try {
                projectsResponses = restClient().get()
                        .uri("/projects")
                        .retrieve()
                        .body(new org.springframework.core.ParameterizedTypeReference<List<ProjectsResponse>>() {
                        });
                break;
            } catch (HttpServerErrorException e) {
            }
        }
            if (projectsResponses == null){
                throw new RuntimeException("ВСЕ");
            }
        List<ProjectPageDto> projectPageDtos = new ArrayList<>();
        for (ProjectsResponse project : projectsResponses) {
            ProjectPageDto projectPageDto = new ProjectPageDto();
            projectPageDto.setId(String.valueOf(project.getId()));
            projectPageDto.setName(project.getName());
            projectPageDto.setCreatedAt(project.getCreatedAt().format(DateTimeFormatter.ISO_DATE));
            projectPageDtos.add(projectPageDto);
        }
        return projectPageDtos;
    }


    public Optional<ProjectPageDto> findById(Long id) {
        ProjectsResponse projectsResponse = restClient().get()
                .uri("/projects/" + id)
                .retrieve()
                .body(ProjectsResponse.class);

        ProjectPageDto projectPageDto = new ProjectPageDto();
        projectPageDto.setId(String.valueOf(projectsResponse.getId()));
        projectPageDto.setName(projectsResponse.getName());
        projectPageDto.setCreatedAt(projectsResponse.getCreatedAt().format(DateTimeFormatter.ISO_DATE));


        return Optional.of(projectPageDto);
    }

}
