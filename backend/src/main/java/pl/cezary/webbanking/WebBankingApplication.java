package pl.cezary.webbanking;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.cezary.webbanking.models.ERole;
import pl.cezary.webbanking.models.Role;
import pl.cezary.webbanking.models.User;
import pl.cezary.webbanking.repository.*;

@SpringBootApplication
public class WebBankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebBankingApplication.class, args);
	}

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
		};
	}


}


