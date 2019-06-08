package org.pureacc.betcentral.application.query;

import org.pureacc.betcentral.application.api.GetUser;
import org.pureacc.betcentral.domain.model.User;
import org.pureacc.betcentral.domain.repository.UserRepository;

@Query
class GetUserQuery implements GetUser {
    private final UserRepository userRepository;

    GetUserQuery(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Response execute(Request request) {
        User user = userRepository.get(request.getUserId());
        return Response.newBuilder()
                .withUsername(user.getUsername())
                .withBalance(user.getBalance().getEuros())
                .build();
    }
}
