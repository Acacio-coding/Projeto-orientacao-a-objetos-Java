public class Comment{
    int numComments;
    int[] userId = new int[25];
    String[] userName = new String[25];
    String[] content = new String[25];

    public int getUserId(int position){
        return userId[position];
    }

    public void setUserId(int userId, int position){
        this.userId[position] = userId;
    }

    public int getNumComments(){
        return numComments;
    }

    public void setNumComments(){
        this.numComments++;
    }

    public String getUserName(int position){
        return userName[position];
    }

    public void setUserName(String userName, int position){
        this.userName[position] = userName;
    }

    public String getContent(int position){
        return content[position];
    }

    public void setContent(String content, int position){
        this.content[position] = content;
    }
}
