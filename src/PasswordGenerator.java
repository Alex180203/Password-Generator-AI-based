import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PasswordGenerator {

    private final Map<String, List<Character>> markovModel = new HashMap<>();
    private final SecureRandom random = new SecureRandom();
    private final int stateSize;

    public PasswordGenerator(int stateSize) {
        this.stateSize = stateSize;
    }

    public void train(List<String> dataset) {
        for (String word : dataset) {
            String paddedWord = " ".repeat(stateSize) + word + " ";
            for (int i = 0; i < paddedWord.length() - stateSize; i++) {
                String state = paddedWord.substring(i, i + stateSize);
                char nextChar = paddedWord.charAt(i + stateSize);

                markovModel.putIfAbsent(state, new ArrayList<>());
                markovModel.get(state).add(nextChar);
            }
        }
    }

    public String generatePassword(int targetLength) {
        if (markovModel.isEmpty()) {
            throw new IllegalStateException("AI Model is not trained yet!");
        }

        StringBuilder password = new StringBuilder();
        String currentState = " ".repeat(stateSize);

        int baseLength = targetLength - 2;

        while (password.length() < baseLength) {
            List<Character> possibleNextChars = markovModel.get(currentState);

            if (possibleNextChars == null || possibleNextChars.isEmpty()) {
                currentState = " ".repeat(stateSize);
                continue;
            }

            char nextChar = possibleNextChars.get(random.nextInt(possibleNextChars.size()));

            if (nextChar == ' ') {
                currentState = " ".repeat(stateSize);
            } else {
                password.append(nextChar);
                currentState = currentState.substring(1) + nextChar;
            }
        }

        return enforceSecurityRules(password.toString());
    }

    private String enforceSecurityRules(String basePassword) {
        String symbols = "!@#$%^&*()-_+=";
        char randomSymbol = symbols.charAt(random.nextInt(symbols.length()));
        int randomNumber = random.nextInt(10); // 0-9

        if (basePassword.length() > 0) {
            basePassword = basePassword.substring(0, 1).toUpperCase() + basePassword.substring(1);
        }

        return basePassword + randomNumber + randomSymbol;
    }
}