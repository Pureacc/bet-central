package application.query

import application.AbstractApplicationSpec
import org.pureacc.betcentral.application.api.GetUser
import org.pureacc.betcentral.domain.model.User
import org.pureacc.betcentral.infra.security.AccessDeniedException
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Unroll

import static infra.security.web.Authentications.authenticate
import static infra.security.web.Authentications.unauthenticate

class GetUserSpec extends AbstractApplicationSpec {
    @Autowired
    GetUser getUser

    @Unroll
    def "An authenticated user can get his user information"() {
        given: "I am an authenticated user"
        User user = users.aUser()
        authenticate(user)

        when: "I get my user information"
        GetUser.Response response = getUser.execute(GetUser.Request.newBuilder().withUserId(user.id).build())

        then: "I get his username"
        response.username == user.username
        and: "I get his balance"
        response.balance == user.balance.euros
    }

    def "An authenticated user cannot get someone else's user information"() {
        given: "I am an authenticated user"
        User user = users.aUser()
        authenticate(user)
        and: "Another user"
        User anotherUser = users.aUser()

        when: "I get the information of the other user"
        getUser.execute(GetUser.Request.newBuilder().withUserId(anotherUser.id).build())

        then: "Access is denied"
        thrown AccessDeniedException
    }

    def "An unauthenticated user's access is denied"() {
        given: "I am unauthenticated"
        unauthenticate()

        when: "I call the use case"
        getUser.execute(null)

        then: "Access is denied"
        thrown AccessDeniedException
    }
}
