package com.healspan.claim.client;

import com.google.gson.Gson;
import com.healspan.claim.model.rpa.RpaRequiredDetailDto;
import com.healspan.claim.model.rpa.TokenResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class RpaClient {

    private static final Logger logger = LoggerFactory.getLogger(RpaClient.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HeaderRequestParameter parameter;

    @Autowired
    private Gson gson;

    @Value("${rpa.service.token.url}")
    String tokenGenerationUrl;

    @Value("${rpa.service.claim.consumer.url}")
    String rpaClaimDetailsConsumerUrl;

    public TokenResponseDto getToken() {
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(parameter.getTokenGenerationRequestValueMap(), parameter.getTokenGenerationHeaders());
        ResponseEntity<String> responseEntity = restTemplate.exchange(tokenGenerationUrl, HttpMethod.POST, httpEntity, String.class);
        TokenResponseDto dto = gson.fromJson(responseEntity.getBody(), TokenResponseDto.class);
        logger.info("getToken-1 ::{}", dto.getAccess_token());
        logger.info("getToken-2 ::{}", responseEntity.getStatusCode());
        return dto;
    }

    public void pushClaimDataToRpa(RpaRequiredDetailDto dto, String token) {
        String requestBody = gson.toJson(dto);
        logger.info("pushClaimDataToRpa-1-Req Body ::{}", requestBody);
        MultiValueMap<String, String> headerValueMap = parameter.getRpaRequestValueMap(token);
        HttpEntity<String> httpEntity = new HttpEntity<>(requestBody, headerValueMap);
        ResponseEntity<String> responseEntity = restTemplate.exchange(rpaClaimDetailsConsumerUrl, HttpMethod.POST, httpEntity, String.class);
        logger.info("pushClaimDataToRpa-2 ::{}", responseEntity.getBody());
        logger.info("pushClaimDataToRpa-3 ::{}", responseEntity.getStatusCode());
    }

    public void pushRpaClaim(String request, String token) {
        logger.info("pushClaimDataToRpa-1-Req Body ::{}", request);
        MultiValueMap<String, String> headerValueMap = parameter.getRpaRequestValueMap(token);
        HttpEntity<String> httpEntity = new HttpEntity<>(request, headerValueMap);
        ResponseEntity<String> responseEntity = restTemplate.exchange(rpaClaimDetailsConsumerUrl, HttpMethod.POST, httpEntity, String.class);
        logger.info("pushClaimDataToRpa-2 ::{}", responseEntity.getBody());
        logger.info("pushClaimDataToRpa-3 ::{}", responseEntity.getStatusCode());
    }
}
