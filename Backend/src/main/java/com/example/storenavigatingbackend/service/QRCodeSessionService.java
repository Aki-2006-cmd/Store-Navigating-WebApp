package com.example.storenavigatingbackend.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class QRCodeSessionService {

    @Value("${app.server.base-url:http://192.168.1.100:8080}")
    private String serverBaseUrl;

    private final Map<String, Map<String, Object>> activeSessions = new ConcurrentHashMap<>();

    public String generateQRCodeBase64(String textUrl, int width, int height) throws Exception {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(textUrl, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
        return "data:image/png;base64," + Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }

    public Map<String, Object> createMobileSession(List<String> productIds, List<?> routeCoordinates) throws Exception {
        String sessionId = UUID.randomUUID().toString();
        String mobileWebUrl = serverBaseUrl + "/mobile-map.html?session=" + sessionId;

        String qrCodeDataUri = generateQRCodeBase64(mobileWebUrl, 250, 250);

        Map<String, Object> sessionPayload = new HashMap<>();
        sessionPayload.put("sessionId", sessionId);
        sessionPayload.put("mobileWebUrl", mobileWebUrl);
        sessionPayload.put("qrCode", qrCodeDataUri);
        sessionPayload.put("productIds", productIds);
        sessionPayload.put("routeCoordinates", routeCoordinates);

        activeSessions.put(sessionId, sessionPayload);
        return sessionPayload;
    }

    public Map<String, Object> getSessionData(String sessionId) {
        return activeSessions.get(sessionId);
    }
}