package woowahan.anifarm.tecolearning.location.service.exception;

import woowahan.anifarm.tecolearning.common.exception.BadRequestException;

public class NotFoundStudyLocationException extends BadRequestException {
    private static final String MESSAGE = "스터디 장소를 찾을 수 없습니다.";

    public NotFoundStudyLocationException() {
        super(MESSAGE);
    }
}
