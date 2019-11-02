package org.pureacc.betcentral.infra.rest;

import org.pureacc.betcentral.application.api.Authenticate;
import org.pureacc.betcentral.application.api.CreateUser;
import org.pureacc.betcentral.application.api.GetUser;
import org.pureacc.betcentral.vocabulary.Euros;
import org.pureacc.betcentral.vocabulary.UserId;
import org.pureacc.betcentral.vocabulary.Username;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@RestController
class UserController {
	private final CreateUser createUser;
	private final GetUser getUser;
	private final Authenticate authenticate;

	UserController(CreateUser createUser, GetUser getUser, Authenticate authenticate) {
		this.createUser = createUser;
		this.getUser = getUser;
		this.authenticate = authenticate;
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

	@PostMapping("/api/user/authenticate")
	public AuthenticateWebResponse authenticate(@RequestBody AuthenticateWebRequest webRequest) {
		Authenticate.Request request = Authenticate.Request.newBuilder()
				.withUsername(webRequest.getUsername())
				.withPassword(webRequest.getPassword())
				.build();
		Authenticate.Response response = authenticate.execute(request);
		return new AuthenticateWebResponse(response.getUserId(), response.getUsername(), response.getBalance());
	}

	@GetMapping("/api/user")
	public GetUserWebResponse get(@RequestParam("userId") long userId) {
		GetUser.Request request = GetUser.Request.newBuilder()
				.withUserId(UserId.of(userId))
				.build();
		GetUser.Response response = getUser.execute(request);
		return new GetUserWebResponse(request.getUserId(), response.getUsername(), response.getBalance());
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

	static final class AuthenticateWebRequest {
		private final String username;
		private final String password;

		@JsonCreator
		AuthenticateWebRequest(@JsonProperty("username") String username, @JsonProperty("password") String password) {
			this.username = username;
			this.password = password;
		}

		public Username getUsername() {
			return Username.of(username);
		}

		public String getPassword() {
			return password;
		}
	}

	static final class AuthenticateWebResponse {
		private final long userId;
		private final String username;
		private final double balance;

		AuthenticateWebResponse(UserId userId, Username username, Euros balance) {
			this.userId = userId.getValue();
			this.username = username.getValue();
			this.balance = balance.getValue();
		}

		public long getUserId() {
			return userId;
		}

		public String getUsername() {
			return username;
		}

		public double getBalance() {
			return balance;
		}
	}

	static final class GetUserWebResponse {
		private final long userId;
		private final String username;
		private final double balance;

		GetUserWebResponse(UserId userId, Username username, Euros balance) {
			this.userId = userId.getValue();
			this.username = username.getValue();
			this.balance = balance.getValue();
		}

		public long getUserId() {
			return userId;
		}

		public String getUsername() {
			return username;
		}

		public double getBalance() {
			return balance;
		}
	}
}
