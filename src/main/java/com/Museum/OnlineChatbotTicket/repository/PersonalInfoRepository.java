package com.Museum.OnlineChatbotTicket.repository;


import com.Museum.OnlineChatbotTicket.model.PersonalInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


// PersonalInfoRepository.java
@Repository
public interface PersonalInfoRepository extends JpaRepository<PersonalInfo, Long> {

}
