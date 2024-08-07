package ru.gb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import ru.gb.model.*;
import ru.gb.repository.*;


import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
@EnableDiscoveryClient
@SpringBootApplication
public class RestApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(RestApplication.class, args);
		ProjectRepositoryDb projectRepo = ctx.getBean(ProjectRepositoryDb.class);
		Random random = new Random();
		LocalDate createdAt = LocalDate.now();


		UserRepository userRepository = ctx.getBean(UserRepository.class);
		User admin = new User();
		admin.setLogin("admin");
		admin.setPassword("admin");
		User user = new User();
		user.setLogin("user");
		user.setPassword("user");
		User rest = new User();
		rest.setLogin("rest");
		rest.setPassword("rest");
		admin = userRepository.save(admin);
		user = userRepository.save(user);
		rest = userRepository.save(rest);

		List<User> users1 = new ArrayList<>();
		List<User> users2 = new ArrayList<>();
		List<User> users3 = new ArrayList<>();

		users1.add(admin);
		users2.add(admin);
		users2.add(user);
		users3.add(rest);

		RoleRepository roleRepository = ctx.getBean(RoleRepository.class);
		Role adminRole = new Role();
		adminRole.setName("ADMIN");
		adminRole.setUsers(users1);
		Role restRole = new Role();
		restRole.setName("REST");
		restRole.setUsers(users3);
		Role userRole = new Role();
		userRole.setName("USER");
		userRole.setUsers(users2);

		adminRole = roleRepository.save(adminRole);
		userRole = roleRepository.save(userRole);
		restRole = roleRepository.save(restRole);

		List<Role> roles1 = new ArrayList<>();
		List<Role> roles2 = new ArrayList<>();
		List<Role> roles3 = new ArrayList<>();
		roles1.add(adminRole);
		roles1.add(userRole);
		roles2.add(userRole);
		roles3.add(restRole);

		admin.setRoles(roles1);
		user.setRoles(roles2);
		rest.setRoles(roles3);

		userRepository.save(admin);
		userRepository.save(user);
		userRepository.save(rest);

		roleRepository.save(adminRole);
		roleRepository.save(userRole);
		roleRepository.save(restRole);






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
