package org.pureacc.betcentral.infra.jpa.mapping;

import org.pureacc.betcentral.domain.model.User;
import org.pureacc.betcentral.domain.model.snapshot.UserSnapshot;
import org.pureacc.betcentral.infra.jpa.model.UserEntity;
import org.pureacc.betcentral.vocabulary.UserId;

public class ToUser {
	public static User map(UserEntity userEntity) {
		UserSnapshot userSnapshot = UserSnapshot.newBuilder()
				.withUserId(new UserId(userEntity.getId()))
				.withUsername(userEntity.getUsername())
				.withPassword(userEntity.getPassword())
				.withBalance(ToBalance.map(userEntity.getBalance()))
				.build();
		return new User(userSnapshot);
	}
}
