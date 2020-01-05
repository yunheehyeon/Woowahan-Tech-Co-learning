package woowahan.anifarm.tecolearning.location.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import woowahan.anifarm.tecolearning.location.domain.studylocation.StudyLocation;
import woowahan.anifarm.tecolearning.location.domain.studylocation.StudyLocationRepository;
import woowahan.anifarm.tecolearning.location.service.dto.LocationDto;
import woowahan.anifarm.tecolearning.location.service.dto.StudyLocationDto;
import woowahan.anifarm.tecolearning.location.service.exception.NotFoundStudyLocationException;
import woowahan.anifarm.tecolearning.study.domain.StudyParticipant;
import woowahan.anifarm.tecolearning.study.service.StudyParticipantService;
import woowahan.anifarm.tecolearning.user.dto.UserInfoDto;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StudyLocationService {
    private final StudyParticipantService studyParticipantService;
    private final LocationService locationService;
    private final StudyLocationRepository studyLocationRepository;

    public StudyLocationService(StudyParticipantService studyParticipantService, LocationService locationService, StudyLocationRepository studyLocationRepository) {
        this.studyParticipantService = studyParticipantService;
        this.locationService = locationService;
        this.studyLocationRepository = studyLocationRepository;
    }

    public LocationDto save(long studyId, LocationDto locationDto, UserInfoDto userInfoDto) {
        StudyParticipant studyParticipant = studyParticipantService.findByStudyIdAndUserId(studyId, userInfoDto.getId());

        StudyLocation studyLocation = StudyLocation.builder()
                .study(studyParticipant.getStudy())
                .location(locationService.getOrCreate(locationDto))
                .build();

        return LocationDto.from(studyLocationRepository.save(studyLocation)
                .getLocation());
    }

    public void delete(long studyLocationId, UserInfoDto userInfoDto) {
        StudyLocation studyLocation = studyLocationRepository.findById(studyLocationId)
                .orElseThrow(NotFoundStudyLocationException::new);
        studyParticipantService.findByStudyIdAndUserId(studyLocation.getStudy().getId(), userInfoDto.getId());

        studyLocationRepository.deleteById(studyLocationId);
    }

    public List<StudyLocationDto> read(long studyId) {
        return studyLocationRepository.findAllByStudyId(studyId).stream()
                .map(StudyLocationDto::from)
                .collect(Collectors.toList());
    }
}
