public class User {
    private String userid;
    private String name;
    private String password;
    private String role;
    public User(String userid, String name,String password,String role){
        this.userid=userid;
        this.name=name;
        this.password=password;
        this.role=role;
    }
    public String getUserId(){
        return userid;
    }
    public String getName(){
        return name;
    }
    public String getPassword(){
        return password;
    }
    public String getRole(){
        return role;
    }
    @Override
    public String toString(){
        return userid+" | "+name;
    }
}
