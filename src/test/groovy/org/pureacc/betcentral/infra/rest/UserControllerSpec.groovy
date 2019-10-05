package org.pureacc.betcentral.infra.rest

import org.pureacc.betcentral.application.api.CreateUser
import org.pureacc.betcentral.main.SpringAndReactApplication
import org.pureacc.betcentral.vocabulary.UserId
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static groovy.json.JsonOutput.toJson
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = UserController.class)
@ContextConfiguration(classes = SpringAndReactApplication)
class UserControllerSpec extends Specification {
    @Autowired
    MockMvc mvc
    @SpringBean
    CreateUser createUser = Mock(CreateUser)

    def "POST to #url with username '#username' creates a user and returns the user id"() {
        when: "I create a user with username '#username'"
        def request = new UserController.RegisterWebRequest(username)
        def result = mvc.perform(post(url).contentType(APPLICATION_JSON).content(toJson(request)))

        then: "A user is created"
        1 * createUser.execute(_) >> CreateUser.Response.newBuilder().withUserId(UserId.of(Long.valueOf(userId))).build()
        and: "HTTP status is 200"
        result.andExpect(status().isOk())
        and: "I receive the user id"
        result.andExpect(jsonPath('$.userId').value(userId))

        where:
        url                  | username   || userId
        "/api/user/register" | "John Doe" || "123"
    }
}