package Caralivro;

import java.util.ArrayList;
import java.util.Iterator;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Scanner;
import Caralivro.entities.*;

public class Main {
    static int userId, loggedUserIndex, loggedUserId;
    static boolean postedOnce = false, sharedOnce = false;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        input.useDelimiter("\r?\n");

        ArrayList<Person> people = new ArrayList<>();
        ArrayList<Company> companies = new ArrayList<>();
        ArrayList<Post> posts = new ArrayList<>();
        ArrayList<User> users = new ArrayList<>();

        LocalDate today = LocalDate.now();

        int op, year, month, day;
        String answer, email, password, title, content, name, gender, classification;
        LocalDate birth = null, creationDate = null;
        boolean menuAccessed = false;

        do {
            System.out.println("\n=====================================");
            System.out.println("--------Welcome to Caralivro!--------");
            System.out.println("=====================================");
            System.out.println("1) Login");
            System.out.println("2) Register");
            System.out.println("3) Exit");
            System.out.print("Choose an option: ");
            op = input.nextInt(); input.nextLine();

            if (op < 1 || op > 3) {
                System.out.println("\n=====================================");
                System.out.println("Invalid option!");
            }

            if (op == 1) {
                do {
                    System.out.print("\nEmail: ");
                    email = input.nextLine();
                } while (isEmptyString(email) || isInvalidEmail(email));

                do {
                    System.out.print("\nPassword: ");
                    password = input.nextLine();
                } while (isEmptyString(password) || isInvalidPassword(password));

                if (!login(users, email, password)) {
                    System.out.println("\n=====================================");
                    System.out.println("Wrong email or password!");
                } else {
                    do {
                        if (!menuAccessed) {
                            if (users.get(loggedUserIndex).getType().equals("Person")) {
                                for (Person person : people) {
                                    if (person.getId() == loggedUserId && person.getName() != null) {
                                        System.out.println("\n=====================================");
                                        System.out.println("Welcome " + person.getName() +"!");
                                        break;
                                    }
                                }
                            } else {
                                for (Company company : companies) {
                                    if (company.getId() == loggedUserId && company.getName() != null) {
                                        System.out.println("\n=====================================");
                                        System.out.println("Welcome " + company.getName() +"!");
                                        break;
                                    }
                                }
                            }
                            System.out.println("Today is " + today);
                        }
                        System.out.println("=====================================");
                        System.out.println("Menu");
                        System.out.println("=====================================");
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
                                    System.out.println("\n=====================================");
                                    System.out.println("There isn't any post created yet!\n");
                                } else {
                                    for (Post post : posts) {
                                        int countPost = 0;
                                        System.out.println("\n=====================================");
                                        System.out.println("Posts");
                                        System.out.println("\n=====================================");
                                        if (post.getAuthor().getType().equals("Person")) {
                                            for (Person person : people) {
                                                if (post.getAuthor().getId() == person.getId()) {
                                                    System.out.println("\nAuthor: " + person.getName());
                                                    break;
                                                }
                                            }
                                        } else {
                                            for (Company company : companies) {
                                                if (post.getAuthor().getId() == company.getId()) {
                                                    System.out.println("\nAuthor: " + company.getName());
                                                    break;
                                                }
                                            }
                                        }
                                        System.out.println("=====================================");
                                        System.out.println("Title: " + post.getTitle());
                                        System.out.println("=====================================");
                                        System.out.println("Date of creation: " + post.getCreationDate());
                                        System.out.println("=====================================");
                                        System.out.println("Content: " + post.getContent());
                                        System.out.println("=====================================");
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

                                                    isEmptyString(answer);

                                                    if (answer.equals("n") || answer.equals("N")) break;

                                                    if (answer.equals("y") || answer.equals("Y")) {
                                                        Like newLike = new Like(users.get(loggedUserIndex), today);
                                                        post.getLikes().add(newLike);
                                                        System.out.println("\n=====================================");
                                                        System.out.println("Done!");
                                                        break;
                                                    }

                                                    else {
                                                        System.out.println("\n=====================================");
                                                        System.out.println("Invalid option!");
                                                    }
                                                } while (true);
                                            }
                                        }

                                        //Comment
                                        do {
                                            System.out.println("\nWould you like to comment? (y/n)");
                                            answer = input.nextLine();

                                            isEmptyString(answer);

                                            if (answer.equals("n") || answer.equals("N")) break;

                                            if (answer.equals("y") || answer.equals("Y")) {
                                                do {
                                                    System.out.print("\nType the content of the comment: ");
                                                    content = input.nextLine();
                                                } while (isEmptyString(content));

                                                Comment newComment = new Comment(users.get(loggedUserIndex), today, content);
                                                post.getComments().add(newComment);
                                                System.out.println("\n=====================================");
                                                System.out.println("Done!");
                                                break;
                                            } else {
                                                System.out.println("\n=====================================");
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

                                                    isEmptyString(answer);

                                                    if (answer.equals("n") || answer.equals("N")) break;

                                                    if (answer.equals("y") || answer.equals("Y")) {
                                                        Share newShare = new Share(users.get(loggedUserIndex), today);
                                                        post.getShares().add(newShare);
                                                        System.out.println("\n=====================================");
                                                        System.out.println("Done!");
                                                        break;
                                                    } else {
                                                        System.out.println("\n=====================================");
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

                                                isEmptyString(answer);

                                                if (answer.equals("n") || answer.equals("N")) break;

                                                if (answer.equals("y") || answer.equals("Y")) {
                                                    System.out.println("\n=====================================");
                                                    System.out.println("Likes");
                                                    System.out.println("=====================================");


                                                    for (Iterator<Like> iterator = post.getLikes().iterator(); iterator.hasNext();) {
                                                        Like like = iterator.next();

                                                        if (like.getUser().getType().equals("Person")) {
                                                            for (Person person : people) {
                                                                if (like.getUser().getId() == person.getId()) {
                                                                    System.out.println("\nUser: " + person.getName());
                                                                }
                                                            }
                                                        } else {
                                                            for (Company company : companies) {
                                                                if (like.getUser().getId() == company.getId()) {
                                                                    System.out.println("\nUser: " + company.getName());
                                                                }
                                                            }
                                                        }
                                                        System.out.println("Date: " + like.getLikeDate());

                                                        if (like.getUser().getId() == loggedUserId)
                                                            do {
                                                                System.out.println("\nWould you like to remove your reaction? (y/n)");
                                                                answer = input.nextLine();

                                                                isEmptyString(answer);

                                                                if (answer.equals("n") || answer.equals("N")) break;

                                                                if (answer.equals("y") || answer.equals("Y")) {
                                                                    iterator.remove();

                                                                    System.out.println("\n=====================================");
                                                                    System.out.println("Done!");
                                                                    break;
                                                                } else {
                                                                    System.out.println("\n=====================================");
                                                                    System.out.println("Invalid option!");
                                                                }

                                                            } while (true);
                                                    }
                                                    break;

                                                } else {
                                                    System.out.println("\n=====================================");
                                                    System.out.println("Invalid option!");
                                                }
                                            } while (true);
                                        }

                                        //Comments
                                        if (post.getComments().size() > 0) {
                                            do {
                                                System.out.println("\nWould you like to view the comments of the post? (y/n)");
                                                answer = input.nextLine();

                                                isEmptyString(answer);

                                                if (answer.equals("n") || answer.equals("N")) break;

                                                if (answer.equals("y") || answer.equals("Y")) {
                                                    System.out.println("\n=====================================");
                                                    System.out.println("Comments");
                                                    System.out.println("=====================================");

                                                    for (Iterator<Comment> iterator = post.getComments().iterator(); iterator.hasNext();) {
                                                        Comment comment = iterator.next();

                                                        if (comment.getUser().getType().equals("Person")) {
                                                            for (Person person : people) {
                                                                if (comment.getUser().getId() == person.getId()) {
                                                                    System.out.println("\nUser: " + person.getName());
                                                                }
                                                            }
                                                        } else {
                                                            for (Company company : companies) {
                                                                if (comment.getUser().getId() == company.getId()) {
                                                                    System.out.println("\nUser: " + company.getName());
                                                                }
                                                            }
                                                        }
                                                        System.out.println("Date: "  + comment.getCommentDate());
                                                        System.out.println("Content: " + comment.getContent());

                                                        if (comment.getUser().getId() == loggedUserId) {
                                                            do {
                                                                System.out.println("\nWould you like to edit this comment? (y/n)");
                                                                answer = input.nextLine();

                                                                isEmptyString(answer);

                                                                if (answer.equals("n") || answer.equals("N")) break;

                                                                if (answer.equals("y") || answer.equals("Y")) {
                                                                    do {
                                                                        System.out.print("\nType the new content of the comment: ");
                                                                        content = input.nextLine();
                                                                    } while (isEmptyString(content));

                                                                    comment.setContent(content);

                                                                    System.out.println("\n=====================================");
                                                                    System.out.println("Done!\n");
                                                                    break;
                                                                } else {
                                                                    System.out.println("\n=====================================");
                                                                    System.out.println("Invalid option!");
                                                                }
                                                            } while (true);

                                                            do {
                                                                System.out.println("\nWould you like to remove this comment? (y/n)");
                                                                answer = input.nextLine();

                                                                isEmptyString(answer);

                                                                if (answer.equals("n") || answer.equals("N")) break;

                                                                if (answer.equals("y") || answer.equals("Y")) {
                                                                    iterator.remove();

                                                                    System.out.println("\n=====================================");
                                                                    System.out.println("Done!\n");
                                                                    break;
                                                                } else {
                                                                    System.out.println("\n=====================================");
                                                                    System.out.println("Invalid option!");
                                                                }
                                                            } while (true);
                                                        }
                                                    }
                                                    break;
                                                } else {
                                                    System.out.println("\n=====================================");
                                                    System.out.println("Invalid option!");
                                                }
                                            } while (true);
                                        }

                                        //Shares
                                        if (post.getShares().size() > 0) {
                                            do {
                                                System.out.println("\nWould you like to view the shares of the post? (y/n)");
                                                answer = input.nextLine();

                                                isEmptyString(answer);

                                                if (answer.equals("n") || answer.equals("N")) break;

                                                if (answer.equals("y") || answer.equals("Y")) {
                                                    System.out.println("\n=====================================");
                                                    System.out.println("Shares");
                                                    System.out.println("=====================================");


                                                    for (Iterator<Share> iterator = post.getShares().iterator(); iterator.hasNext();) {
                                                        Share share = iterator.next();

                                                        if (share.getUser().getType().equals("Person")) {
                                                            for (Person person : people) {
                                                                if (share.getUser().getId() == person.getId()) {
                                                                    System.out.println("\nUser: " + person.getName());
                                                                }
                                                            }
                                                        } else {
                                                            for (Company company : companies) {
                                                                if (share.getUser().getId() == company.getId()) {
                                                                    System.out.println("\nUser: " + company.getName());
                                                                }
                                                            }
                                                        }
                                                        System.out.println("Date: " + share.getShareDate());

                                                        if (share.getUser().getId() == loggedUserId)
                                                            do {
                                                                System.out.println("\nWould you like to remove this share? (y/n)");
                                                                answer = input.nextLine();

                                                                isEmptyString(answer);

                                                                if (answer.equals("n") || answer.equals("N")) break;

                                                                if (answer.equals("y") || answer.equals("Y")) {
                                                                    iterator.remove();

                                                                    System.out.println("\n=====================================");
                                                                    System.out.println("Done!");
                                                                    break;
                                                                } else {
                                                                    System.out.println("\n=====================================");
                                                                    System.out.println("Invalid option!");
                                                                }
                                                            } while (true);
                                                    }
                                                    break;
                                                } else {
                                                    System.out.println("\n=====================================");
                                                    System.out.println("Invalid option!");
                                                }
                                            } while (true);
                                        }

                                        if (posts.size() == countPost) {
                                            System.out.println("\n=====================================");
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
                                } while (isEmptyString(title));

                                do {
                                    System.out.print("\nType the content of the post: ");
                                    content = input.nextLine();
                                } while (isEmptyString(content));

                                createPost(posts, title, today, content, people, companies, users);
                            }

                            case 3 -> {
                                if (!menuAccessed) menuAccessed = true;

                                if (users.get(loggedUserIndex).getType().equals("Person")) {
                                    for (Person person : people) {
                                        if (person.getId() == loggedUserId) {
                                            System.out.println("\n=====================================");
                                            System.out.println("Profile");
                                            if (person.getGender() == null) {
                                                System.out.println("=====================================");
                                                System.out.println("Name: " + person.getName());
                                                System.out.println("=====================================");
                                                System.out.println("You didn't configure your profile yet!");
                                            } else {
                                                System.out.println("=====================================");
                                                System.out.println("Name: " + person.getName());
                                                System.out.println("Gender: " + person.getGender());
                                                System.out.println("Date of birth: " + person.getBirth());
                                            }
                                        }
                                    }
                                } else {
                                    for (Company company : companies) {
                                        if (company.getId() == loggedUserId) {
                                            System.out.println("\n=====================================");
                                            System.out.println("Profile");
                                            if (company.getClassification() == null) {
                                                System.out.println("=====================================");
                                                System.out.println("Name: " + company.getName());
                                                System.out.println("=====================================");
                                                System.out.println("You didn't configure the company profile yet!");
                                            } else {
                                                System.out.println("=====================================");
                                                System.out.println("Name: " + company.getName());
                                                System.out.println("Creation date: " + company.getCreationDate());
                                                System.out.println("Classification: " + company.getClassification());
                                            }
                                        }
                                    }
                                }

                                System.out.println("=====================================");
                                System.out.println("Your posts");
                                System.out.println("=====================================");

                                if (posts.size() < 1) System.out.println("\nThere isn't any posts created yet!\n");
                                else {
                                    for (Post post : posts) {
                                        if (post.getAuthor().getId() == loggedUserId) {
                                            System.out.println("\nTitle: " + post.getTitle());
                                            System.out.println("=====================================");
                                            System.out.println("Date of creation: " + post.getCreationDate());
                                            System.out.println("\n=====================================");
                                            System.out.println("Content: " + post.getContent());
                                            System.out.println("\n=====================================");
                                            System.out.println("Likes: " + post.getLikes().size());
                                            System.out.println("Comments: " + post.getComments().size());
                                            System.out.println("Shares: " + post.getShares().size());
                                            postedOnce = true;

                                            do {
                                                System.out.println("\nWould you like to edit this post? (y/n)");
                                                answer = input.nextLine();

                                                isEmptyString(answer);

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
                                                        } while (isEmptyString(title));

                                                        post.setTitle(title);

                                                        System.out.println("\n=====================================");
                                                        System.out.println("Post edited succesfully!");
                                                        break;
                                                    }

                                                    if (op == 2) {
                                                        do {
                                                            System.out.print("\nType the new content of the post: ");
                                                            content = input.nextLine();
                                                        } while (isEmptyString(content));

                                                        post.setContent(content);

                                                        System.out.println("\n=====================================");
                                                        System.out.println("Post edited succesfully!");
                                                        break;
                                                    }
                                                } else {
                                                    System.out.println("\n=====================================");
                                                    System.out.println("Invalid option!");
                                                }

                                            } while (true);

                                            do {
                                                System.out.println("\nWould you like to remove this post? (y/n)");
                                                answer = input.nextLine();

                                                isEmptyString(answer);

                                                if (answer.equals("n") || answer.equals("N")) break;

                                                if (answer.equals("y") || answer.equals("Y")) {
                                                    posts.remove(post);
                                                    System.out.println("\n=====================================");
                                                    System.out.println("Post removed successfully!\n");
                                                    break;
                                                } else {
                                                    System.out.println("\n=====================================");
                                                    System.out.println("Invalid option!");
                                                }
                                                } while (true);
                                            }

                                            if (posts.size() < 1) break;
                                        }
                                        if (!postedOnce) System.out.println("\nYou didn't create any posts yet!\n");
                                    }

                                    System.out.println("=====================================");
                                    System.out.println("Shared posts");
                                    System.out.println("=====================================");

                                    if (posts.size() < 1) System.out.println("\nThere isn't any posts created yet!\n");
                                    else {
                                        for (Post post : posts) {
                                            for (Share share : post.getShares()) {
                                                if (share.getUser().getId() == loggedUserId) {
                                                    if (post.getAuthor().getType().equals("Person")) {
                                                        for (Person person : people) {
                                                            if (person.getId() == post.getAuthor().getId()) {
                                                                System.out.println("\nAuthor: " + person.getName());
                                                                break;
                                                            }
                                                        }
                                                    }else {
                                                        for (Company company : companies) {
                                                            if (company.getId() == post.getAuthor().getId()) {
                                                                System.out.println("\nAuthor: " + company.getName());
                                                                break;
                                                            }
                                                        }
                                                    }
                                                System.out.println("Title: " + post.getTitle());
                                                System.out.println("=====================================");
                                                System.out.println("Date of share: " + share.getShareDate());
                                                System.out.println("=====================================");
                                                System.out.println("Content: " + post.getContent());
                                                sharedOnce = true;
                                            }
                                        }
                                    }
                                    if (!sharedOnce) System.out.println("\nYou didn't share any posts yet!\n");
                                }
                                System.out.println();
                            }

                            case 4 -> {
                                if (!menuAccessed) menuAccessed = true;

                                if (users.get(loggedUserIndex).getType().equals("Person")) {
                                    for (Person person : people) {
                                        if (person.getId() == loggedUserId) {
                                            do {
                                                System.out.println("\nWhich field would you like to edit?");
                                                System.out.println("1) Name");
                                                System.out.println("2) Gender");
                                                System.out.println("3) Date of birth");
                                                System.out.println("4) Email");
                                                System.out.println("5) Password");
                                                System.out.println("6) Cancel");
                                                System.out.print("Choose an option: ");
                                                op = input.nextInt();
                                                input.nextLine();

                                                if (op < 1 || op > 4) {
                                                    System.out.println("\n==============================");
                                                    System.out.println("Invalid option!");
                                                }

                                                if (op == 1) {
                                                    do {
                                                        System.out.print("\nType your new name: ");
                                                        name = input.nextLine();
                                                    } while (isEmptyString(name));

                                                    person.setName(name);

                                                    System.out.println("\n==============================");
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
                                                            System.out.println("\n==============================");
                                                            System.out.println("Invalid option!");
                                                    } while (true);

                                                    person.setGender(gender);

                                                    System.out.println("\n==============================");
                                                    System.out.println("Done!\n");
                                                    break;
                                                }

                                                if (op == 3) {
                                                    do {
                                                        System.out.print("\nEnter the year of your birth: ");
                                                        year = input.nextInt(); input.nextLine();
                                                    } while ((today.getYear() - year) < 13 || (today.getYear() - year) > 100);

                                                    do {
                                                        System.out.print("\nEnter the month of your birth: ");
                                                        month = input.nextInt(); input.nextLine();
                                                    } while (month < 1 || month > 12);

                                                    do {
                                                        System.out.print("\nEnter the day of your birth: ");
                                                        day = input.nextInt(); input.nextLine();

                                                        try {
                                                            birth = LocalDate.of(year, month, day);
                                                        } catch (DateTimeException error) {
                                                            System.out.println("\n==============================");
                                                            System.out.println("Invalid day for the month entered");
                                                        }
                                                    } while (birth == null);

                                                    person.setBirth(birth);

                                                    System.out.println("\n==============================");
                                                    System.out.println("Done!\n");
                                                    break;
                                                }

                                                if (op == 4) {
                                                    do {
                                                        System.out.print("\nEnter the your new email: ");
                                                        email = input.nextLine();
                                                    } while (isEmptyString(email) || isInvalidEmail(email));

                                                    users.get(loggedUserIndex).setEmail(email);
                                                    System.out.println("\n==============================");
                                                    System.out.println("Done!\n");
                                                    break;
                                                }

                                                if (op == 5) {
                                                    do {
                                                        System.out.print("\nEnter the your new password: ");
                                                        password = input.nextLine();
                                                    } while (isEmptyString(password) || isInvalidEmail(password));

                                                    users.get(loggedUserIndex).setPassword(password);
                                                    System.out.println("\n==============================");
                                                    System.out.println("Done!\n");
                                                    break;
                                                }

                                                if (op == 6) break;
                                            } while (true);
                                        }
                                    }
                                } else {
                                    for (Company company : companies) {
                                        if (company.getId() == loggedUserId) {
                                            do {
                                                System.out.println("\nWhich field would you like to edit?");
                                                System.out.println("1) Name");
                                                System.out.println("2) Creation date");
                                                System.out.println("3) Classification");
                                                System.out.println("4) Email");
                                                System.out.println("5) Password");
                                                System.out.println("6) Cancel");
                                                System.out.print("Choose an option: ");
                                                op = input.nextInt();
                                                input.nextLine();

                                                if (op < 1 || op > 4) {
                                                    System.out.println("\n=====================================");
                                                    System.out.println("Invalid option!");
                                                }

                                                if (op == 1) {
                                                    do {
                                                        System.out.print("\nType the new company name: ");
                                                        name = input.nextLine();
                                                    } while (isEmptyString(name));

                                                    company.setName(name);

                                                    System.out.println("\n=====================================");
                                                    System.out.println("Done!\n");
                                                    break;
                                                }

                                                if (op == 2) {
                                                    do {
                                                        System.out.print("\nEnter the company creation year: ");
                                                        year = input.nextInt(); input.nextLine();
                                                    } while ((today.getYear() - year) > 1317);

                                                    do {
                                                        System.out.print("\nEnter the company creation month: ");
                                                        month = input.nextInt(); input.nextLine();
                                                    } while (month < 1 || month > 12);

                                                    do {
                                                        System.out.print("\nEnter the company creation day: ");
                                                        day = input.nextInt(); input.nextLine();

                                                        try {
                                                            creationDate = LocalDate.of(year, month, day);
                                                        } catch (DateTimeException error) {
                                                            System.out.println("\n=====================================");
                                                            System.out.println("Invalid day for the month entered");
                                                        }
                                                    } while (creationDate == null);

                                                    company.setCreationDate(creationDate);

                                                    System.out.println("\n=====================================");
                                                    System.out.println("Done!\n");
                                                    break;
                                                }

                                                if (op == 3) {
                                                    do {
                                                        System.out.println("\nClassification");
                                                        System.out.println("1) Small");
                                                        System.out.println("2) Medium");
                                                        System.out.println("3) Big");
                                                        System.out.println("4) Multinational");
                                                        System.out.print("Choose an option: ");
                                                        op = input.nextInt(); input.nextLine();

                                                        if (op < 1 || op > 4) {
                                                            System.out.println("\n=====================================");
                                                            System.out.println("Invalid option!");
                                                        }

                                                        if (op == 1) {
                                                            classification = "Small";
                                                            break;
                                                        }

                                                        if (op == 2) {
                                                            classification = "Medium";
                                                            break;
                                                        }

                                                        if (op == 3) {
                                                            classification = "Big";
                                                            break;
                                                        }

                                                        if (op == 4) {
                                                            classification = "Multinational";
                                                            break;
                                                        }
                                                    } while (true);

                                                    company.setClassification(classification);

                                                    System.out.println("\n=====================================");
                                                    System.out.println("Done!\n");
                                                    break;
                                                }

                                                if (op == 4) {
                                                    do {
                                                        System.out.print("\nEnter the company new email: ");
                                                        email = input.nextLine();
                                                    } while (isEmptyString(email) || isInvalidEmail(email));

                                                    users.get(loggedUserIndex).setEmail(email);
                                                    System.out.println("\n==============================");
                                                    System.out.println("Done!\n");
                                                    break;
                                                }

                                                if (op == 5) {
                                                    do {
                                                        System.out.print("\nEnter the company new password: ");
                                                        password = input.nextLine();
                                                    } while (isEmptyString(password) || isInvalidEmail(password));

                                                    users.get(loggedUserIndex).setPassword(password);
                                                    System.out.println("\n==============================");
                                                    System.out.println("Done!\n");
                                                    break;
                                                }

                                                if (op == 6) break;
                                            } while (true);
                                        }
                                    }
                                }
                            }

                            case 5 -> {
                                if (!menuAccessed) menuAccessed = true;

                                boolean end = false;

                                do {
                                    System.out.println("\n=====================================");
                                    System.out.println("1) List all users");
                                    System.out.println("2) Search person by name");
                                    System.out.println("3) Search company by name");
                                    System.out.println("4) Add person by ID");
                                    System.out.println("5) Add company by ID");
                                    System.out.println("6) Cancel");
                                    System.out.print("Choose an option: ");
                                    op = input.nextInt();
                                    input.nextLine();

                                    if (op < 1 || op > 6) {
                                        System.out.println("\n=====================================");
                                        System.out.println("Invalid option!");
                                    }

                                    if (op == 1) {
                                        System.out.println("\n=====================================");
                                        System.out.println("People");
                                        System.out.println("=====================================");
                                        if (people.size() < 1) {
                                            System.out.println("\nThere isn't any people registered yet!");
                                        } else {
                                            for (Person person : people) {
                                                if (people.size() == 1 && person.getId() == loggedUserId) {
                                                    System.out.println("\n=====================================");
                                                    System.out.println("There's no person to add yet!");
                                                    break;
                                                } else {
                                                    System.out.println("\n=====================================");
                                                    System.out.println("ID: " + person.getId());
                                                    System.out.println("Name: " + person.getName());
                                                }
                                            }
                                        }

                                        System.out.println("\n=====================================");
                                        System.out.println("Company");
                                        System.out.println("=====================================");
                                        if (companies.size() < 1) {
                                            System.out.println("\nThere isn't any company registered yet!");
                                        } else {
                                            for (Company company : companies) {
                                                if (companies.size() == 1 && company.getId() == loggedUserId) {
                                                    System.out.println("\n=====================================");
                                                    System.out.println("There's no company to add yet!");
                                                    break;
                                                } else {
                                                    System.out.println("\n=====================================");
                                                    System.out.println("ID: " + company.getId());
                                                    System.out.println("Name: " + company.getName());
                                                }
                                            }
                                        }
                                        System.out.println("\n=====================================");
                                        System.out.println("There's no one left to show!");
                                    }

                                    if (op == 2) {
                                        do {
                                            System.out.print("\nType the name of the person: ");
                                            name = input.nextLine();
                                        } while (isEmptyString(name) || isInvalidName(name));

                                        for (Person person : people) {
                                            if (person.getName().equals(name) && person.getId() != loggedUserId) {
                                                System.out.println("\n=====================================");
                                                System.out.println("ID: " + person.getId());
                                                System.out.println("Name: " + person.getName());
                                            } else {
                                                System.out.println("\n=====================================");
                                                System.out.println("Person not found!");
                                            }
                                        }
                                    }

                                    if (op == 3) {
                                        do {
                                            System.out.print("\nType the name of the company: ");
                                            name = input.nextLine();
                                        } while (isEmptyString(name) || isInvalidName(name));

                                        for (Company company : companies) {
                                            if (company.getName().equals(name) && company.getId() != loggedUserId){
                                                System.out.println("\n=====================================");
                                                System.out.println("ID: " + company.getId());
                                                System.out.println("Name: " + company.getName());
                                            } else {
                                                System.out.println("\n=====================================");
                                                System.out.println("Company not found!");
                                            }
                                        }
                                    }

                                    if (op == 4) {
                                        System.out.print("\nType the person ID: ");
                                        int id = input.nextInt(); input.nextLine();

                                        for (Person toAddperson : people) {
                                            if (toAddperson.getId() == id && toAddperson.getId() != loggedUserId) {
                                                if (users.get(loggedUserIndex).getType().equals("Person")) {
                                                    for (Person person : people) {
                                                        if (person.getId() == loggedUserId) {
                                                            person.getFriendList().add(toAddperson);
                                                            System.out.println("\n=====================================");
                                                            System.out.println("Done!\n");
                                                            end = true;
                                                        } else {
                                                            System.out.println("\n=====================================");
                                                            System.out.println("Company not found!");

                                                        }
                                                        break;
                                                    }
                                                } else {
                                                    for (Company company : companies) {
                                                        if (company.getId() == loggedUserId) {
                                                            company.getFriendList().add(toAddperson);
                                                            System.out.println("\n=====================================");
                                                            System.out.println("Done!\n");
                                                            end = true;
                                                        } else {
                                                            System.out.println("\n=====================================");
                                                            System.out.println("Company not found!");
                                                        }
                                                        break;
                                                    }
                                                }
                                                break;
                                            }
                                        }
                                    }

                                    if (op == 5) {
                                        System.out.print("\nType the company ID: ");
                                        int id = input.nextInt(); input.nextLine();

                                        for (Company toAddCompany : companies) {
                                            if (toAddCompany.getId() == id && toAddCompany.getId() != loggedUserId) {
                                                if (users.get(loggedUserIndex).getType().equals("Person")) {
                                                    for (Person person : people) {
                                                        if (person.getId() == loggedUserId) {
                                                            person.getFriendList().add(toAddCompany);
                                                            System.out.println("\n=====================================");
                                                            System.out.println("Done!\n");
                                                            end = true;
                                                        } else {
                                                            System.out.println("\n=====================================");
                                                            System.out.println("Company not found!");

                                                        }
                                                        break;
                                                    }
                                                } else {
                                                    for (Company company : companies) {
                                                        if (company.getId() == loggedUserId) {
                                                            company.getFriendList().add(toAddCompany);
                                                            System.out.println("\n=====================================");
                                                            System.out.println("Done!\n");
                                                            end = true;
                                                        } else {
                                                            System.out.println("\n=====================================");
                                                            System.out.println("Company not found!");
                                                        }
                                                        break;
                                                    }
                                                }
                                                break;
                                            }
                                        }
                                    }

                                    if (op == 6) end = true;
                                } while (!end);
                            }

                            case 6 -> {
                                if (!menuAccessed) menuAccessed = true;

                                boolean hasAnyPerson = false;
                                boolean hasAnyCompany = false;

                                System.out.println("\n=====================================");
                                System.out.println("Friendlist");
                                System.out.println("=====================================");

                                if (users.get(loggedUserIndex).getType().equals("Person")) {
                                    for (Person person : people) {
                                        if (person.getFriendList().size() < 1) {
                                            System.out.println("\n=====================================");
                                            System.out.println("You didn't add any friend yet!\n");
                                        } else {
                                            System.out.println("People");
                                            System.out.println("=====================================");
                                            for (Person toAddPerson : people) {
                                               if (person.getFriendList().contains(toAddPerson)) {
                                                   System.out.println("\nID: " + toAddPerson.getId() + " Name: " + toAddPerson.getName());
                                                   hasAnyPerson = true;
                                               }
                                            }
                                            if (!hasAnyPerson) System.out.println("\nYou didn't add any person yet!");
                                            System.out.println();

                                            System.out.println("Companies");
                                            System.out.println("=====================================");
                                            for (Company company : companies) {
                                                if (person.getFriendList().contains(company)) {
                                                    System.out.println("\nID: " + company.getId() + " Name: " + company.getName());
                                                    hasAnyCompany = true;
                                                }
                                            }
                                            if (!hasAnyCompany) System.out.println("\nYou didn't add any company yet!");
                                            System.out.println();
                                        }
                                    }
                                } else {
                                    for (Company company : companies) {
                                        if (company.getFriendList().size() < 1) {
                                            System.out.println("\n=====================================");
                                            System.out.println("You didn't add any friend yet!\n");
                                        } else {
                                            System.out.println("People");
                                            System.out.println("=====================================");
                                            for (Person person : people) {
                                                if (company.getFriendList().contains(person)) {
                                                    System.out.println("\nID: " + person.getId() + " Name: " + person.getName());
                                                }
                                            }
                                            System.out.println();

                                            System.out.println("Companies");
                                            System.out.println("=====================================");
                                            for (Company company1 : companies) {
                                                if (company.getFriendList().contains(company1)) {
                                                    System.out.println("\nID: " + company1.getId() + " Name: " + company1.getName());
                                                }
                                            }
                                            System.out.println();
                                        }
                                    }
                                }
                            }

                            case 7 -> {
                                if (!menuAccessed) menuAccessed = true;

                                boolean removed = false;
                                int id;

                                if (users.get(loggedUserIndex).getFriendList().size() < 1) {
                                    System.out.println("\n=====================================");
                                    System.out.println("You didn't add any friend yet!\n");
                                } else {
                                    System.out.print("\nType the friend ID: ");
                                    id = input.nextInt();
                                    input.nextLine();

                                    if (users.get(loggedUserIndex).getType().equals("Person")) {
                                        for (Person person : people) {
                                            if (person.getId() == loggedUserId) {
                                                for (Iterator<User> iterator = person.getFriendList().iterator(); iterator.hasNext(); ) {
                                                    User friend = iterator.next();

                                                    if (friend.getId() == id) {
                                                        iterator.remove();
                                                        System.out.println("\n=====================================");
                                                        System.out.println("Done!\n");
                                                        removed = true;
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        for (Company company : companies) {
                                            if (company.getId() == loggedUserId) {
                                                for (Iterator<User> iterator = company.getFriendList().iterator(); iterator.hasNext(); ) {
                                                    User friend = iterator.next();

                                                    if (friend.getId() == id) {
                                                        iterator.remove();
                                                        System.out.println("\n=====================================");
                                                        System.out.println("Done!\n");
                                                        removed = true;
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    if (!removed) {
                                        System.out.println("\n=====================================");
                                        System.out.println("\nFriend not found!\n");
                                    }
                                }
                            }

                            case 8 -> {
                                if (!menuAccessed) menuAccessed = true;

                                do {
                                    System.out.println("\nAre you sure you want to delete your account? (y/n)");
                                    answer = input.nextLine();

                                    isEmptyString(answer);

                                    if (answer.equals("n") || answer.equals("N")) break;

                                    if (answer.equals("y") || answer.equals("Y")) {
                                        deleteAccount(users, people, companies, posts);
                                        break;
                                    }
                                    else {
                                        System.out.println("\n=====================================");
                                        System.out.println("Invalid option!");
                                    }

                                } while (true);

                                if (answer.equals("y") || answer.equals("Y")) op = 9;
                            }

                            case 9 -> {
                                loggedUserIndex = users.size() + 1;
                                loggedUserId = userId + 1;
                                postedOnce = false;
                                sharedOnce = false;
                                menuAccessed = false;
                                System.out.println("\n=====================================");
                                System.out.println("Logging out!");
                            }

                            default -> {
                                System.out.println("\n=====================================");
                                System.out.println("Invalid option!");
                            }
                        }
                    } while (op != 9);
                }
            }

            if (op == 2) {
                do {
                    System.out.println("\n1) I'm a person");
                    System.out.println("2) I'm a company");
                    System.out.print("Choose an option: ");
                    op = input.nextInt(); input.nextLine();

                    if (op < 1 || op > 2) {
                        System.out.println("\n=====================================");
                        System.out.println("Invalid option!");
                    } else {
                        break;
                    }
                } while(true);

                do {
                    System.out.print("\nEmail: ");
                    email = input.nextLine();
                } while (isEmptyString(email) || isInvalidEmail(email));

                do {
                    System.out.print("\nPassword: ");
                    password = input.nextLine();
                } while (isEmptyString(password) || isInvalidPassword(password));

                do {
                    System.out.print("\nName: ");
                    name = input.nextLine();
                } while (isEmptyString(name) || isInvalidName(name));

                if (register(op, email, password, name, input, people, companies, users, today)) {
                    System.out.println("\n=====================================");
                    System.out.println("Registered successfully!");
                } else {
                    System.out.println("\n=====================================");
                    System.out.println("Email already registered!");
                }
            }

            if (op == 3) {
                System.out.println("\n=====================================");
                System.out.println("Finished by user!");
            }
        } while (op != 3);
        input.close();
    }

    //Person or Company
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

    static boolean register(int op, String email,
                            String password,
                            String name,
                            Scanner input,
                            ArrayList<Person> people,
                            ArrayList<Company> companies,
                            ArrayList<User> users,
                            LocalDate today) {
        String answer;

        if (op == 1) {
            do {
                System.out.println("\nWould you like to include your profile info now? (y/n)");
                answer = input.nextLine();

                if (answer.equals("n") || answer.equals("N")) {
                    if (people.size() < 1) {
                        Person person = new Person(userId, email, password, name);
                        people.add(person);
                        users.add(person);
                    } else {
                        for (Person person : people) {
                            if (person.getEmail().equals(email)) return false;
                        }
                        Person person = new Person(userId, email, password, name);
                        people.add(person);
                        users.add(person);
                    }
                    break;
                }

                if (answer.equals("y") || answer.equals("Y")) {
                    String gender;
                    LocalDate birth = null;
                    int year, month, day;

                    do {
                        System.out.println("\nGender");
                        System.out.println("1) Male");
                        System.out.println("2) Female");
                        System.out.println("3) Others");
                        System.out.print("Choose an option: ");
                        op = input.nextInt(); input.nextLine();

                        if (op < 1 || op > 3) {
                            System.out.println("\n=====================================");
                            System.out.println("Invalid option!");
                        }

                        if (op == 1) {
                            gender = "Male";
                            break;
                        }

                        if (op == 2) {
                            gender = "Female";
                            break;
                        }

                        if (op == 3) {
                            gender = "Others";
                            break;
                        }
                    } while (true);

                    do {
                        System.out.print("\nEnter the year of your birth: ");
                        year = input.nextInt(); input.nextLine();
                    } while ((today.getYear() - year) < 13 || (today.getYear() - year) > 100);

                    do {
                        System.out.print("\nEnter the month of your birth: ");
                        month = input.nextInt(); input.nextLine();
                    } while (month < 1 || month > 12);

                    do {
                        System.out.print("\nEnter the day of your birth: ");
                        day = input.nextInt(); input.nextLine();

                        try {
                            birth = LocalDate.of(year, month, day);
                        } catch (DateTimeException error) {
                            System.out.println("\n=====================================");
                            System.out.println("Invalid day for the month entered");
                        }
                    } while (birth == null);

                    if (people.size() < 1) {
                        Person person = new Person(userId, email, password, name, gender, birth);
                        people.add(person);
                        users.add(person);
                    } else {
                        for (Person person : people) {
                            if (person.getEmail().equals(email)) return false;
                        }
                        Person person = new Person(userId, email, password, name, gender, birth);
                        people.add(person);
                        users.add(person);
                    }
                    break;
                } else {
                    System.out.println("\n=====================================");
                    System.out.println("Invalid option!");
                }

            } while (true);
        }

        if (op == 2) {
            do {
                System.out.println("\nWould you like to include the company profile info now? (y/n)");
                answer = input.nextLine();

                if (answer.equals("n") || answer.equals("N")) {
                    if (companies.size() < 1) {
                        Company company = new Company(userId, email, password, name);
                        companies.add(company);
                        users.add(company);
                    } else {
                        for (Company company : companies) {
                            if (company.getEmail().equals(email)) return false;
                        }
                        Company company = new Company(userId, email, password, name);
                        companies.add(company);
                        users.add(company);
                    }
                    break;
                }

                if (answer.equals("y") || answer.equals("Y")) {
                    String classification;
                    LocalDate creationDate = null;
                    int year, month, day;

                    do {
                        System.out.print("\nEnter the company creation year: ");
                        year = input.nextInt(); input.nextLine();
                    } while ((today.getYear() - year) > 1317);

                    do {
                        System.out.print("\nEnter the company creation month: ");
                        month = input.nextInt(); input.nextLine();
                    } while (month < 1 || month > 12);

                    do {
                        System.out.print("\nEnter the company creation day: ");
                        day = input.nextInt(); input.nextLine();

                        try {
                            creationDate = LocalDate.of(year, month, day);
                        } catch (DateTimeException error) {
                            System.out.println("\n=====================================");
                            System.out.println("Invalid day for the month entered");
                        }
                    } while (creationDate == null);

                    do {
                        System.out.println("\nClassification");
                        System.out.println("1) Small");
                        System.out.println("2) Medium");
                        System.out.println("3) Big");
                        System.out.println("4) Multinational");
                        System.out.print("Choose an option: ");
                        op = input.nextInt(); input.nextLine();

                        if (op < 1 || op > 4) {
                            System.out.println("\n=====================================");
                            System.out.println("Invalid option!");
                        }

                        if (op == 1) {
                            classification = "Small";
                            break;
                        }

                        if (op == 2) {
                            classification = "Medium";
                            break;
                        }

                        if (op == 3) {
                            classification = "Big";
                            break;
                        }

                        if (op == 4) {
                            classification = "Multinational";
                            break;
                        }
                    } while (true);

                    if (companies.size() < 1) {
                        Company company = new Company(userId, email, password, name, creationDate, classification);
                        companies.add(company);
                        users.add(company);
                    } else {
                        for (Company company : companies) {
                            if (company.getEmail().equals(email)) return false;
                        }
                        Company company = new Company(userId, email, password, name, creationDate, classification);
                        companies.add(company);
                        users.add(company);
                    }
                    break;
                } else {
                    System.out.println("\n=====================================");
                    System.out.println("Invalid option!");
                }

            } while (true);
        }
        userId++;
        return true;
    }

    static void deleteAccount(ArrayList<User> users, ArrayList<Person> people, ArrayList<Company> companies, ArrayList<Post> posts) {
        posts.removeIf(post -> post.getAuthor().getId() == loggedUserId);

        for (Post post : posts) {
            post.getLikes().removeIf(like -> like.getUser().getId() == loggedUserId);
            post.getComments().removeIf(comment -> comment.getUser().getId() == loggedUserId);
            post.getShares().removeIf(share -> share.getUser().getId() == loggedUserId);
        }

        for (Person person : people) {
            person.getFriendList().removeIf(user -> user.getId() == loggedUserId);
        }

        for (Company company : companies) {
            company.getFriendList().removeIf(user -> user.getId() == loggedUserId);
        }

        if (users.get(loggedUserIndex).getType().equals("Person")) {
            people.removeIf(person -> person.getId() == loggedUserId);
        } else {
            companies.removeIf(company -> company.getId() == loggedUserId);
        }

        users.removeIf(user -> user.getId() == loggedUserId);

        System.out.println("\n=====================================");
        System.out.println("Done!");
    }

    //Post
    static void createPost(ArrayList<Post> posts, String title, LocalDate today, String content, ArrayList<Person> people, ArrayList<Company> companies, ArrayList<User> users) {
        Post post;
        if (users.get(loggedUserIndex).getType().equals("Person")) {
            for (Person person : people) {
                if (person.getId() == loggedUserId) {
                    post = new Post(person, title, today, content);
                    posts.add(post);
                    break;
                }
            }
        } else {
            for (Company company : companies) {
                if (company.getId() == loggedUserId) {
                    post = new Post(company, title, today, content);
                    posts.add(post);
                    break;
                }
            }
        }
        System.out.println("\n==============================");
        System.out.println("Posted created successfully\n");
    }

    //Validation
    static boolean isEmptyString(String string) {
        if (string.isEmpty() || string.isBlank()) {
            System.out.println("\n=====================================");
            System.out.println("Empty field!");
            return true;
        }
        return false;
    }

    static boolean isInvalidEmail(String email) {
        String[] emailParameters = new String[2];
        emailParameters[0] = "@";
        emailParameters[1] = ".com";

        for (String emailParameter : emailParameters) {
            if (!email.contains(emailParameter)) {
                System.out.println("\n=====================================");
                System.out.println("Invalid email!");
                return true;
            }
        }
        return false;
    }

    static boolean isInvalidPassword(String password) {
        if (password.length() < 5 || password.length() > 15) {
            System.out.println("\n=====================================");
            System.out.println("Password must be between 5 and 15 characters long!");
            return true;
        }
        return false;
    }

    static boolean isInvalidName(String name) {
        String[] numbers = new String[10];

        for (int i = 0; i < numbers.length; i ++) {
            numbers[i] = Integer.toString(i);
            if (name.contains(numbers[i])) {
                System.out.println("\n=====================================");
                System.out.println("Invalid name!");
                return true;
            }
        }
        return false;
    }
}