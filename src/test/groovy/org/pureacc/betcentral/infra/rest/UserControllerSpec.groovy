package org.pureacc.betcentral.infra.rest


import org.pureacc.betcentral.application.api.CreateUser
import org.pureacc.betcentral.application.api.GetUser
import org.pureacc.betcentral.main.SpringAndReactApplication
import org.pureacc.betcentral.vocabulary.UserId
import org.pureacc.betcentral.vocabulary.Username
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.core.io.Resource
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import spock.lang.Unroll

import static org.pureacc.betcentral.ResourceReader.asString
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = UserController.class)
@ContextConfiguration(classes = SpringAndReactApplication)
@Unroll
class UserControllerSpec extends Specification {
    @Autowired
    MockMvc mvc
    @SpringBean
    CreateUser createUser = Mock(CreateUser)
    @SpringBean
    GetUser getUser = Mock(GetUser)
    @Value("classpath:web/user-register-request.json")
    Resource userRegisterRequest

    def "POST to #url with username '#username' creates a user and returns the user id"(String url, String username, long userId) {
        when: "I create a user with username '#username'"
        def result = mvc.perform(post(url).contentType(APPLICATION_JSON).content(asString(userRegisterRequest)))

        then: "A user is created"
        1 * createUser.execute({
            it.username == Username.of(username)
        }) >> CreateUser.Response.newBuilder().withUserId(UserId.of(userId)).build()
        and: "HTTP status is 200"
        result.andExpect(status().isOk())
        and: "I receive the user id"
        result.andExpect(jsonPath('$.userId').value(userId))

        where:
        url                  | username   || userId
        "/api/user/register" | "John Doe" || 123
    }

    def "GET to #url with user id '#userId' gets a user"(String url, long userId, String username) {
        when: "I get a user with user id '#userId'"
        def result = mvc.perform(get(url).param("userId", String.valueOf(userId)))

        then: "A user is returned"
        1 * getUser.execute({
            it.userId == UserId.of(userId)
        }) >> GetUser.Response.newBuilder().withUsername(Username.of(username)).build()
        and: "HTTP status is 200"
        result.andExpect(status().isOk())
        and: "I receive the user id"
        result.andExpect(jsonPath('$.userId').value(userId))
        and: "I receive the username"
        result.andExpect(jsonPath('$.username').value(username))

        where:
        url         | userId || username
        "/api/user" | 123    || "John Doe"
    }
}