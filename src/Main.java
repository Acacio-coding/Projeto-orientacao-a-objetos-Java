import java.util.Scanner;

public class Main{
    static Scanner input = new Scanner(System.in);
    static User[] users = new User[5];
    static Post[] posts = new Post[25];
    static int numUser, loggedUserId, numPost;
    static boolean menuAccessed;

    public static void main(String[] args) {
        int op;
        boolean statement;

        do {
            op = initialMenu();
            switch(op){
                case 1 -> {
                    String email, password;
                    do {
                        System.out.println("\n------------------------------------");
                        System.out.println("Email: ");
                        email = input.next();

                        System.out.println("Senha: ");
                        password = input.next();

                        if(!email.isBlank() && !password.isBlank()){
                            break;
                        }

                        System.out.println("------------------------------------");
                        System.out.println("There are empty fields!");
                    }while(true);

                    statement = login(email, password);

                    if(!statement) {
                        System.out.println("------------------------------------");
                        System.out.println("Error! Wrong password or email\n");

                    }else if(users[loggedUserId].isFirstLogin()){
                        System.out.println("\nIt seems it's your first login on Caralivro, let's configure your profile!");

                        do{
                            System.out.println("\n------------------------------------");
                            System.out.println("Name:");
                            String name = input.next();

                            System.out.println("Date of birth (dd-mm-yyyy): ");
                            String birth = input.next();

                            System.out.println("Gender (m/f/o): ");
                            String gender = input.next();

                            if(name.isBlank() || birth.isBlank() || gender.isBlank()) {
                                System.out.println("------------------------------------");
                                System.out.println("There are empty fields!");

                            }else if(birth.length() != 10) {
                                System.out.println("------------------------------------");
                                System.out.println("Invalid input for date of birth!");

                            }else if(!(gender.equals("m") || gender.equals("M") || gender.equals("f") || gender.equals("F") || gender.equals("o") || gender.equals("O"))) {
                                System.out.println("------------------------------------");
                                System.out.println("Invalid option for gender!");
                            }else {
                                users[loggedUserId].userProfile.setName(name);
                                users[loggedUserId].userProfile.setBirth(birth);
                                users[loggedUserId].userProfile.setGender(gender);
                                users[loggedUserId].setFirstLogin(false);
                                System.out.println("------------------------------------");
                                System.out.println("Profile configured successfully!\n");
                                break;
                            }
                        }while(true);
                    }

                    if(statement) {
                        do {
                            op = loggedMenu();
                            switch(op){
                                case 1 -> {
                                    menuAccessed = true;
                                    System.out.println("\n------------------------------------");
                                    System.out.println("Redirecting...");
                                    viewHome();
                                }

                                case 2 -> {
                                    menuAccessed = true;

                                    System.out.println("\n------------------------------------");
                                    System.out.println("Content:");
                                    String content = input.next();

                                    if(content.isBlank()) {
                                        System.out.println("------------------------------------");
                                        System.out.println("Content is empty!");
                                    }else {
                                        createPost(content);
                                    }
                                }

                                case 3-> {
                                    menuAccessed = true;
                                    showProfile();
                                }


                                case 4 -> {
                                    menuAccessed = true;

                                    System.out.println("\n------------------------------------");
                                    System.out.println("Which field would you like to edit?");
                                    System.out.println("1 - Name");
                                    System.out.println("2 - Date of birth");
                                    System.out.println("3 - Gender");
                                    System.out.println("4 - Cancel");
                                    System.out.println("Option: ");
                                    op = input.nextInt();

                                    if(op == 4){
                                        System.out.println("\n------------------------------------");
                                        System.out.println("Cancelled by user!");
                                    }

                                    if (op > 4 || op < 1) {
                                        System.out.println("\n------------------------------------");
                                        System.out.println("Invalid option!");
                                    }else {
                                        editProfile(op);
                                    }
                                }

                                case 5 -> {
                                    menuAccessed = true;
                                    viewFriendList();
                                }

                                case 9 -> {
                                    menuAccessed = false;
                                    System.out.println("\n------------------------------------");
                                    System.out.println("Logging out...");
                                }

                                default -> {
                                    System.out.println("\n------------------------------------");
                                    System.out.println("Invalid option!");
                                }

                            }
                        }while(op != 9);
                    }
                }

                case 2 -> {
                    String email, password;
                    boolean notRegistered = true;
                    do {
                        System.out.println("\n------------------------------------");
                        System.out.println("Email: ");
                        email = input.next();

                        System.out.println("Senha: ");
                        password = input.next();

                        if(email.isBlank() || password.isBlank()) {
                            System.out.println("------------------------------------");
                            System.out.println("There are empty fields!");

                        }else if(password.length() < 5) {
                            System.out.println("------------------------------------");
                            System.out.println("Password too short!");
                        }else {
                            break;
                        }
                    }while(true);

                    if(numUser > 0) {
                        for (int i = 0; i < numUser; i++) {
                            if (email.equals(users[i].getEmail())) {
                                System.out.println("------------------------------------");
                                System.out.println("Email is already registered!\n");
                                notRegistered = false;
                                break;
                            }
                        }
                    }

                    if(notRegistered) {
                        register(email, password);
                    }
                }

                case 3 -> {
                    System.out.println("\n------------------------------------");
                    System.out.println("Finished by user!");
                }

                default -> {
                    System.out.println("\n------------------------------------");
                    System.out.println("Invalid option!\n");
                }
            }
        }while(op != 3);
        input.close();
    }

    static int initialMenu() {
        input.useDelimiter("\r?\n");
        System.out.println("\n------- Welcome to Caralivro -------");
        System.out.println("1 - Login");
        System.out.println("2 - Register");
        System.out.println("3 - Quit");
        System.out.println("Option: ");
        return input.nextInt();
    }

    public static void register(String email, String password) {
        User user = new User();
        user.setId(numUser);
        user.setEmail(email);
        user.setPassword(password);
        user.setFirstLogin(true);

        Profile profile = new Profile();
        profile.setName("null");
        profile.setBirth("null");
        profile.setGender("null");
        user.userProfile = profile;

        Friend friends = new Friend();
        for(int i = 0; i < (users.length - 1); i++){
            friends.setUserId(-1, i);
            friends.setUserName("null", i);
        }
        user.friendList = friends;

        users[numUser] = user;
        numUser++;

        System.out.println("------------------------------------");
        System.out.println("Registered successfully!\n");
    }

    static boolean login(String email, String password){
        for(int i = 0; i < numUser; i++){
            if(email.equals(users[i].getEmail()) && password.equals(users[i].getPassword())) {
                System.out.println("------------------------------------");
                System.out.println("Redirecting...");
                loggedUserId = users[i].getId();
                return true;
            }
        }
        return false;
    }

    static int loggedMenu() {
        input.useDelimiter("\r?\n");

        if(!menuAccessed) {
            System.out.println("\n------------------------------------");
            System.out.println("Welcome " + users[loggedUserId].userProfile.getName() + "!");
            System.out.println("------------------------------------");

            System.out.println("1 - View posts");
        }else {
            System.out.println("\n1 - View posts");
        }
        System.out.println("2 - Create post");
        System.out.println("3 - View Profile");
        System.out.println("4 - Edit profile");
        System.out.println("5 - View friend list");
        System.out.println("9 - Logout");
        System.out.println("Option: ");
        return input.nextInt();
    }

    static void viewHome() {
        menuAccessed = true;

        int countPost = 0, authorId = -1, i, j;

        if(numPost == 0){
            System.out.println("\nThere's no post added yet.");
            System.out.println("--------------------------------------");
        }

        for(i = 0; i < numPost; i++){
            System.out.println("\n--------------------------------------");
            System.out.println("Author: " + posts[i].getAuthor());
            System.out.println("Content: " + posts[i].getContent());
            System.out.println("--------------------------------------");

            countPost++;

            //Friendlist
            if(posts[i].getAuthorId() != loggedUserId) {
                for(j = 0; j < users[loggedUserId].friendList.userName.length; j++) {
                    if(users[loggedUserId].friendList.getUserId(j) == posts[i].getAuthorId()) {
                        break;
                    }else {
                        authorId = posts[i].getAuthorId();
                    }
                }

                if(authorId != -1) {
                    addFriend(i);
                }
            }

            //Like
            if(posts[i].getAuthorId() != loggedUserId && posts[i].likes.getUserId(loggedUserId) != loggedUserId) {
                likePost(i);
            }

            //Comment
            commentPost(i);

            //Share
            if(posts[i].getAuthorId() != loggedUserId && posts[i].shares.getUserId(loggedUserId) != loggedUserId) {
                sharePost(i);
            }

            //Details
            showDetailsPost(i);

            if(numPost != countPost) {
                System.out.println("\nLooking for more posts...");
            }else {
                System.out.println("\nThere's no post left to show.");
            }
            System.out.println("------------------------------------");
        }
    }

    static void createPost(String content) {
        input.useDelimiter("\r?\n");

        int i;

        Post post = new Post();
        post.setAuthorId(loggedUserId);
        post.setAuthor(users[loggedUserId].userProfile.getName());
        post.setContent(content);

        Like like = new Like();
        Share share = new Share();

        for(i = 0; i < (users.length - 1); i++){
            like.setUserId(-1, i);
            like.setUserName("null", i);

            share.setUserId(-1, i);
            share.setUserName("null", i);
        }

        Comment comment = new Comment();

        for(i = 0; i < posts.length; i++){
            comment.setUserId(-1, i);
            comment.setContent("null", i);
            comment.setUserName("null", i);
        }

        post.likes = like;
        post.comments = comment;
        post.shares = share;
        posts[numPost] = post;

        numPost++;

        System.out.println("\nPost created!");
        System.out.println("------------------------------------");
    }

    static void likePost(int i) {
        input.useDelimiter("\r?\n");

        boolean exit = false;

        do{
            System.out.println("\nWould you like to leave a like to the post? (y/n):");
            String answer = input.next();

            switch(answer){
                case "" -> System.out.println("\nOption is empty!");

                case "n", "N" -> exit = true;

                case "y", "Y" -> {
                    posts[i].likes.setUserId(loggedUserId, loggedUserId);
                    posts[i].likes.setNumLikes();
                    posts[i].likes.setUserName(users[loggedUserId].userProfile.getName(), loggedUserId);
                    System.out.println("\nPost liked successfully!");
                    exit = true;
                }

                default -> System.out.println("\nInvalid option!");
            }
        }while(!exit);
    }

    static void commentPost(int i) {
        input.useDelimiter("\r?\n");

        int  numCommentariesLoggedUser = 0;
        boolean exit = false;

        do {
            System.out.println("\nWould you like to leave a comment on this post? (y/n):");
            String answer = input.next();

            for(int j = 0; j < posts[i].comments.getNumComments(); j++) {
               if(posts[i].comments.getUserId(j) == loggedUserId) {
                   numCommentariesLoggedUser++;
               }
            }

            if(numCommentariesLoggedUser == 5) {
                System.out.println("\nYou've already reached the limit of commentaries for this post!");
                exit = true;
            }else {
                switch(answer){
                    case "" -> System.out.println("\nOption is empty!");

                    case "n", "N" -> exit = true;

                    case "y", "Y" -> {
                        int j = 0;
                        System.out.println("\nContent: ");
                        String content = input.next();

                        if(content.isBlank()){
                            System.out.println("Content is empty!");

                        }else{
                            if(posts[i].comments.getNumComments() > 0) {
                                for(j = 0; j < posts.length; j++) {
                                    if(posts[i].comments.getUserId(j) == -1) {
                                        posts[i].comments.setNumComments();
                                        posts[i].comments.setUserId(loggedUserId, j);
                                        posts[i].comments.setUserName(users[loggedUserId].userProfile.getName(), j);
                                        posts[i].comments.setContent(content, j);
                                        System.out.println("\nComment added successfully!");
                                        break;
                                    }
                                }
                            }else {
                                posts[i].comments.setNumComments();
                                posts[i].comments.setUserId(loggedUserId, j);
                                posts[i].comments.setUserName(users[loggedUserId].userProfile.getName(), j);
                                posts[i].comments.setContent(content, j);
                                System.out.println("\nComment added successfully!");
                            }
                            exit = true;
                        }
                    }
                    default -> System.out.println("\nInvalid option!");
                }
            }
        }while(!exit);
    }

    static void sharePost(int i) {
        input.useDelimiter("\r?\n");

        boolean exit = false;

        do {
            System.out.println("\nWould you like to share the post? (y/n):");
            String answer = input.next();

            switch(answer) {
                case "" -> System.out.println("\nOption is empty!");

                case "n", "N" -> exit = true;

                case "y", "Y" -> {
                    posts[i].shares.setUserId(loggedUserId, loggedUserId);
                    posts[i].shares.setNumShares();
                    posts[i].shares.setUserName(users[loggedUserId].userProfile.getName(), loggedUserId);
                    System.out.println("\nPost shared successfully!");
                    exit = true;
                }

                default -> System.out.println("\nInvalid option!");
            }
        }while(!exit);
    }

    static void showDetailsPost(int i) {
        input.useDelimiter("\r?\n");

        boolean exit = false;
        int j;

        do{
            System.out.println("\nWould you like to get more details about this post? (y/n)");
            String answer = input.next();

            switch(answer){
                case "" -> System.out.println("\nOption is empty!");

                case "n", "N" -> exit = true;

                case "y", "Y" -> {

                    System.out.println("\n------------------------------------");
                    System.out.println("Likes: " + posts[i].likes.getNumLikes());
                    System.out.println("Comments: " + posts[i].comments.getNumComments());
                    System.out.println("Shares: " + posts[i].shares.getNumShares());
                    System.out.println("------------------------------------");

                    if(posts[i].likes.getNumLikes() != 0){
                        System.out.println("People that liked the post:");
                        for(j = 0; j <= posts[i].likes.getNumLikes(); j++){
                            if(posts[i].likes.getUserId(j) != -1){
                                System.out.println(posts[i].likes.getUserName(j));
                            }
                        }
                        System.out.println("------------------------------------");
                    }

                    if(posts[i].comments.getNumComments() != 0) {
                        System.out.println("Commentaries:");
                        for (j = 0; j < posts[i].comments.getNumComments(); j++) {
                            if (!posts[i].comments.getUserName(j).equals("null")) {
                                System.out.println("\n[" + j + "]");
                                System.out.println("User: " + posts[i].comments.getUserName(j));
                                System.out.println("Commentary: " + posts[i].comments.getContent(j));
                            }
                        }
                        System.out.println("------------------------------------");
                    }

                    if(posts[i].shares.getNumShares() != 0){
                        System.out.println("People who shared this post:");
                        for(j = 0; j <= posts[i].shares.getNumShares(); j++){
                            if(posts[i].shares.getUserId(j) != -1){
                                System.out.println(posts[i].shares.getUserName(j));
                            }
                        }
                        System.out.println("------------------------------------");
                    }
                    exit = true;
                }

                default -> System.out.println("\nInvalid option!");
            }
        }while(!exit);
    }

    static void showProfile() {
        int i, j, count = 0;
        boolean postedOnce = false;
        boolean sharedOnce = false;
        System.out.println("\n------------------------------------");
        System.out.println("Profile: ");
        System.out.println("------------------------------------");
        System.out.println("Name: " + users[loggedUserId].userProfile.getName());
        System.out.println("Date of birth: " + users[loggedUserId].userProfile.getBirth());
        System.out.println("Gender: " + users[loggedUserId].userProfile.getGender());
        System.out.println("------------------------------------");

        System.out.println("Your posts:");
        if(numPost > 0) {
            for (i = 0; i < numPost; i++) {
                if (posts[i].getAuthorId() == loggedUserId) {
                    System.out.println("\n[" + count + "]");
                    System.out.println("Content: " + posts[i].getContent());
                    System.out.println("------------------------------------");
                    System.out.println("Likes: " + posts[i].likes.getNumLikes());
                    System.out.println("Comments: " + posts[i].comments.getNumComments());
                    System.out.println("Shares: " + posts[i].shares.getNumShares());
                    System.out.println("------------------------------------");
                    postedOnce = true;
                    count++;
                }
            }
        }

        if(!postedOnce) {
            System.out.println("\nYou haven't created any post yet");
            System.out.println("------------------------------------");
        }

        System.out.println("Shared posts:");
        if (numPost > 0) {
            for (j = 0; j < numPost; j++) {
                if (posts[j].shares.getUserId(loggedUserId) == loggedUserId) {
                    System.out.println("\n" + "[" + j + "]");
                    System.out.println("Author: " + posts[j].getAuthor());
                    System.out.println("Content: " + posts[j].getContent());
                    sharedOnce = true;
                    System.out.println("------------------------------------");
                }
            }
        }

        if(!sharedOnce) {
            System.out.println("\nYou haven't shared any post yet");
            System.out.println("------------------------------------");
        }
    }

    static void editProfile(int op) {
        input.useDelimiter("\r?\n");

        int i;

        if(op == 1){
            String name;
            do{
                System.out.println("\n------------------------------------");
                System.out.println("Name: ");
                name = input.next();

                if (name.isBlank()){
                    System.out.println("------------------------------------");
                    System.out.println("Field is empty, please try again");
                }else{
                    break;
                }
            }while(true);

            users[loggedUserId].userProfile.setName(name);

            if(numPost > 0) {
                for(i = 0; i < numPost; i++) {
                    if (posts[i].getAuthorId() == loggedUserId) {
                        posts[i].setAuthor(name);
                    }

                    if (posts[i].likes.getUserId(loggedUserId) == loggedUserId) {
                        posts[i].likes.setUserName(name, loggedUserId);
                    }

                    if (posts[i].shares.getUserId(loggedUserId) == loggedUserId) {
                        posts[i].shares.setUserName(name, loggedUserId);
                    }
                }

                for(i = 0; i < numPost; i++) {
                    for (int j = 0; j < posts.length; j++) {
                        if (posts[i].comments.getUserId(j) == loggedUserId) {
                            posts[i].comments.setUserName(name, j);
                        }
                    }
                }

                for(i = 0; i < numUser; i++) {
                    if (users[i].friendList.getUserId(loggedUserId) == loggedUserId) {
                        users[i].friendList.setUserName(name, loggedUserId);
                    }
                }

                System.out.println("------------------------------------");
                System.out.println("Done!");
                return;
            }
            System.out.println("------------------------------------");
            System.out.println("Done!");
        }

        if(op == 2){
            String birth;

            while (true){
                System.out.println("\n------------------------------------");
                System.out.println("Date of birth (dd-mm-yyyy): ");
                birth = input.next();

                if (birth.isBlank()) {
                    System.out.println("------------------------------------");
                    System.out.println("Field is empty, please try again");

                }else if(birth.length() != 10) {
                    System.out.println("------------------------------------");
                    System.out.println("Invalid input for date of birth!");
                }else {
                    users[loggedUserId].userProfile.setBirth(birth);
                    System.out.println("------------------------------------");
                    System.out.println("Done!");
                    break;
                }
            }
            return;
        }

        if(op == 3){
            String gender;

            while (true){
                System.out.println("\n------------------------------------");
                System.out.println("Gender (m/f/o): ");
                gender = input.next();

                if (gender.isBlank()){
                    System.out.println("------------------------------------");
                    System.out.println("Field is empty, please try again");

                }else if(!(gender.equals("m") || gender.equals("M") || gender.equals("f") || gender.equals("F") || gender.equals("o") || gender.equals("O"))) {
                    System.out.println("------------------------------------");
                    System.out.println("Invalid option for gender!");
                }else{
                    users[loggedUserId].userProfile.setGender(gender);
                    System.out.println("------------------------------------");
                    System.out.println("Done!");
                    break;
                }
            }
        }
    }

    static void viewFriendList() {
        System.out.println("\n------------------------------------");
        System.out.println("Friend List:");
        if(users[loggedUserId].friendList.getNumFriends() != 0){
            for(int i = 0; i < users[loggedUserId].friendList.getNumFriends(); i++){
                System.out.println("\n[" + i + "]");
                System.out.println("ID:" + users[loggedUserId].friendList.getUserId(i));
                System.out.println("User: " + users[loggedUserId].friendList.getUserName(i));
            }
        }else{
            System.out.println("You didn't add any friend yet.");
        }
        System.out.println("------------------------------------");
    }

    static void addFriend(int i) {
        input.useDelimiter("\r?\n");

        boolean exit = false;

        do{
            System.out.println("\nWould you like to add the author of the post to your friend list? (y/n):");
            String answer = input.next();

            switch(answer){
                case "" -> System.out.println("\nOption is empty!");

                case "n", "N" -> exit = true;

                case "y", "Y" -> {
                    users[loggedUserId].friendList.setUserId(posts[i].getAuthorId(), i);
                    users[loggedUserId].friendList.setNumFriends(1);
                    users[loggedUserId].friendList.setUserName(posts[i].getAuthor(), i);
                    System.out.println("\nFriend added successfully!");
                    exit = true;
                }

                default -> System.out.println("\nInvalid option!");
            }
        }while(!exit);
    }
}
