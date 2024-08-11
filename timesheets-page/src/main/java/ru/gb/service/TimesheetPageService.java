package ru.gb.service;


import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;
import ru.gb.client.ProjectsResponse;
import ru.gb.client.TimesheetsResponse;
import ru.gb.page.TimesheetPageDto;


import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class TimesheetPageService {

    private final DiscoveryClient discoveryClient;



    public TimesheetPageService(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    private RestClient restClient(){
        List<ServiceInstance> instances = discoveryClient.getInstances("TIMESHEET-REST");
        int instancesCount = instances.size();
        int instanceIndex = ThreadLocalRandom.current().nextInt(0, instancesCount);

        ServiceInstance instance = instances.get(instanceIndex);
        String uri = "http://" + instance.getHost() + ":" + instance.getPort();
        return RestClient.create(uri);
    }
    public List<TimesheetPageDto> findAll(){
        List<TimesheetsResponse> timesheetsResponses = null;
        int attempts = 5;
        while (attempts-- > 0) {
            try {
                timesheetsResponses = restClient().get()
                        .uri("/timesheets")
                        .retrieve()
                        .body(new org.springframework.core.ParameterizedTypeReference<List<TimesheetsResponse>>() {
                        });
                break;
            }catch (HttpServerErrorException e){
            }
        }
        if (timesheetsResponses == null){
            throw new RuntimeException("ВСЕ");
        }

        List<TimesheetPageDto> pageDtos = new ArrayList<>();
        for (TimesheetsResponse timesheet : timesheetsResponses){
            TimesheetPageDto timesheetPageDto = new TimesheetPageDto();
            timesheetPageDto.setId(String.valueOf(timesheet.getId()));
            timesheetPageDto.setMinutes(String.valueOf(timesheet.getMinutes()));
            timesheetPageDto.setCreatedAt(timesheet.getCreatedAt().format(DateTimeFormatter.ISO_DATE));

            ProjectsResponse projectsResponse = restClient().get()
                    .uri("/projects/"+timesheet.getProjectID())
                    .retrieve()
                    .body(ProjectsResponse.class);
            timesheetPageDto.setProjectName(projectsResponse.getName());
            timesheetPageDto.setProjectId(timesheet.getProjectID());
            pageDtos.add(timesheetPageDto);
        }
        return pageDtos;
    }


    public Optional<TimesheetPageDto> findById (Long id){
        try {
            TimesheetsResponse timesheetsResponse = restClient().get()
                    .uri("/timesheets/" + id)
                    .retrieve()
                    .body(TimesheetsResponse.class);

            TimesheetPageDto timesheetPageDto = new TimesheetPageDto();
            timesheetPageDto.setId(String.valueOf(timesheetsResponse.getId()));
            timesheetPageDto.setMinutes(String.valueOf(timesheetsResponse.getMinutes()));
            timesheetPageDto.setCreatedAt(timesheetsResponse.getCreatedAt().format(DateTimeFormatter.ISO_DATE));

            ProjectsResponse projectsResponse = restClient().get()
                    .uri("/projects/" + timesheetsResponse.getProjectID())
                    .retrieve()
                    .body(ProjectsResponse.class);
            timesheetPageDto.setProjectName(projectsResponse.getName());
            timesheetPageDto.setProjectId(timesheetsResponse.getProjectID());

            return Optional.of(timesheetPageDto);
        }catch (HttpClientErrorException.NotFound e){
            return Optional.empty();
        }
    }





}
