package com.prography.zone_2_be.global.converter;

import java.time.Instant;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class InstantToEpochSecondConverter implements AttributeConverter<Instant, Long> {

	@Override
	public Long convertToDatabaseColumn(Instant attribute) {
		return (attribute == null ? null : attribute.getEpochSecond());
	}

	@Override
	public Instant convertToEntityAttribute(Long dbData) {
		return (dbData == null ? null : Instant.ofEpochSecond(dbData));
	}
}
