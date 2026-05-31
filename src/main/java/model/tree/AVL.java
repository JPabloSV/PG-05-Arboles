package model.tree;

public class AVL<T extends Comparable<T>> extends BST<T> {

    @Override
    public void add(T element) {
        this.root = add(root, element, "root");
    }

    public BTreeNode<T> add(BTreeNode<T> node, T element, String path) {
        if (node == null) return new BTreeNode<>(element, path);
        if (compareElements(element, node.data) < 0)
            node.left = add(node.left, element, path + "/left");
        else if (compareElements(element, node.data) > 0)
            node.right = add(node.right, element, path + "/right");
        else return node;

        int balance = getBalanceFactor(node);

        // Caso 1 - II: rotación simple derecha
        if (balance > 1 && compareElements(element, node.left.data) < 0)
            return rightRotate(node);
        // Caso 2 - DD: rotación simple izquierda
        if (balance < -1 && compareElements(element, node.right.data) > 0)
            return leftRotate(node);
        // Caso 3 - ID: rotación doble izquierda-derecha
        if (balance > 1 && compareElements(element, node.left.data) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        // Caso 4 - DI: rotación doble derecha-izquierda
        if (balance < -1 && compareElements(element, node.right.data) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        return node;
    }

    @Override
    public void remove(T element) throws TreeException {
        if (isEmpty()) throw new TreeException("AVL Tree is empty");
        root = remove(root, element);
    }

    @Override
    public BTreeNode<T> remove(BTreeNode<T> node, T element) {
        if (node == null) return null;

        if (compareElements(element, node.data) < 0)
            node.left = remove(node.left, element);
        else if (compareElements(element, node.data) > 0)
            node.right = remove(node.right, element);
        else {
            if (node.left == null && node.right == null) return null;
            else if (node.left == null) return node.right;
            else if (node.right == null) return node.left;
            else {
                T minValue = min(node.right);
                node.data = minValue;
                node.right = remove(node.right, minValue);
            }
        }

        int balance = getBalanceFactor(node);

        // Caso 1
        if (balance > 1 && getBalanceFactor(node.left) >= 0)
            return rightRotate(node);
        // Caso 2
        if (balance < -1 && getBalanceFactor(node.right) <= 0)
            return leftRotate(node);
        // Caso 3
        if (balance > 1 && getBalanceFactor(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        // Caso 4
        if (balance < -1 && getBalanceFactor(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        return node;
    }

    public boolean isBalanced() {
        return isBalanced(root);
    }

    private boolean isBalanced(BTreeNode<T> node) {
        if (node == null) return true;
        int balance = getBalanceFactor(node);
        if (balance > 1 || balance < -1) return false;
        return isBalanced(node.left) && isBalanced(node.right);
    }

    private int height(BTreeNode<T> node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    public int getBalanceFactor(BTreeNode<T> node) {
        if (node == null) return 0;
        return height(node.left) - height(node.right);
    }

    private BTreeNode<T> leftRotate(BTreeNode<T> x) {
        BTreeNode<T> y = x.right;
        BTreeNode<T> T2 = y.left;
        y.left = x;
        x.right = T2;
        return y;
    }

    private BTreeNode<T> rightRotate(BTreeNode<T> y) {
        BTreeNode<T> x = y.left;
        BTreeNode<T> T2 = x.right;
        x.right = y;
        y.left = T2;
        return x;
    }

    @Override
    public String toString() {
        if (isEmpty()) return "AVL Tree is empty";
        String result = "AVL Tree\n";
        try {
            result += "PreOrder  : " + preOrder()   + "\n";
            result += "InOrder   : " + inOrder()    + "\n";
            result += "PostOrder : " + postOrder()  + "\n";
            result += "Altura    : " + height()     + "\n";
            result += "Balanceado: " + isBalanced() + "\n";
        } catch (TreeException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}