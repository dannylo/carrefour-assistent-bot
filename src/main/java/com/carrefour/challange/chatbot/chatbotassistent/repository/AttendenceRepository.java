package com.carrefour.challange.chatbot.chatbotassistent.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carrefour.challange.chatbot.chatbotassistent.domain.Attendance;

@Repository
public interface AttendenceRepository extends JpaRepository<Attendance, Long> {}
