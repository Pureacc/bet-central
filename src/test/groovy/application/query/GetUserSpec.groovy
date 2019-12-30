package application.query


import org.pureacc.betcentral.application.api.GetUser
import org.pureacc.betcentral.domain.model.User
import org.pureacc.betcentral.infra.security.AccessDeniedException
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Unroll

import static application.factory.Authentications.authenticate
import static application.factory.Authentications.unauthenticate

class GetUserSpec extends application.AbstractApplicationSpec {
    @Autowired
    GetUser getUser

    @Unroll
    def "An authenticated user can get a user"() {
        given: "I am an authenticated user"
        User user = users.aUser()
        authenticate(user)

        when: "I get the user"
        GetUser.Response response = getUser.execute(GetUser.Request.newBuilder().withUserId(user.id).build())

        then: "I get his username"
        response.username == user.username
        and: "I get his balance"
        response.balance == user.balance.euros
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
