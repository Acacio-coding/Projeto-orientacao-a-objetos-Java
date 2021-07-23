public class Friend{
    int numFriends;
    int[] userId = new int[4];
    String[] userName = new String[4];

    public int getUserId(int position){
        return userId[position];
    }

    public void setUserId(int userId, int position){
        this.userId[position] = userId;
    }

    public int getNumFriends(){
        return numFriends;
    }

    public void setNumFriends(int num){
        this.numFriends++;
    }

    public String getUserName(int position){
        return userName[position];
    }

    public void setUserName(String userName, int position){
        this.userName[position] = userName;
    }
}
