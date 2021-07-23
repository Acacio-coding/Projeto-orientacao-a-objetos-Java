public class Post{
    int authorId;
    String author;
    String content;

    Like likes;
    Comment comments;
    Share shares;

    public int getAuthorId(){
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getAuthor(){
        return author;
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content = content;
    }
}
