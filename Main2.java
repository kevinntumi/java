import java.util.regex.Matcher;

public class Main2 {
    public static void main(String[] args) {
        String str = "a + b * c";
        Node<String> n = null;
        Matcher operationMatcher = Util.OPERATION_PATTERN.matcher(str);

        while (operationMatcher.find()) {
            Matcher operandFirstOperatorLaterMatcher = Util.OPERAND_FIRST_OPERATOR_LATER_PATTERN.matcher(operationMatcher.group());

            while (operandFirstOperatorLaterMatcher.find()) {
                Matcher operatorMatcher = Util.OPERATOR_PATTERN.matcher(operandFirstOperatorLaterMatcher.group());

                if (operatorMatcher.find()) {
                    n = insert(operatorMatcher.group(), null);

                    if (n != null) {
                        Matcher operandMatcher = Util.OPERATION_MEMBER_PATTERN.matcher(operandFirstOperatorLaterMatcher.group());

                        if (operandMatcher.find()) {
                            n.setLeft(insert(operandMatcher.group(), n));
                        }
                    }
                }
            }

            Matcher
        }

        Util.inOrder(n);
    }

    private static Node<String> insert(String item, Node<String> n) {
        if (Util.isOperationMember(item) || Util.isArithmeticOperator(item)) {
            return new Node<>(item);
        }

        return null;
    }
}