package com.prography.zone_2_be.global.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class InstantToEpochSecondConverter implements AttributeConverter<Long, Long> {

	@Override
	public Long convertToDatabaseColumn(Long attribute) {
		return (attribute == null ? null : attribute / 1000);
	}

	@Override
	public Long convertToEntityAttribute(Long dbData) {
		return (dbData);
	}
}
