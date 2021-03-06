package org.pureacc.betcentral.infra.jpa.mapping;

import org.pureacc.betcentral.domain.model.User;
import org.pureacc.betcentral.domain.model.snapshot.UserSnapshot;
import org.pureacc.betcentral.infra.jpa.model.UserEntity;

public class ToUserEntity {
	public static UserEntity map(User user) {
		UserSnapshot userSnapshot = user.toSnapshot();
		UserEntity userEntity = new UserEntity();
		if (userSnapshot.getUserId() != null) {
			userEntity.setId(userSnapshot.getUserId()
					.getValue());
		}
		userEntity.setUsername(userSnapshot.getUsername());
		userEntity.setPassword(userSnapshot.getPassword());
		userEntity.setBalance(ToBalanceEntity.map(userSnapshot.getBalance()));
		return userEntity;
	}
}
