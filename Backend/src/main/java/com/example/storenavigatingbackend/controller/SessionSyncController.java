package com.example.storenavigatingbackend.controller;

import com.example.storenavigatingbackend.service.QRCodeSessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/session")
@CrossOrigin(origins = "*")
public class SessionSyncController {

    private final QRCodeSessionService qrCodeSessionService;

    public SessionSyncController(QRCodeSessionService qrCodeSessionService) {
        this.qrCodeSessionService = qrCodeSessionService;
    }

    // Request body for creating a mobile hand-off session
    public record SessionCreateRequest(List<String> productIds, List<?> routeCoordinates) {}

    @PostMapping("/create")
    public ResponseEntity<?> createSession(@RequestBody SessionCreateRequest request) {
        try {
            Map<String, Object> session = qrCodeSessionService.createMobileSession(
                    request.productIds(), request.routeCoordinates());
            return ResponseEntity.ok(session);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to create session: " + ex.getMessage()));
        }
    }

    @GetMapping("/{sessionId}")
    public ResponseEntity<Map<String, Object>> getSession(@PathVariable String sessionId) {
        Map<String, Object> session = qrCodeSessionService.getSessionData(sessionId);
        if (session == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(session);
    }
}
