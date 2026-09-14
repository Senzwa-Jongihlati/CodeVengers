/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codevengersapplication;

/**
 *
 * @author Senzwa4
 */
public class User {
    private String dsFullName;
    private String dsEmail;
    private String dsRole;
    private String dsDepartment;
    private int userID;
    
    public User(int id, String username, String email,String department,String role) {
        this.userID = id;
        this.dsFullName = username;
        this.dsEmail=email;
        this.dsDepartment=department;
        this.dsRole = role;
    }

    public int getUserId() {
        return userID;
    }

    public String getUsername() {
        return dsFullName;
    }
    public String getEmail() {
        return dsEmail;
    }
    public String getDepartment() {
        return dsDepartment;
    }
    public String getRole() {
        return dsRole;
    }
    // --- Setters (optional, only if you want to update user info later) ---
    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setFullName(String dsFullName) {
        this.dsFullName = dsFullName;
    }

    public void setEmail(String dsEmail) {
        this.dsEmail = dsEmail;
    }

    public void setDepartment(String dsDepartment) {
        this.dsDepartment = dsDepartment;
    }

    public void setRole(String dsRole) {
        this.dsRole = dsRole;
    }   
}
