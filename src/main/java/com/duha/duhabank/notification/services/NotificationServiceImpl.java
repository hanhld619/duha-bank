package com.duha.duhabank.notification.services;

import java.nio.charset.StandardCharsets;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.duha.duhabank.auth_users.entity.User;
import com.duha.duhabank.enums.NotificationType;
import com.duha.duhabank.notification.dtos.NotificationDTO;
import com.duha.duhabank.notification.entity.Notification;
import com.duha.duhabank.notification.repo.NotificationRepo;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
	
	private final NotificationRepo notificationRepo;
	private final JavaMailSender mailSender;
	private final TemplateEngine templateEngine;

	@Override
	public void sendEmail(NotificationDTO dto, User user) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
			helper.setTo(dto.getRecipient());
			helper.setSubject(dto.getSubject());
			
			if(dto.getTemplateName() != null) {
				Context context = new Context();
				context.setVariables(dto.getTemplateVariables());
				String htmlContent = templateEngine.process(dto.getTemplateName(), context);
				helper.setText(htmlContent, true);
			} else {
				helper.setText(dto.getBody(), true);
			}
			
			mailSender.send(mimeMessage);
			log.info("Email sent out!");
			
			//Save to DB
//			Notification notification = Notification.builder()
//					.recipient(dto.getRecipient()).subject(dto.getSubject()).body(dto.getBody()).type(NotificationType.EMAIL).user(user)
//					.build();
//			
//			notificationRepo.save(notification);
			
		} catch (MessagingException e) {
			log.error(e.getMessage());
		}
	}

}
