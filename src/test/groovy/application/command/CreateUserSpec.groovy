package application.command

import application.AbstractApplicationSpec
import org.pureacc.betcentral.application.api.CreateUser
import org.pureacc.betcentral.domain.model.User
import org.pureacc.betcentral.domain.repository.UserRepository
import org.pureacc.betcentral.vocabulary.Password
import org.pureacc.betcentral.vocabulary.Username
import org.pureacc.betcentral.infra.security.AccessDeniedException
import org.pureacc.betcentral.vocabulary.exception.UserException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import spock.lang.Unroll

import java.util.regex.Pattern

import static infra.security.web.Authentications.authenticate
import static infra.security.web.Authentications.unauthenticate
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic
import static org.pureacc.betcentral.application.api.CreateUser.Request
import static org.pureacc.betcentral.application.api.CreateUser.Response

class CreateUserSpec extends AbstractApplicationSpec {
    private static final Pattern PASSWORD_PREFIX_PATTERN = Pattern.compile("\\{.+?}")
    private static final int USERNAME_LENGTH_MIN = 8
    private static final int USERNAME_LENGTH_MAX = 32

    @Autowired
    CreateUser createUser
    @Autowired
    UserRepository userRepository

    @Unroll
    def "An unauthenticated user can create a new user with username #username and password #password"() {
        given: "I am unauthenticated"
        unauthenticate()

        when: "I create a new user with username #username and password #password"
        Request request = Request.newBuilder().withUsername(username).withPassword(password).build()
        Response response = createUser.execute(request)

        then: "a new user with the returned id is created"
        User user = userRepository.get(response.userId)
        user.id == response.userId
        and: "the username matches"
        user.username == username
        and: "the password is hashed and can be matched with my raw password"
        String prefixHashedPassword = user.password.value
        String hashedPassword = PASSWORD_PREFIX_PATTERN.split(prefixHashedPassword)[1]
        new BCryptPasswordEncoder().matches(password.value, hashedPassword)
        and: "the user's balance is empty"
        user.balance.isEmpty()

        where:
        username                 | password                 | _
        Username.of("Bettor420") | Password.of("hunter2")   | _
        Username.of("John Doe")  | Password.of("p4ssword!") | _
    }

    @Unroll
    def "An unauthenticated user cannot create a new user with username #username and password #password"() {
        given: "I am unauthenticated"
        unauthenticate()

        when: "I create a new user with username #username and password #password"
        Request request = Request.newBuilder().withUsername(username).withPassword(password).build()
        createUser.execute(request)

        then: "An exception is thrown"
        thrown UserException

        where:
        username                                               | password
        Username.of("")                                        | Password.of("hunter2")
        Username.of("   ")                                     | Password.of("hunter2")
        Username.of(null)                                      | Password.of("hunter2")
        Username.of(randomAlphabetic(USERNAME_LENGTH_MIN - 1)) | Password.of("hunter2")
        Username.of(randomAlphabetic(USERNAME_LENGTH_MAX + 1)) | Password.of("hunter2")
        null                                                   | Password.of("hunter2")
        Username.of("Bettor420")                               | Password.of("")
        Username.of("Bettor420")                               | Password.of("   ")
        Username.of("Bettor420")                               | Password.of(null)
    }

    def "An authenticated user's access is denied"() {
        given: "I am authenticated"
        User user = users.aUser()
        authenticate(user)

        when: "I call the use case"
        createUser.execute(null)

        then: "Access is denied"
        thrown AccessDeniedException
    }
}
