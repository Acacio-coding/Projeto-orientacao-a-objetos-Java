import java.util.Iterator;
import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;

public class Main {
    static int userId = 0, loggedUserId, loggedUserIndex;
    static boolean postedOnce, sharedOnce;

    public static void main (String[] args) {
        Scanner input = new Scanner(System.in);
        ArrayList<User> users = new ArrayList<>();
        ArrayList<Post> posts = new ArrayList<>();
        LocalDate today = LocalDate.now();

        int op, day, month, year;
        String email, password, name, gender, title, content, answer;
        boolean menuAccessed = false;

        do {
            System.out.println("\n================================");
            System.out.println("----- Welcome to Caralivro -----");
            System.out.println("--------------------------------");
            System.out.println("1) Login");
            System.out.println("2) Register");
            System.out.println("3) Exit");
            System.out.print("Choose an option: ");
            op = input.nextInt();
            input.nextLine();

            switch (op) {
                case 1 -> {

                    do {
                        System.out.print("\nEmail: ");
                        email = input.nextLine();
                    } while (isEmpty(email));

                    do {
                        System.out.print("\nPassword: ");
                        password = input.nextLine();
                    } while (isEmpty(password));

                    if (!login(users, email, password)) {
                        System.out.println("\n--------------------------------");
                        System.out.println("Wrong email or password!");
                    } else {
                        if (users.get(loggedUserIndex).getName() == null) {
                            System.out.println("\nIt seems it's your first login on Caralivro, let's configure your profile!");

                            do {
                                System.out.print("\nName:");
                                name = input.nextLine();
                            } while (isEmpty(name));

                            do {
                                System.out.println("\nGender:");
                                System.out.println("1) Male");
                                System.out.println("2) Female");
                                System.out.println("3) Others");
                                System.out.print("Choose an option: ");
                                gender = input.nextLine();

                                if (gender.equals("1")) {
                                    gender = "Male";
                                    break;
                                }

                                if (gender.equals("2")) {
                                    gender = "Female";
                                    break;
                                }

                                if (gender.equals("3")) {
                                    gender = "Others";
                                    break;
                                }
                                else
                                    System.out.println("\n--------------------------------");
                                    System.out.println("Invalid option!");

                            } while (true);

                            do {
                                System.out.print("\nDay of birth (1 - 31):");
                                day = input.nextInt();

                                if (day < 1 || day > 31) {
                                    System.out.println("\n--------------------------------");
                                    System.out.println("Invalid day!");
                                }
                            } while (day < 1 || day > 31);

                            do {
                                System.out.print("\nMonth of birth (1 - 12):");
                                month = input.nextInt();

                                if (month < 1 || month > 12) {
                                    System.out.println("\n--------------------------------");
                                    System.out.println("Invalid month!");
                                }
                            } while (month < 1 || month > 12);

                            do {
                                System.out.print("\nYear of birth:");
                                year = input.nextInt();
                                input.nextLine();

                                if (today.getYear() - year < 13) {
                                    System.out.println("\n--------------------------------");
                                    System.out.println("You're too young!");
                                }

                                if (today.getYear() - year > 100) {
                                    System.out.println("\n--------------------------------");
                                    System.out.println("Invalid year!");
                                }
                            } while ((today.getYear() - year < 13) || (today.getYear() - year > 100));

                            users.get(loggedUserIndex).setName(name);
                            users.get(loggedUserIndex).setGender(gender);
                            users.get(loggedUserIndex).setBirth(LocalDate.of(year, month, day));

                            System.out.println("\n--------------------------------");
                            System.out.println("Profile configured successfully!");
                        }
                        System.out.println();

                        do {
                            if (!menuAccessed) {
                                System.out.println("\n================================");
                                System.out.println("Welcome " + users.get(loggedUserIndex).getName() + "!");
                                System.out.println("Today is " + today);
                            }
                            System.out.println("================================");
                            System.out.println("Menu");
                            System.out.println("--------------------------------");
                            System.out.println("1) View posts");
                            System.out.println("2) Create post");
                            System.out.println("3) View profile");
                            System.out.println("4) Edit profile");
                            System.out.println("5) Add friend");
                            System.out.println("6) View friends");
                            System.out.println("7) Remove friend");
                            System.out.println("8) Delete account");
                            System.out.println("9) Logout");
                            System.out.print("Choose an option: ");
                            op = input.nextInt();
                            input.nextLine();

                            switch (op) {
                                case 1 -> {
                                    if (!menuAccessed) menuAccessed = true;

                                    if (posts.size() < 1) {
                                        System.out.println("\n--------------------------------");
                                        System.out.println("There isn't any post created yet!\n");
                                    } else {
                                        for (Post post : posts) {
                                            int countPost = 0;
                                            System.out.println("\n================================");
                                            System.out.println("Posts");
                                            System.out.println("--------------------------------");
                                            System.out.println("\nAuthor: " + post.getAuthor().getName());
                                            System.out.println("Title: " + post.getTitle());
                                            System.out.println("--------------------------------");
                                            System.out.println("Date of creation: " + post.getCreationDate());
                                            System.out.println("--------------------------------");
                                            System.out.println("Content: " + post.getContent());
                                            System.out.println("--------------------------------");
                                            System.out.println("Likes: " + post.getLikes().size());
                                            System.out.println("Comments: " + post.getComments().size());
                                            System.out.println("Shares: " + post.getShares().size());
                                            countPost++;

                                            //Like
                                            if (post.getAuthor().getId() != loggedUserId) {
                                                boolean canLike = true;

                                                for (Like like : post.getLikes()) {
                                                    if (like.getUser().getId() == loggedUserId) {
                                                        canLike = false;
                                                        break;
                                                    }
                                                }

                                                if (canLike) {
                                                    do {
                                                        System.out.println("\nWould you like to react? (y/n)");
                                                        answer = input.nextLine();

                                                        isEmpty(answer);

                                                        if (answer.equals("n") || answer.equals("N")) break;

                                                        if (answer.equals("y") || answer.equals("Y")) {
                                                            Like newLike = new Like(users.get(loggedUserIndex), today);
                                                            post.getLikes().add(newLike);
                                                            System.out.println("\n--------------------------------");
                                                            System.out.println("Done!");
                                                            break;
                                                        }

                                                        else {
                                                            System.out.println("\n--------------------------------");
                                                            System.out.println("Invalid option!");
                                                        }
                                                    } while (true);
                                                }
                                            }

                                            //Comment
                                            do {
                                                System.out.println("\nWould you like to comment? (y/n)");
                                                answer = input.nextLine();

                                                isEmpty(answer);

                                                if (answer.equals("n") || answer.equals("N")) break;

                                                if (answer.equals("y") || answer.equals("Y")) {
                                                    do {
                                                        System.out.print("\nType the content of the comment: ");
                                                        content = input.nextLine();
                                                    } while (isEmpty(content));

                                                    Comment newComment = new Comment(users.get(loggedUserIndex), today, content);
                                                    post.getComments().add(newComment);
                                                    System.out.println("\n--------------------------------");
                                                    System.out.println("Done!");
                                                    break;
                                                }

                                                else {
                                                    System.out.println("\n--------------------------------");
                                                    System.out.println("Invalid option!");
                                                }
                                            } while (true);

                                            //Share
                                            if (post.getAuthor().getId() != loggedUserId) {
                                                boolean canShare = true;

                                                for (Share share : post.getShares()) {
                                                    if (share.getUser().getId() == loggedUserId) {
                                                        canShare = false;
                                                        break;
                                                    }
                                                }

                                                if (canShare) {
                                                    do {
                                                        System.out.println("\nWould you like to share? (y/n)");
                                                        answer = input.nextLine();

                                                        isEmpty(answer);

                                                        if (answer.equals("n") || answer.equals("N")) break;

                                                        if (answer.equals("y") || answer.equals("Y")) {
                                                            Share newShare = new Share(users.get(loggedUserIndex), today);
                                                            post.getShares().add(newShare);
                                                            System.out.println("\n--------------------------------");
                                                            System.out.println("Done!");
                                                            break;
                                                        }

                                                        else {
                                                            System.out.println("\n--------------------------------");
                                                            System.out.println("Invalid option!");
                                                        }
                                                    } while (true);
                                                }
                                            }


                                            //Likes
                                            if (post.getLikes().size() > 0) {
                                                do {
                                                    System.out.println("\nWould you like to view the likes of the post? (y/n)");
                                                    answer = input.nextLine();

                                                    isEmpty(answer);

                                                    if (answer.equals("n") || answer.equals("N")) break;

                                                    if (answer.equals("y") || answer.equals("Y")) {
                                                        System.out.println("\n================================");
                                                        System.out.println("Likes");
                                                        System.out.println("--------------------------------");


                                                        for (Iterator<Like> iterator = post.getLikes().iterator(); iterator.hasNext();) {
                                                            Like like = iterator.next();

                                                            System.out.println("\nUser: " + like.getUser().getName());
                                                            System.out.println("Date: " + like.getLikeDate());

                                                            if (like.getUser().getId() == loggedUserId)
                                                                do {
                                                                    System.out.println("\nWould you like to remove your reaction? (y/n)");
                                                                    answer = input.nextLine();

                                                                    isEmpty(answer);

                                                                    if (answer.equals("n") || answer.equals("N")) break;

                                                                    if (answer.equals("y") || answer.equals("Y")) {
                                                                        iterator.remove();
                                                                        System.out.println("\n--------------------------------");
                                                                        System.out.println("Done!");
                                                                        break;
                                                                    }

                                                                    else {
                                                                        System.out.println("\n--------------------------------");
                                                                        System.out.println("Invalid option!");
                                                                    }

                                                                } while (true);
                                                        }
                                                        break;

                                                    } else {
                                                        System.out.println("\n--------------------------------");
                                                        System.out.println("Invalid option!");
                                                    }
                                                } while (true);
                                            }

                                            //Comments
                                            if (post.getComments().size() > 0) {
                                                do {
                                                    System.out.println("\nWould you like to view the comments of the post? (y/n)");
                                                    answer = input.nextLine();

                                                    isEmpty(answer);

                                                    if (answer.equals("n") || answer.equals("N")) break;

                                                    if (answer.equals("y") || answer.equals("Y")) {
                                                        System.out.println("\n================================");
                                                        System.out.println("Comments");
                                                        System.out.println("--------------------------------");

                                                        for (Iterator<Comment> iterator = post.getComments().iterator(); iterator.hasNext();) {
                                                            Comment comment = iterator.next();

                                                            System.out.println("\nUser: " + comment.getUser().getName());
                                                            System.out.println("Date: "  + comment.getCommentDate());
                                                            System.out.println("Content: " + comment.getContent());

                                                            if (comment.getUser().getId() == loggedUserId) {
                                                                do {
                                                                    System.out.println("\nWould you like to edit this comment? (y/n)");
                                                                    answer = input.nextLine();

                                                                    isEmpty(answer);

                                                                    if (answer.equals("n") || answer.equals("N")) break;

                                                                    if (answer.equals("y") || answer.equals("Y")) {
                                                                        do {
                                                                            System.out.print("\nType the new content of the comment: ");
                                                                            content = input.nextLine();
                                                                        } while (isEmpty(content));

                                                                        comment.setContent(content);
                                                                        System.out.println("\n--------------------------------");
                                                                        System.out.println("Done!\n");
                                                                        break;
                                                                    }

                                                                    else {
                                                                        System.out.println("\n--------------------------------");
                                                                        System.out.println("Invalid option!");
                                                                    }
                                                                } while (true);

                                                                do {
                                                                    System.out.println("\nWould you like to remove this comment? (y/n)");
                                                                    answer = input.nextLine();

                                                                    isEmpty(answer);

                                                                    if (answer.equals("n") || answer.equals("N")) break;

                                                                    if (answer.equals("y") || answer.equals("Y")) {
                                                                        iterator.remove();
                                                                        System.out.println("\n--------------------------------");
                                                                        System.out.println("Done!\n");
                                                                        break;
                                                                    }

                                                                    else {
                                                                        System.out.println("\n--------------------------------");
                                                                        System.out.println("Invalid option!");
                                                                    }
                                                                } while (true);
                                                            }
                                                        }
                                                        break;

                                                    } else {
                                                        System.out.println("\n--------------------------------");
                                                        System.out.println("Invalid option!");
                                                    }
                                                } while (true);
                                            }

                                            //Shares
                                            if (post.getShares().size() > 0) {
                                                do {
                                                    System.out.println("\nWould you like to view the shares of the post? (y/n)");
                                                    answer = input.nextLine();

                                                    isEmpty(answer);

                                                    if (answer.equals("n") || answer.equals("N")) break;

                                                    if (answer.equals("y") || answer.equals("Y")) {
                                                        System.out.println("\n================================");
                                                        System.out.println("Shares");
                                                        System.out.println("--------------------------------");


                                                        for (Iterator<Share> iterator = post.getShares().iterator(); iterator.hasNext();) {
                                                            Share share = iterator.next();

                                                            System.out.println("\nUser: " + share.getUser().getName());
                                                            System.out.println("Date: " + share.getShareDate());

                                                            if (share.getUser().getId() == loggedUserId)
                                                                do {
                                                                    System.out.println("\nWould you like to remove this share? (y/n)");
                                                                    answer = input.nextLine();

                                                                    isEmpty(answer);

                                                                    if (answer.equals("n") || answer.equals("N")) break;

                                                                    if (answer.equals("y") || answer.equals("Y")) {
                                                                        iterator.remove();
                                                                        System.out.println("\n--------------------------------");
                                                                        System.out.println("Done!");
                                                                        break;
                                                                    }

                                                                    else {
                                                                        System.out.println("\n--------------------------------");
                                                                        System.out.println("Invalid option!");
                                                                    }

                                                                } while (true);
                                                        }
                                                        break;

                                                    } else {
                                                        System.out.println("\n--------------------------------");
                                                        System.out.println("Invalid option!");
                                                    }
                                                } while (true);
                                            }

                                            if (posts.size() == countPost) {
                                                System.out.println("\n--------------------------------");
                                                System.out.println("Theres nothing left to show!\n");
                                            }
                                        }
                                    }
                                }

                                case 2 -> {
                                    if (!menuAccessed) menuAccessed = true;

                                    do {
                                        System.out.print("\nType the title of the post: ");
                                        title = input.nextLine();
                                    } while (isEmpty(title));

                                    do {
                                        System.out.print("\nType the content of the post: ");
                                        content = input.nextLine();
                                    } while (isEmpty(content));

                                    createPost(users, posts, title, content, today);
                                }

                                case 3 -> {
                                    if (!menuAccessed) menuAccessed = true;

                                    System.out.println("\n--------------------------------");
                                    System.out.println("Profile");
                                    System.out.println("--------------------------------");
                                    System.out.println("Name: " + users.get(loggedUserIndex).getName());
                                    System.out.println("Gender: " + users.get(loggedUserIndex).getGender());
                                    System.out.println("Date of birth: " + users.get(loggedUserIndex).getBirth());
                                    System.out.println("--------------------------------");
                                    System.out.println("Your posts");
                                    System.out.println("--------------------------------");

                                    if (posts.size() < 1) System.out.println("\nThere isn't any posts created yet!\n");

                                    for (Post post : posts) {
                                        if (post.getAuthor().getId() == loggedUserId) {
                                            System.out.println("\nTitle: " + post.getTitle());
                                            System.out.println("--------------------------------");
                                            System.out.println("Date of creation: " + post.getCreationDate());
                                            System.out.println("--------------------------------");
                                            System.out.println("Content: " + post.getContent());
                                            System.out.println("--------------------------------");
                                            System.out.println("Likes: " + post.getLikes().size());
                                            System.out.println("Comments: " + post.getComments().size());
                                            System.out.println("Shares: " + post.getShares().size());
                                            postedOnce = true;

                                            do {
                                                System.out.println("\nWould you like to edit this post? (y/n)");
                                                answer = input.nextLine();

                                                isEmpty(answer);

                                                if (answer.equals("n") || answer.equals("N")) break;

                                                if (answer.equals("y") || answer.equals("Y")) {
                                                    System.out.println("\nWhich field would you like to edit?");
                                                    System.out.println("1) Title");
                                                    System.out.println("2) Content");
                                                    System.out.print("Choose an option: ");
                                                    op = input.nextInt();
                                                    input.nextLine();

                                                    if (op < 1 || op > 2) {
                                                        System.out.println("\nInvalid option!");
                                                    }

                                                    if (op == 1) {
                                                        do {
                                                            System.out.print("\nType the new title of the post: ");
                                                            title = input.nextLine();
                                                        } while (isEmpty(title));

                                                        post.setTitle(title);

                                                        System.out.println("\n--------------------------------");
                                                        System.out.println("Post edited succesfully!");
                                                        break;
                                                    }

                                                    if (op == 2) {
                                                        do {
                                                            System.out.print("\nType the new content of the post: ");
                                                            content = input.nextLine();
                                                        } while (isEmpty(content));

                                                        post.setContent(content);

                                                        System.out.println("\n--------------------------------");
                                                        System.out.println("Post edited succesfully!");
                                                        break;
                                                    }
                                                } else {
                                                    System.out.println("\n--------------------------------");
                                                    System.out.println("Invalid option!");
                                                }

                                            } while (true);

                                            do {
                                                System.out.println("\nWould you like to remove this post? (y/n)");
                                                answer = input.nextLine();

                                                isEmpty(answer);

                                                if (answer.equals("n") || answer.equals("N")) break;

                                                if (answer.equals("y") || answer.equals("Y")) {
                                                    posts.remove(post);
                                                    System.out.println("\n--------------------------------");
                                                    System.out.println("Post removed successfully!\n");
                                                    break;
                                                }
                                                else System.out.println("\nInvalid option!");
                                            } while (true);
                                        }

                                        if (posts.size() < 1) break;
                                    }

                                    if (!postedOnce && posts.size() > 1)  System.out.println("\nYou didn't create any posts yet!\n");

                                    System.out.println("--------------------------------");
                                    System.out.println("Shared posts");
                                    System.out.println("--------------------------------");

                                    for (Post post : posts) {
                                        for (Share share : post.getShares()) {
                                            if (share.getUser().getId() == loggedUserId) {
                                                System.out.println("\nAuthor: " + post.getAuthor().getName());
                                                System.out.println("Title: " + post.getTitle());
                                                System.out.println("--------------------------------");
                                                System.out.println("Date of share: " + share.getShareDate());
                                                System.out.println("--------------------------------");
                                                System.out.println("Content: " + post.getContent());
                                                sharedOnce = true;
                                            }
                                        }
                                    }

                                    if (!sharedOnce) System.out.println("\nYou didn't share any posts yet!\n");

                                    System.out.println();
                                }

                                case 4 -> {
                                    if (!menuAccessed) menuAccessed = true;

                                    do {
                                        System.out.println("\nWhich field would you like to edit?");
                                        System.out.println("1) Name");
                                        System.out.println("2) Gender");
                                        System.out.println("3) Date of birth");
                                        System.out.println("4) Cancel");
                                        System.out.print("Choose an option: ");
                                        op = input.nextInt();
                                        input.nextLine();

                                        if (op < 1 || op > 4) {
                                            System.out.println("\n--------------------------------");
                                            System.out.println("Invalid option!");
                                        }

                                        if (op == 1) {
                                            do {
                                                System.out.print("\nType your new name: ");
                                                name = input.nextLine();
                                            } while (isEmpty(name));

                                            users.get(loggedUserIndex).setName(name);

                                            System.out.println("\n--------------------------------");
                                            System.out.println("Done!\n");
                                            break;
                                        }

                                        if (op == 2) {
                                            do {
                                                System.out.println("\nGender:");
                                                System.out.println("1) Male");
                                                System.out.println("2) Female");
                                                System.out.println("3) Others");
                                                System.out.print("Choose an option: ");
                                                gender = input.nextLine();

                                                if (gender.equals("1")) {
                                                    gender = "Male";
                                                    break;
                                                }

                                                if (gender.equals("2")) {
                                                    gender = "Female";
                                                    break;
                                                }

                                                if (gender.equals("3")) {
                                                    gender = "Others";
                                                    break;
                                                }
                                                else
                                                    System.out.println("\n--------------------------------");
                                                System.out.println("Invalid option!");

                                            } while (true);

                                            users.get(loggedUserIndex).setGender(gender);
                                            System.out.println("\n--------------------------------");
                                            System.out.println("Done!\n");
                                            break;
                                        }

                                        if (op == 3) {
                                            do {
                                                System.out.print("\nDay of birth (1 - 31):");
                                                day = input.nextInt();

                                                if (day < 1 || day > 31) {
                                                    System.out.println("\n--------------------------------");
                                                    System.out.println("Invalid day!");
                                                }
                                            } while (day < 1 || day > 31);

                                            do {
                                                System.out.print("\nMonth of birth (1 - 12):");
                                                month = input.nextInt();

                                                if (month < 1 || month > 12) {
                                                    System.out.println("\n--------------------------------");
                                                    System.out.println("Invalid month!");
                                                }
                                            } while (month < 1 || month > 12);

                                            do {
                                                System.out.print("\nYear of birth:");
                                                year = input.nextInt();
                                                input.nextLine();

                                                if (today.getYear() - year < 13) {
                                                    System.out.println("\n--------------------------------");
                                                    System.out.println("You're too young!");
                                                }

                                                if (today.getYear() - year > 100) {
                                                    System.out.println("\n--------------------------------");
                                                    System.out.println("Invalid year!");
                                                }
                                            } while ((today.getYear() - year < 13) || (today.getYear() - year > 100));
                                            users.get(loggedUserIndex).setBirth(LocalDate.of(year, month, day));
                                            System.out.println("\n--------------------------------");
                                            System.out.println("Done!\n");
                                            break;
                                        }

                                        if (op == 4) break;
                                    } while (true);
                                }

                                case 5 -> {
                                    if (!menuAccessed) menuAccessed = true;

                                    boolean end = false;

                                    do {
                                        System.out.println("\n================================");
                                        System.out.println("1) List all users");
                                        System.out.println("2) Search user by name");
                                        System.out.println("3) Add user by ID");
                                        System.out.println("4) Cancel");
                                        System.out.print("Choose an option: ");
                                        op = input.nextInt();
                                        input.nextLine();

                                        if (op < 1 || op > 4) {
                                            System.out.println("\n--------------------------------");
                                            System.out.println("Invalid option!");
                                        }

                                        if (op == 1) {
                                            if (users.size() > 1) {
                                                for (User user : users) {
                                                    if (user.getId() != loggedUserId) {
                                                        System.out.println("\n--------------------------------");
                                                        System.out.println("ID: " + user.getId());
                                                        System.out.println("Name: " + user.getName());
                                                    }
                                                }

                                                System.out.println("\n--------------------------------");
                                                System.out.println("There's no one left to show!");
                                            }
                                            else {
                                                System.out.println("\n--------------------------------");
                                                System.out.println("There isn't any user registered yet!");
                                                break;
                                            }
                                        }

                                        if (op == 2) {
                                            if (users.size() < 1) {
                                                System.out.println("\n--------------------------------");
                                                System.out.println("There isn't any user registered yet!");
                                                break;
                                            }

                                            do {
                                                System.out.print("\nType the name of the user: ");
                                                name = input.nextLine();
                                            } while (isEmpty(name));

                                            for (User user : users) {
                                                if (user.getName().equals(name) && user.getId() != loggedUserId){
                                                    System.out.println("\n--------------------------------");
                                                    System.out.println("ID: " + user.getId());
                                                    System.out.println("Name: " + user.getName());
                                                }
                                            }
                                        }

                                        if (op == 3) {
                                            if (users.size() < 1) {
                                                System.out.println("\n--------------------------------");
                                                System.out.println("There isn't any user registered yet!");
                                                break;
                                            }

                                            System.out.print("\nType the user ID: ");
                                            int id = input.nextInt();
                                            input.nextLine();

                                            for (User user : users) {
                                                if (user.getId() == id && user.getId() != loggedUserId) {
                                                    Friend friend = new Friend(user);
                                                    users.get(loggedUserIndex).getFriendList().add(friend);
                                                    System.out.println("\n--------------------------------");
                                                    System.out.println("Done!\n");
                                                    end = true;
                                                    break;
                                                }
                                            }

                                            if (!end) {
                                                System.out.println("\n--------------------------------");
                                                System.out.println("User not found!");
                                            }
                                        }

                                        if (op == 4) end = true;
                                    } while (!end);
                                }

                                case 6 -> {
                                    if (!menuAccessed) menuAccessed = true;

                                    System.out.println("\n================================");
                                    System.out.println("Friendlist");
                                    System.out.println("--------------------------------");
                                    if (users.get(loggedUserIndex).getFriendList().size() < 1)
                                        System.out.println("\nYou didn't add any friend yet!");
                                    else
                                        for (Friend friend : users.get(loggedUserIndex).getFriendList()) {
                                            System.out.println("\nId: " + friend.getUser().getId() + " - Name: " + friend.getUser().getName());
                                        }

                                    System.out.println();
                                }

                                case 7 -> {
                                    if (!menuAccessed) menuAccessed = true;

                                    boolean removed = false;
                                    int id;

                                    System.out.print("\nType the friend ID: ");
                                    id = input.nextInt();
                                    input.nextLine();

                                    for (Iterator<Friend> iterator = users.get(loggedUserIndex).getFriendList().iterator(); iterator.hasNext();) {
                                        Friend friend = iterator.next();

                                        if (friend.getUser().getId() == id) {
                                            iterator.remove();
                                            System.out.println("\n--------------------------------");
                                            System.out.println("Done!\n");
                                            removed = true;
                                            break;
                                        }
                                    }

                                    if (!removed) {
                                        System.out.println("\n--------------------------------");
                                        System.out.println("\nFriend not found!\n");
                                    }
                                }

                                case 8 -> {
                                    if (!menuAccessed) menuAccessed = true;

                                    do {
                                        System.out.println("\nAre you sure you want to delete your account? (y/n)");
                                        answer = input.nextLine();

                                        isEmpty(answer);

                                        if (answer.equals("n") || answer.equals("N")) break;

                                        if (answer.equals("y") || answer.equals("Y")) {
                                            deleteAccount(users, posts);
                                            break;
                                        }
                                        else {
                                            System.out.println("\n--------------------------------");
                                            System.out.println("Invalid option!");
                                        }

                                    } while (true);

                                    if (answer.equals("y") || answer.equals("Y")) op = 9;
                                }

                                case 9 -> {
                                    menuAccessed = false;
                                    loggedUserId = userId + 1;
                                    loggedUserIndex = users.size() + 1;
                                    System.out.println("\n--------------------------------");
                                    System.out.println("Logging out...");
                                }

                                default -> {
                                    System.out.println("\n--------------------------------");
                                    System.out.println("Invalid option!");
                                }
                            }
                        } while (op != 9);
                    }
                }

                case 2 -> {
                    do {
                        System.out.print("\nEmail: ");
                        email = input.nextLine();
                    } while (isEmpty(email));

                    do {
                        System.out.print("\nPassword: ");
                        password = input.nextLine();
                    } while (isEmpty(password));

                    if (register(users, email, password)) {
                        System.out.println("\n--------------------------------");
                        System.out.println("Registered successfully!");
                    } else {
                        System.out.println("\n--------------------------------");
                        System.out.println("Email already registered!");
                    }
                }

                case 3 -> {
                    System.out.println("\n--------------------------------");
                    System.out.println("Finished by user");
                }

                default -> {
                    System.out.println("\n--------------------------------");
                    System.out.println("Invalid option!");
                }
            }
        } while (op != 3);
        input.close();
    }

    //User ----------------------------------------------------------------------------
    static boolean login (ArrayList<User> users, String email, String password) {
        for (User user : users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                loggedUserId = user.getId();
                loggedUserIndex = users.indexOf(user);
                return true;
            }
        }
        return false;
    }

    static boolean register (ArrayList<User> users, String email, String password) {
        if (users.size() == 0) {
            User user = new User(userId, email, password);
            users.add(user);
        } else {
            for (User user : users) {
                if (user.getEmail().equals(email)) return false;
            }
            User user = new User(userId, email, password);
            users.add(user);
        }
        userId++;
        return true;
    }

    static void deleteAccount (ArrayList<User> users, ArrayList<Post> posts) {
        posts.removeIf(post -> post.getAuthor().getId() == loggedUserId);

        for (Post post : posts) {
            post.getLikes().removeIf(like -> like.getUser().getId() == loggedUserId);
            post.getComments().removeIf(comment -> comment.getUser().getId() == loggedUserId);
            post.getShares().removeIf(share -> share.getUser().getId() == loggedUserId);
        }

        for (User user : users) {
            user.getFriendList().removeIf(friend -> friend.getUser().getId() == loggedUserId);
        }

        users.remove(users.get(loggedUserIndex));
        System.out.println("\n--------------------------------");
        System.out.println("Done!");
    }

    //Post ----------------------------------------------------------------------------------------------
    static void createPost (ArrayList<User> users, ArrayList<Post> posts, String title, String content, LocalDate today) {
        Post post = new Post(users.get(loggedUserIndex), title, today, content);
        posts.add(post);

        System.out.println("\n--------------------------------");
        System.out.println("Post created successfully!\n");
    }

    //Validation --------------------------------------------------
    static boolean isEmpty (String variable) {
        if (variable.isBlank() || variable.isEmpty()) {
            System.out.println("\n--------------------------------");
            System.out.println("Empty field!");
            return true;
        }
        return false;
    }
}