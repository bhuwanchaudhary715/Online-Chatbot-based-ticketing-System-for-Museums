package com.Museum.OnlineChatbotTicket.service;

import com.Museum.OnlineChatbotTicket.model.PersonalInfo;
import com.Museum.OnlineChatbotTicket.repository.PersonalInfoRepository;
import lombok.RequiredArgsConstructor;
;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PersonalInfoService {


    private final PersonalInfoRepository personalInfoRepository;


    public List<PersonalInfo> getAllPersonalInfos() {
        return personalInfoRepository.findAll();
    }

    public PersonalInfo getPersonalInfoById(Long id) {
        return personalInfoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PersonalInfo not found with id: " + id));
    }

}