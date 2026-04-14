public class Book{
    private String bookid;
    private String title;
    private String author;
    private boolean isIssued;
    public Book(String bookid, String title, String author){
        this.bookid=bookid;
        this.title=title;
        this.author=author;
        this.isIssued=false;
    }
    public String getBookId(){
        return bookid;
    }
    public String getTitle(){
        return title;
    }
    public String getAuthor(){
        return author;
    }
    public boolean isIssued(){
        return isIssued;
    }
    public void returnBook(){
        isIssued=false;
    }
    @Override
    public String toString(){
        return bookid+" | "+title+"| "+author+" | Issued: "+isIssued; 
    }
}