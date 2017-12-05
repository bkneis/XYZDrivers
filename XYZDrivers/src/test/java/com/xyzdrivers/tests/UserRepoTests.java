/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xyzdrivers.tests;

import com.xyzdrivers.models.User;
import com.xyzdrivers.repositories.RepositoryException;
import com.xyzdrivers.services.SQLService;
import com.xyzdrivers.repositories.UserRepo;
import com.xyzdrivers.services.ColumnValuePair;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.DuplicateKeyException;
import org.junit.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

/**
 *
 * @author Toneo
 */
@SuppressWarnings("ThrowableResultIgnored")
public class UserRepoTests {

    private SQLService sqlService;
    private UserRepo userRepository;
    
    @Before
    public void Before() {
        sqlService = mock(SQLService.class);
        userRepository = new UserRepo(sqlService);
    }

    /* Get (All) */
    @Test
    public void get_ShouldReturnAll() throws RepositoryException, SQLException, DuplicateKeyException {
        List<User> users = getTestUsers();

        List<Object[]> flattenedUsers = deflateUserList(users);

        when(sqlService
                .retrieve(argThat(new TrimmedInsensitiveMatcher("users"))))
                .thenReturn(flattenedUsers);

        List<User> results = userRepository.get();

        for (User testUser : users) {
            assertTrue(matchingUserPresent(results, testUser),
                    "User " + testUser.getId() + " was not returned");
        }
    }

    private List<Object[]> deflateUserList(List<User> users) {
        List<Object[]> flattenedUsers = new ArrayList<>();
        for (User user : users) {
            flattenedUsers.add(deflateUser(user));
        }
        return flattenedUsers;
    }

    private boolean matchingUserPresent(List<User> users, User needle) {
        for (User user : users) {
            if (verifyUsersMatch(needle, user)) {
                return true;
            }
        }

        return false;
    }

    /* Get (Individual User, by id) */
    @Test
    public void get_ShouldReturnUser() throws RepositoryException, DuplicateKeyException, SQLException {
        User bob = new User("bob", "bobthebest01", "good");

        List<User> users = getTestUsers();
        users.add(bob);

        List<Object[]> flattenedUsers = deflateUserList(users);

        when(sqlService
                .retrieve(argThat(new TrimmedInsensitiveMatcher("users"))))
                .thenReturn(flattenedUsers);

        User result = userRepository.get("bob");
        assertUsersMatch(bob, result);
    }

    @Test
    public void get_ShouldReturnNullIfNoUserWithId() throws RepositoryException, SQLException {
        List<User> users = getTestUsers();

        List<Object[]> flattenedUsers = deflateUserList(users);

        when(sqlService
                .retrieve(argThat(new TrimmedInsensitiveMatcher("users"))))
                .thenReturn(flattenedUsers);

        User result = userRepository.get("bobba");
        assertNull(result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void get_ShouldThrowExceptionIfIdIsNull() throws RepositoryException {
        userRepository.get(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void get_ShouldThrowExceptionIfIdIsBlank() throws RepositoryException {
        userRepository.get("");
    }
    
    @Test
    public void getWhere_ShouldGetUsersWithMatchingColumn() throws RepositoryException, SQLException {
        
        List<User> users = getTestUsers();
        
        List<Object[]> flattenedUsers = deflateUserList(users);
        
        String columnName = "password";
        String columnValue = "aoaoaoa";
        
        when(sqlService.retrieve(
                argThat(new TrimmedInsensitiveMatcher("users")), 
                argThat(new TrimmedInsensitiveMatcher("*")),
                argThat(new TrimmedInsensitiveMatcher(columnName)),
                eq(columnValue))).thenReturn(flattenedUsers);
        
        List<User> results = userRepository.getWhere(columnName, columnValue);
        
        verify(sqlService, times(1)).retrieve(
                argThat(new TrimmedInsensitiveMatcher("users")),
                argThat(new TrimmedInsensitiveMatcher("*")),
                argThat(new TrimmedInsensitiveMatcher(columnName)),
                eq(columnValue));
        
        for (User user : users)
        {
            assertTrue(matchingUserPresent(results, user), "User " + user.getId() + " should have been returned");
        }        
    }

    @Test(expected = IllegalArgumentException.class)
    public void getWhere_ShouldThrowExceptionIfKeyColumnNull() throws RepositoryException {
        userRepository.getWhere(null, "value");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void getWhere_ShouldThrowExceptionIfKeyValueNull() throws RepositoryException {
        userRepository.getWhere("column", null);
    }

    @Test
    public void delete_ShouldDeleteUser() throws RepositoryException, SQLException {
        User user = new User("marco", "marco-password0791", "status");

        userRepository.delete(user);

        verify(sqlService, times(1)).remove(
                argThat(new TrimmedInsensitiveMatcher("users")),
                argThat(new TrimmedInsensitiveMatcher("id")),
                argThat(new ExactStringMatcher(user.getId())));
    }

    @Test(expected = IllegalArgumentException.class)
    public void delete_ShouldThrowExceptionIfUserIsNull() throws RepositoryException {
        userRepository.delete(null);
    }

    /* Insert User */
    @Test
    public void insert_ShouldInsertUser() throws RepositoryException, SQLException {
        User bob = new User("bob", "bobthebest01", "good");

        userRepository.insert(bob);

        verify(sqlService, times(1)).insert(
                argThat(new TrimmedInsensitiveMatcher("users")),
                argThat(new DeflatedUserMatcher(bob)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void insert_ShouldThrowExceptionIfUserIsNull() throws RepositoryException {
        userRepository.insert(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void insert_ShouldThrowExceptionIfUserIdIsNull() throws RepositoryException {
        userRepository.insert(new User(null, "test-password", "test-status"));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void insert_ShouldThrowExceptionIfUserIdIsBlank() throws RepositoryException {
        userRepository.insert(new User("", "test-password", "test-status"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void insert_ShouldThrowExceptionIfUserPasswordIsNull() throws RepositoryException {
        userRepository.insert(new User("test-id", null, "test-status"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void insert_ShouldThrowExceptionIfUserPasswordIsBlank() throws RepositoryException {
        userRepository.insert(new User("test-id", "", "test-status"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void insert_ShouldThrowExceptionIfUserStatusIsNull() throws RepositoryException {
        userRepository.insert(new User("test-id", "test-password", null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void insert_ShouldThrowExceptionIfUserStatusIsBlank() throws RepositoryException {
        userRepository.insert(new User("test-id", "test-password", ""));
    }

    /* Update User */
    @Test
    public void update_ShouldUpdateUser() throws RepositoryException, SQLException {
        User bob = new User("bob", "bobthebest01", "good");
        List<ColumnValuePair> expectedColumnValues = new ArrayList<>();
        
        expectedColumnValues.add(new ColumnValuePair("password", bob.getPassword()));
        expectedColumnValues.add(new ColumnValuePair("status", bob.getStatus()));
        
        userRepository.update(bob);

        verify(sqlService, times(1)).update(
                argThat(new TrimmedInsensitiveMatcher("users")),
                argThat(new TrimmedInsensitiveMatcher("id")),
                argThat(new ExactStringMatcher(bob.getId())),
                argThat(new ColumnValuesMatcher(expectedColumnValues)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateUser_ShouldThrowExceptionIfUserIsNull() throws RepositoryException {
        userRepository.update(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void update_ShouldThrowExceptionIfUserIdIsNull() throws RepositoryException {
        userRepository.update(new User(null, "test-password", "test-status")); 
    }

    @Test(expected = IllegalArgumentException.class)
    public void update_ShouldThrowExceptionIfUserIdIsBlank() throws RepositoryException { 
        userRepository.update(new User("", "test-password", "test-status"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void update_ShouldThrowExceptionIfUserPasswordIsNull() throws RepositoryException {
        userRepository.update(new User("test-id", "", "test-status"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void update_ShouldThrowExceptionIfUserPasswordIsBlank() throws RepositoryException {
        userRepository.update(new User("test-id", "", "test-status"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void update_ShouldThrowExceptionIfUserStatusIsNull() throws RepositoryException {
        userRepository.update(new User("test-id", "test-password", null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void update_ShouldThrowExceptionIfUserStatusIsBlank() throws RepositoryException {
        userRepository.update(new User("test-id", "test-password", ""));
    }

    private List<User> getTestUsers() {
        List<User> users = new ArrayList<>();

        users.add(new User("jane", "janethealright02", "ok"));
        users.add(new User("marco91", "42made-up-password42", "astatus"));
        users.add(new User("u8ajkjs", "password", "12345"));

        return users;
    }

    private Object[] deflateUser(User user) {

        return new Object[]{user.getId(), user.getPassword(), user.getStatus()};

    }

    private User inflateUser(Object[] parts) {

        return new User((String) parts[0], (String) parts[1], (String) parts[2]);

    }

    private void assertUsersMatch(User expected, User result) {

        assertEquals(expected.getId(), result.getId());
        assertEquals(expected.getPassword(), result.getPassword());
        assertEquals(expected.getStatus(), result.getStatus());

    }

    private boolean verifyUsersMatch(User expected, User result) {

        // Re-use existing assertion-based logic
        try {
            assertUsersMatch(expected, result);
        } catch (AssertionError ex) {
            return false;
        }

        return true;

    }

}
