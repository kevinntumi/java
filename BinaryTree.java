public abstract class BinaryTree<T> {
    private Node<T> root;

    public BinaryTree() {
        root = null;
    }

    public void insert(T item) {
        root = insert(item, root);
    }

    abstract Node<T> insert(T item, Node<T> node);

    public void inOrder() {
        Util.inOrder(root);
        System.out.println();
    }
}
