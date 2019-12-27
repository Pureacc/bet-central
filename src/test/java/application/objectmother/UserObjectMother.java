package application.objectmother;

import org.pureacc.betcentral.domain.model.Balance;
import org.pureacc.betcentral.domain.model.User;
import org.pureacc.betcentral.domain.model.snapshot.UserSnapshot;
import org.pureacc.betcentral.vocabulary.Euros;
import org.pureacc.betcentral.vocabulary.Password;
import org.pureacc.betcentral.vocabulary.UserId;
import org.pureacc.betcentral.vocabulary.Username;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserObjectMother {
	public static final String RAW_PASSWORD = "password";

	public static User aUser() {
		return new User(defaultUserSnapshot().build());
	}

	public static User aUser(Euros balance) {
		return new User(defaultUserSnapshot().withBalance(new Balance(balance))
				.build());
	}

	private static UserSnapshot.Builder defaultUserSnapshot() {
		return UserSnapshot.newBuilder()
				.withUserId(UserId.of(1L))
				.withUsername(Username.of("John Doe"))
				.withPassword(Password.of("{bcrypt}" + new BCryptPasswordEncoder().encode(RAW_PASSWORD)))
				.withBalance(new Balance(Euros.of(0)));
	}
}
