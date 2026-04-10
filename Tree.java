import java.util.regex.Matcher;

public class Tree extends BinaryTree<String> {
    @Override
    public void insert(String item) {
        if (item == null) throw new NullPointerException();
        if (item.isBlank() | !(Util.isOperation(item) || Util.isOperationMember(item) || Util.isArithmeticOperator(item) || Util.isExpression(item))) return;
        super.insert(item);
    }

    @Override
    Node<String> insert(String item, Node<String> node) {
        if (node == null) {
            if (Util.isOperation(item)) {
                Matcher operationPattern = Util.OPERATION_PATTERN.matcher(item);
                Node<String> n = new Node<>();

                while (operationPattern.find()) {
                    if (n.getLeft() == null){
                        n.setLeft(insert(operationPattern.group(), n));
                    } else {
                        n.setRight(insert(operationPattern.group(), n));
                    }
                }

                if ((n.getValue() == null || n.getValue().isBlank()) || n.getLeft() == null ||n.getRight() == null){
                    return n;
                }
            }

            return null;
        }

        if (Util.isOperationMember(item)) {
            Matcher operationMemberPattern = Util.OPERATION_MEMBER_PATTERN.matcher(item);

            if (operationMemberPattern.find()) {
                return new Node<>(operationMemberPattern.group());
            }
        }

        //int compare = item.compareTo(node.g);

        //if (compare < 0)
          //  node.left = insert(item, node.left);
        //else
           // node.right = insert(item, node.right);

        return node;
    }
}