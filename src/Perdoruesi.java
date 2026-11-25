public class Perdoruesi {
    private String username;
    private String password;
    private String roli; // "ADMIN" ose "KLIENT"

    public Perdoruesi(String username, String password, String roli) {
        this.username = username;
        this.password = password;
        this.roli = roli;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRoli() { return roli; }
}