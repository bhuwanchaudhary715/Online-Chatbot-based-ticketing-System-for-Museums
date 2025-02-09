package com.Museum.OnlineChatbotTicket.repository;


import com.Museum.OnlineChatbotTicket.model.PersonalInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PersonalInfoRepository extends JpaRepository<PersonalInfo, Long> {

}