package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Pattern;

public class LogInForm {
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    private String email;
    private String password;
    private boolean rememberMe;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isRememberme() {
        return rememberMe;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRememberme(boolean rememberme) {
        this.rememberMe = rememberme;
    }
}
