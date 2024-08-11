package ru.gb.controller;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;
import org.springframework.web.client.RestClient;
import ru.gb.model.Project;
import ru.gb.model.Timesheet;
import ru.gb.repository.ProjectRepositoryDb;
import ru.gb.repository.TimesheetRepository;
import ru.gb.repository.TimesheetRepositoryDb;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;


@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TimesheetControllerTest {

    @Autowired
    TimesheetRepositoryDb timesheetRepository;
    @Autowired
    ProjectRepositoryDb projectRepository;






    @LocalServerPort
    private int port;
    private RestClient restClient;

    @BeforeEach
    void beforeEach() {

        restClient = RestClient.create("http://localhost:" + port);

    }

    ;


    @Test
    void getTest() {
        Timesheet timesheet = new Timesheet();
        timesheet.setMinutes(ThreadLocalRandom.current().nextInt(100, 1000));
        timesheet.setCreatedAt(LocalDate.now());
        timesheet.setProjectID(ThreadLocalRandom.current().nextLong(1, 6));
        Timesheet expected = timesheetRepository.save(timesheet);

        ResponseEntity<Timesheet> actual = restClient.get()
                .uri("/timesheets/" + expected.getId())
                .retrieve()
                .toEntity(Timesheet.class);

        Assertions.assertEquals(HttpStatus.OK, actual.getStatusCode());
        Timesheet responseBody = actual.getBody();
        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(expected.getId(), responseBody.getId());
        Assertions.assertEquals(expected.getCreatedAt(), responseBody.getCreatedAt());
        Assertions.assertEquals(expected.getMinutes(), responseBody.getMinutes());
    }

    @Test
    void testGetAll() {
        Timesheet timesheet1 = new Timesheet();
        timesheet1.setMinutes(ThreadLocalRandom.current().nextInt(100, 1000));
        timesheet1.setCreatedAt(LocalDate.now());
        timesheet1.setProjectID(ThreadLocalRandom.current().nextLong(1, 6));
        Timesheet expected1 = timesheetRepository.save(timesheet1);
        Timesheet timesheet2 = new Timesheet();
        timesheet2.setMinutes(ThreadLocalRandom.current().nextInt(100, 1000));
        timesheet2.setCreatedAt(LocalDate.now());
        timesheet2.setProjectID(ThreadLocalRandom.current().nextLong(1, 6));
        Timesheet expected2 = timesheetRepository.save(timesheet2);
        Timesheet timesheet3 = new Timesheet();
        timesheet3.setMinutes(ThreadLocalRandom.current().nextInt(100, 1000));
        timesheet3.setCreatedAt(LocalDate.now());
        timesheet3.setProjectID(ThreadLocalRandom.current().nextLong(1, 6));
        Timesheet expected3 = timesheetRepository.save(timesheet3);

        ResponseEntity<List> actuals = restClient.get()
                .uri("/timesheets")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(List.class);

        List <Timesheet> timesheets = actuals.getBody();
        Assertions.assertEquals(HttpStatus.OK, actuals.getStatusCode());
        Assertions.assertNotNull(timesheets);


    }

    @Test
    void testCreate() {
        Random random = new Random();
        LocalDate createdAt = LocalDate.now();
        for (int i = 1; i <= 5; i++) {
            Set<Long> employees = new HashSet<>();
            employees.add(random.nextLong(1,11));
            employees.add(random.nextLong(1,11));
            employees.add(random.nextLong(1,11));
            createdAt = createdAt.plusDays(1);
            Project project = new Project();
            project.setName("Project #" + i);
            project.setCreatedAt(createdAt);
            projectRepository.save(project);
        }
        Timesheet toCreat = new Timesheet();
        toCreat.setMinutes(ThreadLocalRandom.current().nextInt(100, 1000));
        toCreat.setCreatedAt(LocalDate.now());
        toCreat.setProjectID(ThreadLocalRandom.current().nextLong(1, 6));

        ResponseEntity<Timesheet> actual = restClient.post()
                .uri("/timesheets")
                .body(toCreat)
                .retrieve()
                .toEntity(Timesheet.class);

        Assertions.assertEquals(HttpStatus.CREATED, actual.getStatusCode());
        Timesheet responseBody = actual.getBody();
        Assertions.assertNotNull(responseBody.getId());
        Assertions.assertEquals(toCreat.getCreatedAt(),responseBody.getCreatedAt());
        Assertions.assertEquals(toCreat.getMinutes(),responseBody.getMinutes());
        Assertions.assertEquals(toCreat.getProjectID(),responseBody.getProjectID());

        Assertions.assertTrue(timesheetRepository.existsById(responseBody.getId()));


    }

    @Test
    void taestDelete() {
        Timesheet timesheet = new Timesheet();
        timesheet.setMinutes(ThreadLocalRandom.current().nextInt(100, 1000));
        timesheet.setCreatedAt(LocalDate.now());
        timesheet.setProjectID(ThreadLocalRandom.current().nextLong(1, 6));
        Timesheet expected = timesheetRepository.save(timesheet);

        ResponseEntity<Void> actual = restClient.delete()
                .uri("/timesheets/"+expected.getId())
                .retrieve()
                .toBodilessEntity();

        Assertions.assertEquals(HttpStatus.NO_CONTENT, actual.getStatusCode());

        Assertions.assertFalse(timesheetRepository.existsById(expected.getId()));
    }
}