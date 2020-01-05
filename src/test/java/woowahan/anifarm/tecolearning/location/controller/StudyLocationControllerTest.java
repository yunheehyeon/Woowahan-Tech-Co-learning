package woowahan.anifarm.tecolearning.location.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowahan.anifarm.tecolearning.AbstractWebTestClient;
import woowahan.anifarm.tecolearning.location.service.dto.LocationDto;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;

class StudyLocationControllerTest extends AbstractWebTestClient {
    private static final String API_STUDIES = "/api/studies";
    private static final String API_LOCATIONS = "/api/locations";


    @Test
    @DisplayName("참가자이면 스터디 장소 저장 성공")
    void save_StudyLocation_ok() {
        LocationDto locationDto = LocationDto.builder()
                .id(7947003)
                .placeName("석촌호수 서호")
                .phone("02-415-3595")
                .placeUrl("http://place.map.kakao.com/7947003")
                .x("127.099112837006")
                .y("37.5076807262772")
                .build();

        post(API_STUDIES + "/1/locations", locationDto)
                .expectStatus()
                .isOk()
                .expectBody(LocationDto.class)
                .isEqualTo(locationDto)
                .consumeWith(document("studyLocation/post",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @Test
    @DisplayName("참가자가 아니면 스터디 장소 저장 실패")
    void save_StudyLocation_fail() {
        LocationDto locationDto = LocationDto.builder()
                .id(7947003)
                .placeName("석촌호수 서호")
                .phone("02-415-3595")
                .placeUrl("http://place.map.kakao.com/7947003")
                .x("127.099112837006")
                .y("37.5076807262772")
                .build();

        post(API_STUDIES + "/2/locations", locationDto)
                .expectStatus()
                .isBadRequest();
    }

    @Test
    @DisplayName("스터디 장소 삭제")
    void deleteStudyLocation() {
        delete(API_LOCATIONS + "/1")
                .expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(document("studyLocation/delete",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @Test
    @DisplayName("스터디 장소 스터디 id로 조회")
    void readStudyLocation() {
        get(API_STUDIES + "/1/locations")
                .expectStatus()
                .isOk()
                .expectBodyList(LocationDto.class)
                .hasSize(1)
                .consumeWith(document("studyLocation/get",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }
}