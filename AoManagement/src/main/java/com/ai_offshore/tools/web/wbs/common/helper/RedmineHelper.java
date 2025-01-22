package com.ai_offshore.tools.web.wbs.common.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.ai_offshore.tools.web.wbs.common.model.RedmineIssue;

import jakarta.annotation.PostConstruct;

/**
 * Redmine APIを呼び出すヘルパークラス
 */
@Component
public class RedmineHelper {
    
    private static final Logger logger = LoggerFactory.getLogger(RedmineHelper.class);
    
    @Value("${redmine.api.url}")
    private String redmineUrl;
    
    @Value("${redmine.api.key}")
    private String apiKey;
    
    private RestClient restClient;
    
    @PostConstruct
    public void init() {
        String baseUrl = redmineUrl.endsWith("/") ? redmineUrl : redmineUrl + "/";
        this.restClient = RestClient.builder()
            .baseUrl(baseUrl)
            .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
            .build();
    }
    
    /**
     * チケット情報を取得
     * 
     * @param ticketNumber チケット番号
     * @return チケット情報
     */
    public RedmineIssue getIssue(String ticketNumber) {
        try {
            String url = String.format("issues/%s.json?key=%s", ticketNumber, apiKey);
            
            logger.debug("Requesting Redmine API: {}", url);
            
            return restClient.get()
                .uri(url)
                .retrieve()
                .body(RedmineIssue.class);
                
        } catch (Exception e) {
            logger.error("Error calling Redmine API: {}", e.getMessage(), e);
            throw e;
        }
    }
} 