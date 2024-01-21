package pl.cezary.webbanking.security;
import java.security.SecureRandom;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class OneTimeCodeManager {
    private static final ConcurrentHashMap<String, String> codeStorage = new ConcurrentHashMap<>();
    private static final long CODE_EXPIRATION_TIME = TimeUnit.MINUTES.toMillis(5); // 5 minutes
    private static final ConcurrentHashMap<String, Long> codeGenerationTime = new ConcurrentHashMap<>();
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 6;
    private static final SecureRandom random = new SecureRandom();

    public static String generateOneTimeCode() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }
        return sb.toString();
    }
    public static void saveCodeForUser(Long userId, Long accountId, Long cardId, String code) {
        String key = generateKey(userId, accountId, cardId);
        codeStorage.put(key, code);
        codeGenerationTime.put(key, System.currentTimeMillis());
    }

    public static boolean isCodeValid(Long userId, Long accountId, Long cardId, String code) {
        String key = generateKey(userId, accountId, cardId);
        Long generatedTime = codeGenerationTime.get(key);

        if (generatedTime == null || System.currentTimeMillis() - generatedTime > CODE_EXPIRATION_TIME) {
            // Code is expired
            codeStorage.remove(key); // Cleanup
            codeGenerationTime.remove(key);
            return false;
        }

        String correctCode = codeStorage.get(key);
        if (correctCode != null && correctCode.equals(code)) {
            // Code is correct and not expired
            codeStorage.remove(key); // Cleanup after successful validation
            codeGenerationTime.remove(key);
            return true;
        }
        return false;
    }

    private static String generateKey(Long userId, Long accountId, Long cardId) {
        return userId + ":" + accountId + ":" + cardId;
    }
}
