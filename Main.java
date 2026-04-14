import java.util.*;
public class Main {
    public static void main(String[] args){
        Library library=new Library();
        Scanner sc=new Scanner(System.in);
        while(true){
            System.out.println("\n===== WELCOME =====");
            System.out.println("1. login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            int choice=sc.nextInt();
            sc.nextLine();
            if(choice==1){
                System.out.println("User ID: ");
                String uid=sc.nextLine();
                System.out.println("Password: ");
                String pass=sc.nextLine();
                String role=library.login(uid,pass);
                if(role==null){
                    System.out.println("Invalid login");
                    continue;
                }
                if(role.equalsIgnoreCase("admin")){
                    while(true){
                        System.out.println("\n===== ADMIN PANNEL =====");
                        System.out.println("1. Add book");
                        System.out.println("2. Issue book");
                        System.out.println("3. View books");
                        System.out.println("4. Logout");
                        int ch=sc.nextInt();
                        sc.nextLine();
                        switch(ch){
                            case 1:
                                System.out.println("Book ID: ");
                                String id=sc.nextLine();
                                System.out.println("Title: ");
                                String title=sc.nextLine();
                                System.out.println("Author: ");
                                String author=sc.nextLine();
                                library.addBook(new Book(id,title,author));
                                break;
                            case 2:
                                System.out.println("Book ID: ");
                                String bookid=sc.nextLine();
                                System.out.println("Enter User ID to issue book:");
                                String userid=sc.nextLine();
                                library.issueBook(bookid,userid);
                                break;
                            case 3:
                                library.showBooks();
                                break;
                            case 4:
                                System.out.println("Admin Logged out");
                                break;
                        }
                        if(ch==4) break;
                    }
                }
        else{
        while(true){
            System.out.println("\n===== LIBRARY SYSTEM =====");
            System.out.println("1. View books");
            System.out.println("2. Search Books");
            System.out.println("3. Return book");
            System.out.println("4. My transactions");
            System.out.println("5. Logout");
            int ch=sc.nextInt();
            sc.nextLine();
            switch(ch){
                case 1:
                    library.showBooks();
                    break;
                case 2:
                    System.out.println("Enter keyword to search: ");
                    library.searchBook(sc.nextLine());
                    break;
                case 3:
                    System.out.println("Book ID: ");
                    library.returnBook(sc.nextLine());
                    break;
                case 4:
                    library.showTransactions(uid);
                    break;
                case 5:
                    System.out.println("User Logged out");
                    break;
            }
            if(ch==5) break;
        }
        }
    }
        else if(choice==2){
            System.out.println("User ID: ");
            String uid=sc.nextLine();
            System.out.println("Name: ");
            String name=sc.nextLine();
            System.out.println("Password: ");
            String pass=sc.nextLine();
            System.out.println("Role (admin/User)");
            String role=sc.nextLine();
            library.registerUser(new User(uid,name,pass,role));

        }else if(choice==3){
            System.out.println("Exiting...");
            break;
        }else{
            System.out.println("Invalid option");
        }
    }   
    sc.close();
}
}