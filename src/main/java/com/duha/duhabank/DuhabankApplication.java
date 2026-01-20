package com.duha.duhabank;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import com.duha.duhabank.auth_users.entity.User;
import com.duha.duhabank.enums.NotificationType;
import com.duha.duhabank.notification.dtos.NotificationDTO;
import com.duha.duhabank.notification.services.NotificationService;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@EnableAsync
@RequiredArgsConstructor
public class DuhabankApplication {
	
	private final NotificationService repo;

	public static void main(String[] args) {
		SpringApplication.run(DuhabankApplication.class, args);
	}
	
//  @Bean
//  CommandLineRunner runner(){
//      return args -> {
//          NotificationDTO notificationDTO = NotificationDTO.builder()
//                  .recipient("chickenjc619@gmail.com")
//                  .subject("HEllo testing email")
//                  .body("Hey, this is a test eamil 😁")
//                  .type(NotificationType.EMAIL)
//                  .build();
//
//          repo.sendEmail(notificationDTO, new User());
//      };
//  }
}
