import java.time.LocalDate;
public class Transaction{
    private String bookid;
    private String userid;
    private LocalDate issuedate;
    private LocalDate returndate;
    public Transaction(String bookid,String userid){
        this.bookid=bookid;
        this.userid=userid;
        this.issuedate=LocalDate.now();
        this.returndate=null;
    }
    public void returnBook(){
        this.returndate=LocalDate.now();
    }
    @Override
    public String toString(){
        return "Book: "+bookid+
        " | User: "+userid+
        " | Issued: "+issuedate+
        " | Returned: "+returndate;
    }
}