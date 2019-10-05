package org.pureacc.betcentral.infra.rest;

import org.pureacc.betcentral.application.api.CreateUser;
import org.pureacc.betcentral.vocabulary.Username;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@RestController
class UserController {
	private final CreateUser createUser;

	UserController(CreateUser createUser) {
		this.createUser = createUser;
	}

	@PostMapping("/api/user/register")
	public RegisterWebResponse register(@RequestBody RegisterWebRequest webRequest) {
		CreateUser.Request request = CreateUser.Request.newBuilder()
				.withUsername(webRequest.getUsername())
				.build();
		CreateUser.Response response = createUser.execute(request);
		return new RegisterWebResponse(response.getUserId()
				.getValue());
	}

	static final class RegisterWebRequest {
		private final String username;

		@JsonCreator
		RegisterWebRequest(@JsonProperty("username") String username) {
			this.username = username;
		}

		public Username getUsername() {
			return Username.of(username);
		}
	}

	static final class RegisterWebResponse {
		private final long userId;

		RegisterWebResponse(long userId) {
			this.userId = userId;
		}

		public long getUserId() {
			return userId;
		}
	}
}
