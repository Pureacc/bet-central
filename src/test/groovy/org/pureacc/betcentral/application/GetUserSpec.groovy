package org.pureacc.betcentral.application

import org.pureacc.betcentral.application.api.GetUser
import org.pureacc.betcentral.domain.model.User
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Unroll

class GetUserSpec extends ApplicationSpec {
    @Autowired
    GetUser getUser

    @Unroll
    def "I can get a user"() {
        given: "A user"
        User user = users.aUser()

        when: "I get the user"
        GetUser.Response response = getUser.execute(GetUser.Request.newBuilder().withUserId(user.id).build())

        then: "I get his username"
        response.username == user.username
        and: "I get his balance"
        response.balance == user.balance.euros
    }
}
