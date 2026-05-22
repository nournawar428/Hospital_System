public abstract class User {

    private String id;
    private String name;
    private String username;
    private String password;
    private String phoneNumber;

    public User(String id, String name, String username, String password, String phoneNumber){
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getUsername() { return username; }
    public String  getPassword() { return password; }
    public String getPhoneNumber() { return phoneNumber; }

    public void viewProfile() {
    System.out.println("ID: " + id);
    System.out.println("Name: " + name);
    System.out.println("Phone: " + phoneNumber);
    System.out.println("Username: " + username);
    System.out.println("Password: " + password);
    }

}