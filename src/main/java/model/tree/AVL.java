package model.tree;

public class AVL<T extends Comparable<T>> extends BST<T> {

    @Override
    public void add(T element) {
        this.root = add(root, element, "root");
    }

    public BTreeNode<T> add(BTreeNode<T> node, T element, String path) {
        if (node == null) {
            node = new BTreeNode<>(element, path);
        } else if (compareElements(element, node.data) < 0)
            node.left = add(node.left, element, "Added as: " + path + "/left");
        else if (compareElements(element, node.data) > 0)
            node.right = add(node.right, element, "Added as: " + path + "/rigth");

        //Obtenemos el factor de balanceo de este ancestro
        int balance = getBalanceFactor(node);
        //Una vez optenido el factor de balanceo revisamos los 4 casos posibles
        if (balance > 1 && compareElements(element, node.left.data) < 0) {
            node.path = path + ", Simple Rigth Rotate";
            return rigthRotate(node);
        }
        return node;
    }

    private int height(BTreeNode<T> node) {
        if (node == null) {
            return 0;
        }
        //calculamos la altura recursivamente.
        return 1 + Math.max(height(node.left), height(node.right));
    }

    //Calcula el factor de balance: altura_izquierda - altura_derecha
    private int getBalanceFactor(BTreeNode<T> node) {
        if (node == null) {
            return 0;
        }
        return height(node.left) - height(node.right);
    }

    //Rotación simple a la izquierda
    private BTreeNode<T> leftRotate(BTreeNode<T> x) {
        BTreeNode<T> y = x.right;
        BTreeNode<T> T2 = y.left;

        y.left = x;
        x.right = T2;

        return y;
    }

    private BTreeNode<T> rigthRotate(BTreeNode<T> y) {
        BTreeNode<T> x = y.left;
        BTreeNode<T> T2 = x.right;

        x.right = y;
        y.left = T2;

        return x;
    }

    @Override
    public void remove(T element) throws TreeException {
        super.remove(element);
    }
}
