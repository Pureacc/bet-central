package org.pureacc.betcentral.infra.rest;

import org.pureacc.betcentral.application.api.CreateUser;
import org.pureacc.betcentral.application.api.GetUser;
import org.pureacc.betcentral.vocabulary.UserId;
import org.pureacc.betcentral.vocabulary.Username;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@RestController
class UserController {
	private final CreateUser createUser;
	private final GetUser getUser;

	UserController(CreateUser createUser, GetUser getUser) {
		this.createUser = createUser;
		this.getUser = getUser;
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

	@GetMapping("/api/user")
	public GetUserWebResponse get(@RequestParam("userId") long userId) {
		GetUser.Request request = GetUser.Request.newBuilder()
				.withUserId(UserId.of(userId))
				.build();
		GetUser.Response response = getUser.execute(request);
		return new GetUserWebResponse(request.getUserId(), response.getUsername());
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

	static final class GetUserWebResponse {
		private final long userId;
		private final String username;

		GetUserWebResponse(UserId userId, Username username) {
			this.userId = userId.getValue();
			this.username = username.getValue();
		}

		public long getUserId() {
			return userId;
		}

		public String getUsername() {
			return username;
		}
	}
}
