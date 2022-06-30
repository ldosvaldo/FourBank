package com.foursys.fourbank.service;

import com.foursys.fourbank.dto.NotificationDTO;
import com.foursys.fourbank.model.Notification;
import com.foursys.fourbank.model.SavingsAccount;
import com.foursys.fourbank.model.TransactionalAccount;
import com.foursys.fourbank.repository.NotificationRepository;
import com.foursys.fourbank.repository.SavingsAccountRepository;
import com.foursys.fourbank.repository.TransactionalAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private SavingsAccountRepository savingsAccountRepository;

    @Autowired
    private TransactionalAccountRepository transactionalAccountRepository;

    public ResponseEntity<NotificationDTO> update(Long accountId, NotificationDTO notificationDto) {
        Optional<SavingsAccount> savingsAccount = savingsAccountRepository.findById(accountId);
        Optional<TransactionalAccount> transactionalAccount = transactionalAccountRepository.findById(accountId);
        if (savingsAccount.isPresent()) {
            Notification notification = savingsAccount.get().getNotification();
            notification.setEmailNotification(notificationDto.getEmailNotification());
            notification.setSmsNotification(notificationDto.getSmsNotification());
            return ResponseEntity.ok(new NotificationDTO(notification));
        } else if (transactionalAccount.isPresent()) {
            Notification notification = transactionalAccount.get().getNotification();
            notification.setEmailNotification(notificationDto.getEmailNotification());
            notification.setSmsNotification(notificationDto.getSmsNotification());
            return ResponseEntity.ok(new NotificationDTO(notification));
        }

        return ResponseEntity.notFound().build();
    }
}
