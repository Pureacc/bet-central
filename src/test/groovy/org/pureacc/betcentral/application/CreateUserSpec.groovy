package org.pureacc.betcentral.application

import org.pureacc.betcentral.application.api.CreateUser
import org.pureacc.betcentral.domain.model.User
import org.pureacc.betcentral.domain.repository.UserRepository
import org.pureacc.betcentral.main.SpringAndReactApplication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification
import spock.lang.Unroll

import static org.pureacc.betcentral.application.api.CreateUser.Request

@SpringBootTest(classes = SpringAndReactApplication.class)
class CreateUserSpec extends Specification {
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
        user.userId != null
        user.username == username
        user.balance.isEmpty()

        where:
        username    | _
        "Bettor420" | _
        "John Doe"  | _
    }
}
