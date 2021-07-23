public class User{
    int id;
    String email;
    String password;
    Profile userProfile;
    Friend friendList;
    boolean firstLogin;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isFirstLogin(){
        return firstLogin;
    }

    public void setFirstLogin(boolean firstLogin){
        this.firstLogin = firstLogin;
    }
}
