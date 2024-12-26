package com.gamgyul_code.halmang_vision.map.application;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class MapService {
    /*

    @Value("${naver.client-id}")
    private String naverClientId;

    @Value("${naver.client-secret}")
    private String naverClientSecret;

    private final WebClient.Builder webClientBuilder;


    public Mono<CoordinatesResponse> getCoordinates(String address) {
        WebClient webClient = webClientBuilder
                .baseUrl("https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode")
                .build();

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("query", address)
                        .build())
                .header("x-ncp-apigw-api-key-id", naverClientId)
                .header("x-ncp-apigw-api-key", naverClientSecret)
                .retrieve()
                .bodyToMono(String.class)
                .map(this::parseCoordinatesResponse);
    }

    private CoordinatesResponse parseCoordinatesResponse(String responseBody) {
        try {
            // JSON 문자열 파싱
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(responseBody);

            // "addresses" 배열의 첫 번째 객체에서 x, y 값 추출
            JsonNode addressNode = root.path("addresses").get(0);
            double longitude = addressNode.path("x").asDouble(); // 경도
            double latitude = addressNode.path("y").asDouble();  // 위도

            // CoordinatesResponse 객체 생성
            return MapDto.CoordinatesResponse.fromNaverGeocodingApiResponse(latitude, longitude);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse Naver Geocoding API response", e);
        }
    }

     */
}
