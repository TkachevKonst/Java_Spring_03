package ru.gb.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.gb.spring.model.Employee;
import ru.gb.spring.model.Project;
import ru.gb.spring.model.Timesheet;
import ru.gb.spring.repository.*;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(Application.class, args);
		ProjectRepositoryDb projectRepo = ctx.getBean(ProjectRepositoryDb.class);
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
			projectRepo.save(project);
		}

		TimesheetRepositoryDb timesheetRepo = ctx.getBean(TimesheetRepositoryDb.class);


		for (int i = 1; i <= 10; i++) {
			createdAt = createdAt.plusDays(1);

			Timesheet timesheet = new Timesheet();
			timesheet.setProjectID(ThreadLocalRandom.current().nextLong(1, 6));
			timesheet.setCreatedAt(createdAt);
			timesheet.setMinutes(ThreadLocalRandom.current().nextInt(100, 1000));
			timesheetRepo.save(timesheet);
		}

		EmployeeRepositoryDb employeeRepository = ctx.getBean(EmployeeRepositoryDb.class);
		for (int i = 1; i <= 10; i++) {
			Set<Long> timesheets = new HashSet<>();
			timesheets.add(random.nextLong(1,11));
			timesheets.add(random.nextLong(1,11));
			timesheets.add(random.nextLong(1,11));
			timesheets.add(random.nextLong(1,11));
			Employee employee = new Employee();
			employee.setName("Employee" + i);

			employee.setTimesheetId(timesheets);
			employeeRepository.save(employee);

		}
	}

}
