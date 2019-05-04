package ar.edu.itba.paw.webapp.form;

public class LogInForm {
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
