public class Like{
    int numLikes;
    int[] userId = new int[4];
    String[] userName = new String[4];

    public int getUserId(int position){
        return userId[position];
    }

    public void setUserId(int userId, int position){
        this.userId[position] = userId;
    }

    public int getNumLikes(){
        return numLikes;
    }

    public void setNumLikes(){
        this.numLikes++;
    }

    public String getUserName(int position){
        return userName[position];
    }

    public void setUserName(String userName, int position){
        this.userName[position] = userName;
    }
}
