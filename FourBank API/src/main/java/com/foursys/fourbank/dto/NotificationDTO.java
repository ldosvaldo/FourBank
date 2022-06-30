package com.foursys.fourbank.dto;

import com.foursys.fourbank.model.Notification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {

    private Boolean emailNotification;
    private Boolean smsNotification;

    public NotificationDTO(Notification notification) {
        this.emailNotification = notification.getEmailNotification();
        this.smsNotification = notification.getSmsNotification();
    }

    public Boolean getEmailNotification() {
        return emailNotification;
    }

    public Boolean getSmsNotification() {
        return smsNotification;
    }
}
