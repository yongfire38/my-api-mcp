package com.example.weather;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * 날씨 API 직접 호출 테스트
 */
public class WeatherApiTest {

    private static final Logger logger = LoggerFactory.getLogger(WeatherApiTest.class);
    private final String API_KEY = "2vTSgaaWQ8Sjx2Q%2FM2eqZphPQCJoddkTGjrbjkSPrr0LXrdGdzgRE%2BwQCZSPGfroQyScij2xyLf77qSnKYkY9w%3D%3D";

    @Test
    public void testWeatherApi() {
        try {
            // 부천시 코드
            String cityAreaId = "4119500000";
            // 고정된 날짜 사용
            String currentDate = "20250501";

            // JSON 형식으로 요청
            String jsonUrl = "https://apis.data.go.kr/1360000/TourStnInfoService1/getCityTourClmIdx1?ServiceKey=2vTSgaaWQ8Sjx2Q%2FM2eqZphPQCJoddkTGjrbjkSPrr0LXrdGdzgRE%2BwQCZSPGfroQyScij2xyLf77qSnKYkY9w%3D%3D&pageNo=1&numOfRows=10&dataType=JSON&CURRENT_DATE=20250501&DAY=5&CITY_AREA_ID=4119500000";

            logger.info("JSON URL: {}", jsonUrl);

            // HttpURLConnection 사용
            URL url = new URL(jsonUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");

            int responseCode = conn.getResponseCode();
            logger.info("응답 코드: {}", responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            String jsonResponse = response.toString();
            logger.info("JSON 응답: {}", jsonResponse);

            // JSON 파싱 테스트
            JsonNode items = new ObjectMapper().readTree(jsonResponse)
                    .path("response").path("body").path("items").path("item");

            logger.info("파싱된 항목 수: {}", items.size());
            for (JsonNode item : items) {
                logger.info("날짜: {}", item.get("tm").asText());
                logger.info("지역: {}", item.get("totalCityName").asText());
                logger.info("TCI 지수: {}", item.get("kmaTci").asText());
                logger.info("등급: {}", item.get("TCI_GRADE").asText());
                logger.info("-----");
            }

            // XML 형식으로도 요청해보기
            String xmlUrl = String.format(
                    "https://apis.data.go.kr/1360000/TourStnInfoService1/getCityTourClmIdx1" +
                            "?ServiceKey=%s&pageNo=1&numOfRows=10&dataType=XML&CURRENT_DATE=%s&DAY=5&CITY_AREA_ID=%s",
                    API_KEY, currentDate, cityAreaId);

            logger.info("\nXML URL: {}", xmlUrl);
            url = new URL(xmlUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/xml");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");

            responseCode = conn.getResponseCode();
            logger.info("응답 코드: {}", responseCode);

            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            String xmlResponse = response.toString();
            logger.info("XML 응답: {}", xmlResponse);

        } catch (Exception e) {
            logger.error("오류 발생: {}", e.getMessage(), e);
        }
    }
}
