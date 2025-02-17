package com.Museum.OnlineChatbotTicket.utils;

import com.Museum.OnlineChatbotTicket.model.PersonalInfo;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;

public class QRCodeGenerator {

    public static void generateQRCode(PersonalInfo personalInfo) throws WriterException, IOException {

        String qrCodePath = "C:\\Users\\My PC\\Desktop\\SpringBoot Project";
        String qrCodeName = qrCodePath + personalInfo.getFirstname() + personalInfo.getId() + "-QRCODE.png";
        var qrCodeWriter = new QRCodeWriter();

        BitMatrix bitMatrix = qrCodeWriter.encode(
                "ID: " + personalInfo.getId() + "\n" +
                        "Firstname: " + personalInfo.getFirstname() + "\n" +
                        "Lastname: " + personalInfo.getLastname() + "\n" +
                        "Email: " + personalInfo.getEmail() + "\n" +
                        "Phone: " + personalInfo.getPhoneNo() + "\n" +
                        "DOB: " + personalInfo.getDateofbirth().format(DateTimeFormatter.ISO_LOCAL_DATE) + "\n" +
                        "Address: " + personalInfo.getAddress() + "\n" +
                        "ID Details: " + personalInfo.getIdentificationdetails(),
                BarcodeFormat.QR_CODE, 400, 400);

        Path path = FileSystems.getDefault().getPath(qrCodeName);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

    }

}

