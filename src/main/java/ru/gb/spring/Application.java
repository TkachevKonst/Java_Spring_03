package ru.gb.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.gb.spring.model.Project;
import ru.gb.spring.model.Timesheet;
import ru.gb.spring.repository.ProjectRepository;
import ru.gb.spring.repository.TimesheetRepository;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(Application.class, args);
		ProjectRepository projectRepo = ctx.getBean(ProjectRepository.class);
		LocalDate createdAt = LocalDate.now();
		for (int i = 1; i <= 5; i++) {
			createdAt = createdAt.plusDays(1);
			Project project = new Project();
			project.setId((long) i);
			project.setName("Project #" + i);
			project.setCreatedAt(createdAt);
			projectRepo.create(project);
		}

		TimesheetRepository timesheetRepo = ctx.getBean(TimesheetRepository.class);


		for (int i = 1; i <= 10; i++) {
			createdAt = createdAt.plusDays(1);

			Timesheet timesheet = new Timesheet();
			timesheet.setId((long) i);
			timesheet.setProjectID(ThreadLocalRandom.current().nextLong(1, 6));
			timesheet.setCreatedAt(createdAt);
			timesheet.setMinutes(ThreadLocalRandom.current().nextInt(100, 1000));

			timesheetRepo.create(timesheet);
		}
	}

}
