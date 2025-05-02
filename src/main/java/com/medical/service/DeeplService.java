package com.medical.service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeeplService {

    // application.properties 또는 application.yml 에서 API 키 주입
    // 예: deepl.auth.key=YOUR_DEEPL_API_KEY
    @Value("${deepl.auth.key}")
    private String deeplAuthKey;

    private final String deeplApiUrl = "https://api-free.deepl.com/v2/translate"; // 무료 API 엔드포인트
    private final RestTemplate restTemplate = new RestTemplate(); // RestTemplate 인스턴스 (Bean으로 관리하는 것이 더 좋습니다)

    /**
     * DeepL API를 사용하여 텍스트를 번역합니다.
     *
     * @param text        번역할 텍스트
     * @param sourceLang  원본 언어 코드 (대문자, 예: "KO", "EN"). null이면 자동 감지.
     * @param targetLang  목표 언어 코드 (대문자, 예: "KO", "EN-US").
     * @return 번역된 텍스트
     * @throws RuntimeException 번역 API 호출 실패 시
     */
    public String translate(String text, String sourceLang, String targetLang) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "DeepL-Auth-Key " + deeplAuthKey);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("text", Collections.singletonList(text)); // DeepL은 text를 배열로 받습니다.
        requestBody.put("target_lang", targetLang);
        if (sourceLang != null && !sourceLang.isEmpty()) {
            requestBody.put("source_lang", sourceLang);
        }

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(deeplApiUrl, entity, Map.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            List<Map<String, String>> translations = (List<Map<String, String>>) response.getBody().get("translations");
            if (translations != null && !translations.isEmpty()) {
                return translations.get(0).get("text"); // 번역된 텍스트 반환
            }
        }
        // 번역 실패 또는 응답 문제 시 예외 발생
        throw new RuntimeException("DeepL 번역 실패: " + response.getStatusCode() + " " + response.getBody());
    }
}