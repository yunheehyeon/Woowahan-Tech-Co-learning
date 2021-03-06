package woowahan.anifarm.tecolearning.location.domain.studylocation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyLocationRepository extends JpaRepository<StudyLocation, Long> {

    List<StudyLocation> findAllByStudyId(long studyId);
}
