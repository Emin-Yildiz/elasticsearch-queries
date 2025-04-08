package com.example.gatewayservice.model;

public class JwtBody {
    private String userId;
    private String mail;
    private String  role;
    private String subject;
    private Boolean isValid;

    public JwtBody(String userId, String mail, String  role, String subject, Boolean isValid) {
        this.userId = userId;
        this.mail = mail;
        this.role = role;
        this.subject = subject;
        this.isValid = isValid;
    }

    public JwtBody(){}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMail() {
        return mail;
    }

    public void setEmail(String mail) {
        this.mail = mail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean valid) {
        isValid = valid;
    }
}
