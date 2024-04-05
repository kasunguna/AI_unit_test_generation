package org.example;

public class User {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Role role;
    public User(){

    }

    public User(String username1, String password1, String firstName1, String lastName1, Role role1) {
        username = username1;
        password = password1;
        firstName = firstName1;
        lastName = lastName1;
        role = role1;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
