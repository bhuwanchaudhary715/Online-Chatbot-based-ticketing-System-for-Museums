package com.Museum.OnlineChatbotTicket.controller;

import com.Museum.OnlineChatbotTicket.model.PersonalInfo;
import com.Museum.OnlineChatbotTicket.service.PersonalInfoService;

import com.Museum.OnlineChatbotTicket.utils.QRCodeGenerator;
import com.google.zxing.WriterException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/personal-info")
public class PersonalInfoController {


    private final PersonalInfoService personalInfoService;


    @GetMapping
    public ResponseEntity<List<PersonalInfo>> getAllPersonalInfo() throws IOException, WriterException {

        List<PersonalInfo> personalInfo = personalInfoService.getAllPersonalInfos();
        if ( personalInfo.size() != 0) {
            for (PersonalInfo info : personalInfo) {
                QRCodeGenerator.generateQRCode(info);
            }
        }
        return ResponseEntity.ok(personalInfoService.getAllPersonalInfos());
    }


    @GetMapping("/{id}")
    public ResponseEntity<PersonalInfo> getPersonalInfoWithQR(@PathVariable("id") Long id) throws IOException, WriterException {
        PersonalInfo personalInfo = personalInfoService.getPersonalInfoById(id);
        QRCodeGenerator.generateQRCode(personalInfo);
        return ResponseEntity.ok(personalInfo);
    }


}