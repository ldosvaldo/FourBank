package com.foursys.fourbank.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_notifications")
@SequenceGenerator(name="tb_notifications", sequenceName = "tb_sq_notifications", allocationSize = 1, initialValue = 1)
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notification", nullable = false)
    private Long id;

    @Column(name = "email_notification", nullable = false)
    private Boolean emailNotification;

    @Column(name = "sms_notification", nullable = false)
    private Boolean smsNotification;

    @OneToOne(mappedBy = "notification")
    private FourBankAccount fourBankAccount;
}
