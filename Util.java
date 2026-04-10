import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
    private static final Character[] OPERATORS = {
        '+', '-', '*', '/'
    };

    private static final String[] DEFAULT = new String[0];

    public static final String
        WHITESPACE_REGEX = "\\s*",
        OPERATOR_REGEX = "[-+*/]",
        LETTER_REGEX = "[a-zA-Z]",
        DIGIT_REGEX = "[0-9]",
        OPERATION_MEMBER_REGEX = WHITESPACE_REGEX + "(" + LETTER_REGEX + "+" + DIGIT_REGEX + "*" + "|" + DIGIT_REGEX + "+" + ")" + WHITESPACE_REGEX,
        OPERAND_FIRST_OPERATOR_LATER = OPERATION_MEMBER_REGEX + OPERATOR_REGEX + WHITESPACE_REGEX,
        ANYWHERE_OPERATION_MEMBER_AND_OPERATOR = "(" + OPERAND_FIRST_OPERATOR_LATER +"|" + WHITESPACE_REGEX + OPERATOR_REGEX + OPERATION_MEMBER_REGEX + ")",
        OPERATION_REGEX = OPERATION_MEMBER_REGEX + OPERATOR_REGEX + OPERATION_MEMBER_REGEX,
        EXPRESSION_REGEX = OPERATION_MEMBER_REGEX + "(" + OPERATOR_REGEX + OPERATION_MEMBER_REGEX + ")+";
    ;

    public static final Pattern
            OPERATOR_PATTERN = Pattern.compile(OPERATOR_REGEX),
            OPERATION_MEMBER_PATTERN = Pattern.compile(OPERATION_MEMBER_REGEX),
            EXPRESSION_PATERN = Pattern.compile(EXPRESSION_REGEX),
            OPERATION_PATTERN = Pattern.compile(OPERATION_REGEX),
            OPERAND_FIRST_OPERATOR_LATER_PATTERN = Pattern.compile(OPERAND_FIRST_OPERATOR_LATER),
            ANYWHERE_OPERATION_MEMBER_AND_OPERATOR_PATTERN = Pattern.compile(ANYWHERE_OPERATION_MEMBER_AND_OPERATOR)
    ;

    public static boolean isOperationMember(String expression) {
        if (expression == null) throw new NullPointerException();
        if (expression.isEmpty()) return false;
        return OPERATION_MEMBER_PATTERN.matcher(expression).matches();
    }

    public static boolean isArithmeticOperator(String expression) {
        if (expression == null) throw new NullPointerException();
        if (expression.isEmpty()) return false;
        return OPERATOR_PATTERN.matcher(expression).matches();
    }

    public static boolean isLetterOrDigit(Character c) {
        if (c == null) throw new NullPointerException();
        return Character.isLetterOrDigit(c);
    }

    public static boolean isOperation(String expression) {
        if (expression == null) throw new NullPointerException();
        if (expression.isEmpty()) return false;
        return OPERATION_PATTERN.matcher(expression).matches();
    }

    public static boolean isExpression (String expression) {
        if (expression == null) throw new NullPointerException();
        if (expression.isEmpty()) return false;
        return EXPRESSION_PATERN.matcher(expression).matches();
    }

    public static void split(String s) {
        if (s == null) throw new NullPointerException();
        if (s.isEmpty()) return;

        Matcher expMatcher = EXPRESSION_PATERN.matcher(s);

        if (!expMatcher.matches()) {
            System.out.println("Não é uma expressão aritmética");
            expMatcher.reset();
            return;
        }

        Matcher operationPattern = OPERATOR_PATTERN.matcher(s);

        while (operationPattern.find()) {
            System.out.println(operationPattern.group());
        }

        operationPattern.reset();
    }

    public static void checkExpression(String s) {
        Matcher operationMatcher = OPERATION_PATTERN.matcher(s);

        while (operationMatcher.find()) {
            System.out.println(operationMatcher.group());
        }
    }

    public static <T> void inOrder(Node<T> node) {
        if (node != null) {
            inOrder(node.getLeft());
            System.out.print(node.getValue() + " ");
            inOrder(node.getRight());
        }
    }
}