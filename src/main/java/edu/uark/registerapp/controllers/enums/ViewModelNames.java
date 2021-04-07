package edu.uark.registerapp.controllers.enums;

public enum ViewModelNames {
	NOT_DEFINED(""),
	ERROR_MESSAGE("errorMessage"),
	PRODUCTS("products"), // Product listing
	PRODUCT("product"), // Product detail
	IS_ELEVATED_USER("isElevatedUser"),
	EMPLOYEE_ID("employeeId"),
	EMPLOYEE_TYPES("employeeTypes"),
	EMPLOYEE("employee");
	
	public String getValue() {
		return value;
	}
	
	private String value;

	private ViewModelNames(final String value) {
		this.value = value;
	}
}
