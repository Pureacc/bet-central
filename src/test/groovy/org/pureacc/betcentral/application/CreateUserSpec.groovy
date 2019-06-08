package org.pureacc.betcentral.application

import org.pureacc.betcentral.application.api.CreateUser
import org.pureacc.betcentral.domain.model.User
import org.pureacc.betcentral.domain.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Unroll

import javax.validation.ConstraintViolationException

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic
import static org.pureacc.betcentral.application.api.CreateUser.Request

class CreateUserSpec extends ApplicationSpec {
    private static final int USERNAME_LENGTH_MIN = 8
    private static final int USERNAME_LENGTH_MAX = 32

    @Autowired
    CreateUser createUser
    @Autowired
    UserRepository userRepository

    @Unroll
    def "I can create a new user with username #username"() {
        when: "I create a new user with username #username"
        Request request = Request.newBuilder().withUsername(username).build()
        createUser.execute(request)

        then: "The new user is created"
        User user = userRepository.get(username)
        user.id != null
        user.username == username
        user.balance.isEmpty()

        where:
        username    | _
        "Bettor420" | _
        "John Doe"  | _
    }

    @Unroll
    def "I cannot create a new user with username #username"() {
        when: "I create a new user with username #username"
        Request request = Request.newBuilder().withUsername(username).build()
        createUser.execute(request)

        then: "An exception is thrown"
        thrown ConstraintViolationException

        where:
        username                                  | _
        ""                                        | _
        "   "                                     | _
        null                                      | _
        randomAlphabetic(USERNAME_LENGTH_MIN - 1) | _
        randomAlphabetic(USERNAME_LENGTH_MAX + 1) | _
    }
}
