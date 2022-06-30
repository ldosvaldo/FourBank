package com.foursys.fourbank.repository;


import com.foursys.fourbank.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foursys.fourbank.model.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
