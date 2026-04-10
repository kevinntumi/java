import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Haskell {
    private static Map<HS, String> regex = new HashMap<>();

    public boolean ehIdentificadorClasse(String str) {
        if (str == null) throw new NullPointerException();
        if (str.isEmpty()) return false;

        return false;
    }

    private String getRegex (HS hs) {
        if (regex.containsKey(hs)) return regex.get(hs);
        return null;
    }

    public static void main(String[] args) {
        String str = "A Ж Δ Ω Ä Art";
        Matcher matcher = Pattern.compile("\\p{Lu}").matcher(str);

        while (matcher.find()) {
            System.out.println(matcher.group());
        }
    }

    public enum HS {
        ascLarge,
        uniLarge
    }
}