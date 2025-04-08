import java.io.Console;

public class Main {
    public static void main(String[] args) {
        Console console = System.console();

        if (console == null) {
            System.out.println("No console available");
            return;
        }

        char[] passwordArray = console.readPassword("Enter your password: ");
        String password = new String(passwordArray);

        System.out.println("Password entered (not shown while typing): " + password);

    }
}