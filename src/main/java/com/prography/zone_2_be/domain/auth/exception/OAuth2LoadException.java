package com.prography.zone_2_be.domain.auth.exception;

import com.prography.zone_2_be.global.error.ErrorCode;
import com.prography.zone_2_be.global.exception.CustomException;

public class OAuth2LoadException extends CustomException {
	public OAuth2LoadException() {
		super(ErrorCode.LOAD_OAUTH2_USER_FAIL);
	}
}
