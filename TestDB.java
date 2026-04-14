public class TestDB {
    public static void main(String[] args){
        try{
            System.out.println(DBConnection.getConnection());
            System.out.println("Connection successful!");
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
