import java.util.Scanner;
public class main {
    public static void main(String[] args) {Scanner input = new Scanner(System.in);
        Library mylibrary = new Library();
        System.out.println("Enter user name  to move to Admin main  if you are admin");
        String username = input.nextLine();
        System.out.println("Enter Adminpassword to move to Admin main  if you are admin");
        int Adminpassword = input.nextInt();
        if ((username.toLowerCase().equals("admin")) && Adminpassword == 6789) {
            System.out.println("---------------------------------------------------------------------   Welcome To Admin Main Of  LibrarySystem   ----------------------------------------------------");
            int choice;
            do {System.out.println("Press 1 to Add a newbook ");
                System.out.println("Press 2 to update a book ");
                System.out.println("Press 3 to Remove book ");
                System.out.println("Press 4 to display available books in library system ");
                System.out.println("Press 5 to display total numbers  of books in library system ");
                System.out.println("----------------------------------------------------");
                System.out.println("Enter your choice (6 to exit): ");
                choice = input.nextInt();
                switch (choice) {
                    case 1:
                        System.out.println("Enter numOfPages ");
                        int numOfPages = input.nextInt();
                        input.nextLine();
                        System.out.println("EnterThe Title Of the Book  ");
                        String title = input.nextLine();
                        System.out.println("Enter The Price Of The Book ");
                        int price = input.nextInt();
                        System.out.println("Enter The publicationYear OfThe Book ");
                        int publicationYear = input.nextInt();
                        System.out.println("Enter The numOfCopies Of The Book ");
                        int numOfCopies = input.nextInt();
                        input.nextLine();
                        System.out.println("Enter The Name Of Author Of The Book ");
                        String name = input.nextLine();
                        System.out.println("Enter The Surname Author Of The Book ");
                        String surname = input.nextLine();
                        System.out.println("Enter The  Email OF Author Of The Book");
                        String email = input.nextLine();
                        System.out.println("Enter The Phone OF Author Of The Book");
                        String phone = input.nextLine();
                        Author author = new Author(name, surname, email, phone);
                        Book newBook = new Book(title, numOfPages, numOfCopies, price, publicationYear, author);
                        mylibrary.addbook(newBook);
                        break;
                    case 2:
                        System.out.println("Enter Bookid Of The Book You Want update on The System");
                        int bookId = input.nextInt();
                        mylibrary.updatebooks(bookId);
                        break;
                    case 3:
                        System.out.println("Enter Bookid Of The Book You Want Remove From The System");
                        int bookid = input.nextInt();
                        mylibrary.removebook(bookid);
                        break;
                    case 4:
                        mylibrary.displayAvailableBooks();
                        break;
                    case 5:
                        mylibrary.displayTotalBooks();
                        break;
                    case 6:
                        System.out.println("Good bye ! ");
                }
            } while (choice != 6);
        }
        else
            System.out.println(" Sorry You Can't Access Admin main"); 
}
               }
