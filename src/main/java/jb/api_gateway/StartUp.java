package jb.api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class StartUp {

	public static void main(String[] args) {
		SpringApplication.run(StartUp.class, args);

		Map<String, PasswordEncoder> encoders = new HashMap<>();
		Pbkdf2PasswordEncoder pbkdf2encoder = new Pbkdf2PasswordEncoder("", 8, 18500, Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256 );
		encoders.put("pbkdf2", pbkdf2encoder);
		DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2",encoders);
		passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2encoder);
		String result = passwordEncoder.encode("admin1234");
		System.out.println(result);

	}




}
