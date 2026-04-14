import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Library {

    public String login(String userId, String password) {
        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM users WHERE user_id=? AND password=?");

            ps.setString(1, userId);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getString("role");
            }

        } catch (Exception e) {
            System.out.println("Login failed: " + e.getMessage());
        }
        return null;
    }

    public void registerUser(User user) {
        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO users VALUES (?, ?, ?, ?)");

            ps.setString(1, user.getUserId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getPassword());
            ps.setString(4,user.getRole());
            ps.executeUpdate();
            System.out.println("User registered successfully.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void addBook(Book book) {
        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO books VALUES (?, ?, ?, ?)");

            ps.setString(1, book.getBookId());
            ps.setString(2, book.getTitle());
            ps.setString(3, book.getAuthor());
            ps.setBoolean(4, false);

            ps.executeUpdate();
            System.out.println("Book added successfully.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void issueBook(String bookId, String userId) {
        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement check = con.prepareStatement(
                "SELECT is_issued FROM books WHERE book_id=?");
            check.setString(1, bookId);

            ResultSet rs = check.executeQuery();

            if (!rs.next()) {
                System.out.println("Book not found.");
                return;
            }

            if (rs.getBoolean("is_issued")) {
                System.out.println("Book already issued.");
                return;
            }

            LocalDate today = LocalDate.now();
            LocalDate due = today.plusDays(7);

            PreparedStatement ps1 = con.prepareStatement(
                "UPDATE books SET is_issued=true WHERE book_id=?");
            ps1.setString(1, bookId);
            ps1.executeUpdate();

            PreparedStatement ps2 = con.prepareStatement(
                "INSERT INTO transactions(book_id,user_id,issue_date,due_date) VALUES (?, ?, ?, ?)");

            ps2.setString(1, bookId);
            ps2.setString(2, userId);
            ps2.setDate(3, Date.valueOf(today));
            ps2.setDate(4, Date.valueOf(due));

            ps2.executeUpdate();

            System.out.println("Book issued. Due date: " + due);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void returnBook(String bookId) {
        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "SELECT due_date FROM transactions WHERE book_id=? AND return_date IS NULL");

            ps.setString(1, bookId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                LocalDate due = rs.getDate("due_date").toLocalDate();
                LocalDate today = LocalDate.now();

                long late = ChronoUnit.DAYS.between(due, today);

                if (late > 0) {
                    System.out.println("Late by " + late + " days. Fine: ₹" + (late * 10));
                }

                PreparedStatement ps1 = con.prepareStatement(
                    "UPDATE books SET is_issued=false WHERE book_id=?");
                ps1.setString(1, bookId);
                ps1.executeUpdate();

                PreparedStatement ps2 = con.prepareStatement(
                    "UPDATE transactions SET return_date=? WHERE book_id=? AND return_date IS NULL");

                ps2.setDate(1, Date.valueOf(today));
                ps2.setString(2, bookId);

                ps2.executeUpdate();

                System.out.println("Book returned.");

            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void showBooks() {
        try {
            Connection con = DBConnection.getConnection();

            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM books");

            while (rs.next()) {
                System.out.println(
                    rs.getString("book_id") + " | " +
                    rs.getString("title") + " | " +
                    rs.getString("author") + " | Issued: " +
                    rs.getBoolean("is_issued")
                );
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void searchBook(String keyword) {
        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM books WHERE title LIKE ?");
            ps.setString(1, "%" + keyword + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println(
                    rs.getString("title") + " | " +
                    rs.getString("author"));
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void showUserTransactions(String userId) {
        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM transactions WHERE user_id=?");

            ps.setString(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println(
                    rs.getString("book_id") + " | " +
                    rs.getDate("issue_date") + " | " +
                    rs.getDate("return_date"));
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}