package com.api.constant;

public enum Problem {

	SMARTPHONE_IS_RUNNING_SLOWLY(1), POOR_BATTERY_LIFE(2), PHONE_OR_APP_CRASHESH(3), SYNC_ISSUES(4),
	MICROSD_CARD_IS_NOT_WORKING_ON_YOUR_PHONE(5), OVERHEATING(6);

	int code;

	Problem(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

}
