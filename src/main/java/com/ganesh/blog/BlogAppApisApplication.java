package com.ganesh.blog;


import com.ganesh.blog.config.AppConstants;
import com.ganesh.blog.entities.Role;
import com.ganesh.blog.repo.RoleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication(scanBasePackages = "com.ganesh.blog")
public class BlogAppApisApplication  implements CommandLineRunner {
	@Autowired
	private PasswordEncoder passwordEncoder;
@Autowired
	private RoleRepo roleRepo;
	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
System.out.print(this.passwordEncoder.encode("xyz"));
		try {
			if (!roleRepo.existsById(AppConstants.ADMIN_USER)) {
				Role adminRole = new Role();
				adminRole.setId(AppConstants.ADMIN_USER);
				adminRole.setName("ADMIN_USER");
				roleRepo.save(adminRole);

			}

			if (!roleRepo.existsById(AppConstants.NORMAL_USER)) {
				Role userRole = new Role();
				userRole.setId(AppConstants.NORMAL_USER);
				userRole.setName("NORMAL_USER");
				roleRepo.save(userRole);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
