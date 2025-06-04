package com.prography.zone_2_be.global.response;

import java.util.List;

import org.springframework.data.domain.Slice;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SliceResponse<T> {

	private List<T> content;
	private int pageNumber;
	private boolean hasNext;

	@Builder
	public SliceResponse(List<T> content, int pageNumber, boolean hasNext) {
		this.content = content;
		this.pageNumber = pageNumber;
		this.hasNext = hasNext;
	}

	public static <T> SliceResponse<T> from(Slice<T> slice) {
		return SliceResponse.<T>builder()
			.content(slice.getContent())
			.pageNumber(slice.getNumber())
			.hasNext(slice.hasNext())
			.build();
	}
}
