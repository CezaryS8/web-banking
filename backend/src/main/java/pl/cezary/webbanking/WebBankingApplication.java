package pl.cezary.webbanking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.cezary.webbanking.models.*;
import pl.cezary.webbanking.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@SpringBootApplication
public class WebBankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebBankingApplication.class, args);
	}

	@Autowired
	private PasswordEncoder encoder;
	@Bean
	CommandLineRunner init(RoleRepository roleRepository,
						   UserRepository userRepository,
						   AccountRepository accountRepository,
						   CardRepository cardRepository,
						   TransferRepository transferRepository
						   ) {

		return args -> {
			Role userRole = new Role(ERole.ROLE_USER);
			Role adminRole = new Role(ERole.ROLE_ADMIN);
			// Save roles in the database
			roleRepository.save(userRole);
			roleRepository.save(adminRole);
			// Save users in the database
			User user1 = new User("Czarek7891125456", "czarek15345@mail.com", "Cezary", "Kowalski", encoder.encode("K.tO123!!gdigdOK123"));
			User user2 = new User("NieCzarek7891125456", "nieczarek15345@mail.com", "Niecezary", "Niekowalski", encoder.encode("K.tO123!!gdigdOK123"));
			user1.setRoles(Set.of(userRole));
			user2.setRoles(Set.of(userRole));
			User u1 = userRepository.save(user1);
			User u2 = userRepository.save(user2);
			// Save accounts in the database
			Account account1 = new Account(u1, "40004000400040004000400022",  new java.math.BigDecimal("1000.00"));
			Account account2 = new Account(u1, "40004000400040004000400023",  new java.math.BigDecimal("100000.00"));
			Account account3 = new Account(u2, "40004000400040004000400024",  new java.math.BigDecimal("1000000.00"));
			Account ac1 = accountRepository.save(account1);
			Account ac2 = accountRepository.save(account2);
			Account ac3 = accountRepository.save(account3);
			// Save cards in the database
			cardRepository.save(new Card(ac1, "6969000069690001", "VISA", new Date(System.currentTimeMillis() + 3L * 365 * 24 * 60 * 60 * 1000), "969"));
			cardRepository.save(new Card(ac2, "6969000069690002", "MASTERCARD", new Date(System.currentTimeMillis() + 3L * 365 * 24 * 60 * 60 * 600), "696"));
			cardRepository.save(new Card(ac1, "6969000069690003", "MASTERCARD", new Date(System.currentTimeMillis() + 3L * 365 * 24 * 60 * 60 * 600), "696"));
			cardRepository.save(new Card(ac3, "6969000069690004", "VISA", new Date(System.currentTimeMillis() + 3L * 365 * 24 * 60 * 60 * 600), "696"));
			// Save transfers in the database
			Transfer t1 = transferRepository.save( new Transfer(1L, ac1, ac2, new java.math.BigDecimal("100.00"), LocalDateTime.now(), "Przelew 1 - Piwo"));
			Transfer t2 = transferRepository.save( new Transfer(2L, ac2, ac1, new java.math.BigDecimal("10.00"), LocalDateTime.now().minusDays(5), "Przelew 2 - Piwo"));
			Transfer t3 = transferRepository.save( new Transfer(3L, ac3, ac2, new java.math.BigDecimal("50.00"), LocalDateTime.now().minusDays(8), "Przelew 3 - Piwo"));
			Transfer t4 = transferRepository.save( new Transfer(4L, ac1, ac3, new java.math.BigDecimal("60.00"), LocalDateTime.now().minusDays(8), "Przelew 4 - Piwo"));
			Transfer t5 = transferRepository.save( new Transfer(5L, ac2, ac1, new java.math.BigDecimal("20.00"), LocalDateTime.now().minusDays(33), "Przelew 5 - Piwo"));
		};
	}


}


