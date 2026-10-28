package com.example.storenavigatingbackend.controller;

import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/api/navigation")
@CrossOrigin(origins = "*")
public class MapUploadController {

    // Put your free ImgBB API key here (or store it in application.properties)
    private static final String IMGBB_API_KEY = "a03ff2eb2de6035170f80663fe28cdbd"; 

    @PostMapping("/upload-map")
    public ResponseEntity<String> uploadToImgBB(@RequestBody Map<String, String> payload) {
        try {
            String base64Image = payload.get("image");
            if (base64Image == null || base64Image.isEmpty()) {
                return ResponseEntity.badRequest().body("Missing image payload");
            }

            if (base64Image.contains(",")) {
                base64Image = base64Image.split(",")[1];
            }

            // Prepare multipart request to ImgBB API
            String url = "https://api.imgbb.com/1/upload?key=a03ff2eb2de6035170f80663fe28cdbd";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("image", base64Image); // ImgBB accepts base64 strings directly in form data!

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Map> response = restTemplate.postForEntity(url, requestEntity, Map.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Map data = (Map) response.getBody().get("data");
                String imageUrl = (String) data.get("url"); // Permanent public cloud URL
                return ResponseEntity.ok(imageUrl);
            } else {
                return ResponseEntity.status(500).body("Failed to upload to ImgBB");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}