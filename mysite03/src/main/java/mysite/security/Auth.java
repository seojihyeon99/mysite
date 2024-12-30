package mysite.security;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target({TYPE, METHOD})
public @interface Auth {
	// 속성이름은 role, 기본값은 "USER"
	// 기본값 사용 : @Auth, 값 지정 : @Auth(role = "ADMIN")
	String role() default "USER";
}
