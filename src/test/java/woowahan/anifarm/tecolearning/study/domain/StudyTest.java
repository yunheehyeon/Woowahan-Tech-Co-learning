package woowahan.anifarm.tecolearning.study.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowahan.anifarm.tecolearning.user.domain.User;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StudyTest {

    @Test
    @DisplayName("스터디 정보를 수정한다.")
    void update() {
        Study oldStudy = Study.builder()
                .id(1L)
                .description("qweqwe")
                .subject("spring")
                .startDate(LocalDate.of(2019, 12, 12))
                .endDate(LocalDate.of(2019, 12, 23))
                .location("송파")
                .totalNumberOfParticipants(5)
                .status(StudyStatus.RECRUITING)
                .build();

        Study newStudy = Study.builder()
                .id(1L)
                .description("ewqeqw")
                .subject("springggggg")
                .startDate(LocalDate.of(2019, 12, 13))
                .endDate(LocalDate.of(2019, 12, 24))
                .location("은평")
                .totalNumberOfParticipants(6)
                .status(StudyStatus.RECRUITING)
                .build();


        Study updatedStudy = oldStudy.update(newStudy);

        assertThat(updatedStudy.getDescription()).isEqualTo(newStudy.getDescription());
        assertThat(updatedStudy.getEndDate()).isEqualTo(newStudy.getEndDate());
        assertThat(updatedStudy.getLocation()).isEqualTo(newStudy.getLocation());
        assertThat(updatedStudy.getStartDate()).isEqualTo(newStudy.getStartDate());
        assertThat(updatedStudy.getTotalNumberOfParticipants()).isEqualTo(newStudy.getTotalNumberOfParticipants());
        assertThat(updatedStudy.getSubject()).isEqualTo(newStudy.getSubject());
    }

    @Test
    @DisplayName("발제한 회원이 아닐경우 Exception 발생")
    void checkPermission() {
        Study study = Study.builder()
                .id(1L)
                .presenter(User.builder().id(1L).build())
                .build();

        assertThatThrownBy(() -> study.checkPermission(2L)).isInstanceOf(RuntimeException.class);
    }
}