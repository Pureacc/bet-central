package application.factory;

import org.pureacc.betcentral.domain.model.User;
import org.pureacc.betcentral.domain.repository.UserRepository;
import org.pureacc.betcentral.vocabulary.Euros;

import application.objectmother.UserObjectMother;

public class Users {
	private final UserRepository userRepository;

	public Users(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User aUser() {
		return persist(UserObjectMother.aUser());
	}

	public User aUser(Euros balance) {
		return persist(UserObjectMother.aUser(balance));
	}

	private User persist(User user) {
		return userRepository.save(user);
	}
}
