package com.app.utils;

public interface Constant {

	public enum Status {
		INACTIVE(0),
		DELETE(1),
		ACTIVE(2);
		
		private final int value;

		private Status(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

	}
}
