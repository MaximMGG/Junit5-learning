package com.maxim.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import com.maxim.TestBase;
import com.maxim.dao.UserDao;
import com.maxim.dto.User;
import com.maxim.extension.ConditionalExtension;
import com.maxim.extension.GlobalExtension;
import com.maxim.extension.UserServiceParamResolver;
@Tag("fast")
@Tag("user")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith({
    GlobalExtension.class,
    UserServiceParamResolver.class,
    ConditionalExtension.class,
    // ThhrowableExtentsion.class
    // PostProcessingExtension.class
})
public class UserServiceTest extends TestBase {

    private UserService userService;
    private UserDao userDao;

    private static final User IVAN = User.of(1, "Ivan", "123");
    private static final User PETR = User.of(2, "Petr", "321");

    UserServiceTest(TestInfo testInfo) {
        System.out.println();

    }
    @BeforeAll
    static void init() {
        System.out.println("Before all: ");
    }

    @BeforeEach
    void prepare() {
        System.out.println("Before each: " + this);
        this.userDao = Mockito.spy(new UserDao());
        this.userService = new UserService(userDao);
    }

    @Test
    void shouldDeleteExistedUser() {
        userService.add(IVAN);
        Mockito.doReturn(true).when(userDao).delete(IVAN.getId());


        // Mockito.doReturn(true).when(userDao).delete(Mockito.anyInt());
        boolean delete = userService.delete(IVAN.getId());
        System.out.println(userService.delete(IVAN.getId()));
        System.out.println(userService.delete(IVAN.getId()));

        ArgumentCaptor<Integer> forClass = ArgumentCaptor.forClass(Integer.class);
        Mockito.verify(userDao, Mockito.times(3)).delete(forClass.capture());

        assertThat(forClass.getValue()).isEqualTo(1);

        assertThat(delete).isTrue();
    }
    
    @Test
    void usersEmptyIfNoUserAdded() throws IOException {
        if (true) {
            throw new RuntimeException();
        }
        System.out.println("Test 1: " + this);
        List<User> allUsers = userService.getAll();
        // MatcherAssert.assertThat(allUsers, IsEmptyCollection.empty());
        assertTrue(allUsers.isEmpty(), "User list should be empty");
    }

    @Test
    void usersSizeIfUserAdded() {
        System.out.println("Test 2: " + this);
        userService.add(IVAN, PETR);

        List<User> users = userService.getAll();


        assertThat(users).hasSize(2);
        // assertEquals(2, users.size());
    }


    @Test
    void usersConvertedToMapById() {
        userService.add(IVAN, PETR);

        Map<Integer, User> users = userService.getAllConvertedById();

        // MatcherAssert.assertThat(users, IsMapContaining.hasKey(IVAN.getId()));

        assertAll(
                () -> assertThat(users).containsKeys(IVAN.getId(), PETR.getId()),
                () -> assertThat(users).containsValues(IVAN, PETR)
        );
    }


    @AfterEach
    void delteFrom() {
        System.out.println("After each: " + this);
    }

    @AfterAll
    static void close() {
        System.out.println("After all: ");

    }

    @Nested
    @Tag("login")
    @DisplayName("login functionality testing")
    class Login {

        @Test
        void loginFailIfPasswordNotCorrect() {
            userService.add(IVAN);
            Optional<User> maybeUser = userService.login(IVAN.getName(), "Hello");

            assertTrue(maybeUser.isEmpty());
        }

        // @Test
        @RepeatedTest(value = 5, name = RepeatedTest.LONG_DISPLAY_NAME)
        void loginFailIfUserDoesNotExist(RepetitionInfo repetitionInfo) {
            userService.add(IVAN);
            Optional<User> maybeUser = userService.login("Kolya", IVAN.getPassword());

            assertTrue(maybeUser.isEmpty());
        }

        @Test
        @Timeout(value = 200, unit = TimeUnit.MILLISECONDS)
        @Disabled
        void checkLoginFunctionalityPerformance() {
            System.out.println(Thread.currentThread().getName());
            Optional<User> result = assertTimeoutPreemptively(Duration.ofMillis(200), () -> {
                System.out.println(Thread.currentThread().getName());
                Thread.sleep(300);
                return userService.login("Kolya", IVAN.getPassword());
            });
        }

        @Test
        void loginSuccessIfUserExists() {
            userService.add(IVAN);
            Optional<User> maybeUser = userService.login(IVAN.getName(), IVAN.getPassword());

            assertThat(maybeUser).isPresent();
            maybeUser.ifPresent(user -> assertThat(user).isEqualTo(IVAN));
            // assertTrue(maybeUser.isPresent());
            maybeUser.ifPresent(user -> assertEquals(IVAN, user));
        }

        @Test
        void throwExceptionIfUserNameOrPasswordIsNull() {
            assertAll(
                    () -> {
                        var exception = assertThrows(IllegalArgumentException.class,
                                () -> userService.login(null, "1213"));
                        assertThat(exception.getMessage()).isEqualTo("username or password is null");
                    },
                    () -> assertThrows(IllegalArgumentException.class, () -> userService.login("Petr", null)));
        }

        @ParameterizedTest(name = "{arguments} test")
        // @ArgumentsSource()
        // @NullSource
        // @EmptySource
        // @ValueSource(strings = {
        //     "Ivan", "Petr"
        // })
        // @NullAndEmptySource
        @MethodSource("com.maxim.service.UserServiceTest#getArgumentsForLoginTest")
        // @CsvFileSource(resources = "/login-test-data.csv", delimiter = ',', numLinesToSkip = 1)
        // @CsvSource({
        //     "Ivan,123",
        //     "Petr,321"
        // })
        @DisplayName("login param test")
        void loginParametrizedTest(String username, String password, Optional<User> user) {

            userService.add(IVAN, PETR);

            Optional<User> maybeUser = userService.login(username, password);
            assertThat(maybeUser).isEqualTo(user);

        }

    }
    static Stream<Arguments> getArgumentsForLoginTest() {
        return Stream.of(
            Arguments.of("Ivan", "123", Optional.of(IVAN)),
            Arguments.of("Petr", "321", Optional.of(PETR)),
            Arguments.of("Petr", "21", Optional.empty()),
            Arguments.of("eeer", "321", Optional.empty())
        );
    }
}
