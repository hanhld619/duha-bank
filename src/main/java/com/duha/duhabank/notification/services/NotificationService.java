package com.duha.duhabank.notification.services;

import com.duha.duhabank.auth_users.entity.User;
import com.duha.duhabank.notification.dtos.NotificationDTO;

public interface NotificationService {
	void sendEmail(NotificationDTO dto, User user);
}
