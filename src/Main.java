import java.security.SecureRandom;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        PasswordGenerator aiGen = new PasswordGenerator(2);

        List<String> trainingData = Arrays.asList(
                "Secure", "algorithm", "pencil", "Arron",
                "apple", "keyboard", "authorization", "network",
                "notebook", "juice", "quantum", "cupchampions", "database"
        );

        System.out.println("Training AI Model...");
        aiGen.train(trainingData);

        System.out.println("\nGenerating 5 AI-Powered Passwords:");
        for (int i = 0; i < 5; i++) {
            System.out.println(aiGen.generatePassword(10));
        }
    }
}
