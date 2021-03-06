package infra.audit;

import org.pureacc.betcentral.vocabulary.Password;
import org.pureacc.betcentral.vocabulary.annotation.Command;

import vocabulary.TestException;

@Command
public class TestCommandImpl implements TestCommand {
	@Override
	public Response success(Request request) {
		return Response.newBuilder()
				.withUsername("response-username")
				.withPassword(Password.of("response-password"))
				.build();
	}

	@Override
	public Response failure(Request request) {
		throw new TestException("An error occurred");
	}
}
