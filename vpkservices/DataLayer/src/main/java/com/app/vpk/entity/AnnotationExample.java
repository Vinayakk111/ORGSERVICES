package com.app.vpk.entity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Objects;


// 2. Class Using the Annotation
class StringValidator {

	public void validateString(@MinLength String text) { // overriding default message
		System.out.println("Validating parameter: " + text);
		validateMinLength(text, "text");
	}

	public void validateString2(@MinLength(1) String text) { // change default value to 1
		System.out.println("Validating parameter: " + text);
		validateMinLength(text, "text2");
	}

	public void validateString3(String text) {
		System.out.println("Validating parameter: " + text);
		validateMinLength(text, "text3");
	}

	// helper method
	private void validateMinLength(String text, String parameterName) {
		if (text != null && text.length() < 2) {
			System.out.println("Error: " + parameterName + " is too short!");
			// throw new IllegalArgumentException("String is too short!");
		} else {
			System.out.println(parameterName + " is valid");
		}
	}
}

// 3. Class with annotated fields
class MyClass {
	@MinLength(message =  "Name too short")
	private String name; // Field-level annotation

	@MinLength(value = 3, message = "Address too short")
	private String address;

	private String city;

	public MyClass(String name, String address, String city) {
		this.name = name;
		this.address = address;
		this.city = city;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void validate() {
		// moved validation logic here.
		validateField(this.name, "name", MinLength.class);
		validateField(this.address, "address", MinLength.class);
		validateField(this.city, "city", MinLength.class);

	}

	private void validateField(String value, String fieldName, Class<MinLength> annotationClass) {
		try {
			Field field = this.getClass().getDeclaredField(fieldName);
			if (field.isAnnotationPresent(annotationClass)) {
				MinLength minLengthAnnotation = field.getAnnotation(annotationClass);
				if (value != null && value.length() < minLengthAnnotation.value()) {
					System.out.println("Error: Field " + fieldName + " " + minLengthAnnotation.message());
					// throw new IllegalArgumentException(minLengthAnnotation.message());
				} else {
					System.out.println("Field " + fieldName + " is valid");
				}
			} else {
				System.out.println("Field " + fieldName + " is not annotated");
			}
		} catch (NoSuchFieldException e) {
			System.out.println("Field " + fieldName + " not found");
			e.printStackTrace();
		}
	}
}

// 4. Example Usage and Annotation Processing
public class AnnotationExample {

	public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException {
		StringValidator validator = new StringValidator();
		Class<?> clazz = StringValidator.class;
		Method method = clazz.getMethod("validateString", String.class); // gets the method validateString

		// Get the parameter annotation
		MinLength minLengthAnnotation = method.getParameters()[0].getAnnotation(MinLength.class); // gets the annotation
																									// of the first
																									// parameter

		if (minLengthAnnotation != null) {
			System.out.println("Message from annotation: " + minLengthAnnotation.message()); // prints message from
																								// annotation
		}

		validator.validateString("abc"); // Valid string
		validator.validateString("a"); // Invalid string
		validator.validateString(null); // Invalid string

		Method method2 = clazz.getMethod("validateString2", String.class);
		MinLength minLengthAnnotation2 = method2.getParameters()[0].getAnnotation(MinLength.class); // gets the
																									// annotation of the
																									// first parameter
		if (minLengthAnnotation2 != null) {
			System.out.println("Message from annotation: " + minLengthAnnotation2.message()); // prints message from
																								// annotation
		}
		validator.validateString2("abc");
		validator.validateString2("");
		validator.validateString2(null);

		// Example with MyClass
		MyClass myObject1 = new MyClass("John Doe", "123 Main St", "Anytown");
		myObject1.validate();

		MyClass myObject2 = new MyClass("Jo", "12", "New York");
		myObject2.validate();

		MyClass myObject3 = new MyClass(null, null, null);
		myObject3.validate();

		validator.validateString3(null);
	}
}
