package com.prography.zone_2_be.domain.term.agreement.entity;

import com.prography.zone_2_be.domain.term.entity.Term;
import com.prography.zone_2_be.domain.user.entity.User;
import com.prography.zone_2_be.global.entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TermAgreement extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "term_id", nullable = false)
	private Term term;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Builder
	private TermAgreement(Term term, User user) {
		this.term = term;
		this.user = user;
	}

	public static TermAgreement of(Term term, User user) {
		return TermAgreement.builder()
			.term(term)
			.user(user)
			.build();
	}
}
