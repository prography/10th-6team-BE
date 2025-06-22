package com.prography.zone_2_be.global.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.time.Instant; // <- Instant 임포트

@Converter(autoApply = false) // Instant 필드에만 명시적으로 적용
public class InstantToEpochSecondConverter implements AttributeConverter<Instant, Long> { // <- Instant <-> Long 변환

	@Override
	public Long convertToDatabaseColumn(Instant attribute) {
		// Java의 Instant 객체(밀리초 정확도)를 DB에 저장할 초 단위 Long으로 변환
		// Instant.getEpochSecond()는 밀리초 정보(나노초까지)를 버리고 정확히 초 단위 Long을 반환합니다.
		return (attribute == null ? null : attribute.getEpochSecond());
	}

	@Override
	public Instant convertToEntityAttribute(Long dbData) {
		// DB에서 가져온 초 단위 Long 값을 Java의 Instant 객체로 변환
		// Instant.ofEpochSecond()는 초 단위로 Instant 객체를 생성합니다.
		return (dbData == null ? null : Instant.ofEpochSecond(dbData));
	}
}