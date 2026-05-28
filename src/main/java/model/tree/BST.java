package model.tree;

public class BST<T extends Comparable<T>> extends BTree<T> {

    @Override
    public boolean contains(T element) throws TreeException {
        if (isEmpty()) throw new TreeException("Binary Search Tree is empty");
        return binarySearch(this.root, element);
    }
    private boolean binarySearch(BTreeNode<T> node, T element) {
        if (node == null) return false;
        if (equals(node.data, element)) return true;
        else if (compareElements(element,node.data)<0)
            return binarySearch(node.left, element);
        else return binarySearch(node.right, element);
    }

    @Override
    public void add(T element) {
        this.root = add(root, element);
    }

    public BTreeNode<T> add(BTreeNode<T> node, T element) {
        if (node == null) {
            node = new BTreeNode<>(element);
        } else if (compareElements(element, node.data) < 0)
            node.left = add(node.left, element);
        else if (compareElements(element, node.data) > 0)
            node.right = add(node.right, element);
        return node;
    }

    @Override
    public void remove(T element) throws TreeException {
        if (isEmpty()) throw new TreeException("Binary Search Tree is empty");
        root = remove(root, element);
    }

    //interno
    public BTreeNode<T> remove (BTreeNode<T> node, T element) {
        if (node != null) {
            if (compareElements(element, node.data) < 0)
                node.left = remove(node.left, element);
            else if (compareElements(element, node.data) > 0)
                node.right = remove(node.right, element);
            else if (equals(element, node.data)) {
                //caso 1: El nodo a suprimir no tiene hijos es una hoja
                if (node.left == null && node.right == null) return null;
                    //caso2 El nodo a suprimir solo tiene un hijo.
                    //  En este caso el nodo es remplazado por su hijo
                else if (node.left != null && node.right == null) return node.left;
                else if (node.left == null && node.right != null) return node.right;
                    //caso 3 el node a suprimir tiene 2 hijos
                else {
                    //se obtiene el elemento menor del subarbol der
                    //se remplaza la data de node por su valor
                    //luego se suprime el valor
                    T minvalue = min(node.right);
                    node.data = minvalue;
                    node.right = remove(node.right, minvalue);

                }
            }
        }
        return node; // retorna el arbol modificado sin el elemento suprimido
    }

    @Override
    public T min() throws TreeException {
        if (isEmpty()) throw new TreeException("Binary Search Tree is empty");
        return min(root);
    }

    private T min(BTreeNode<T> node) {
        if (node.left != null) return min(node.right);
        return node.data;
    }

    @Override
    public T max() throws TreeException {
        if (isEmpty()) throw new TreeException("Binary Search Tree is empty");
        return max(root);
    }

    private T max(BTreeNode<T> node) {
        if (node.right != null) return max(node.right);
            return node.data;
    }

    @Override
    public String preOrder() throws TreeException {
        if (isEmpty()) throw new TreeException("Binary search Tree is empty");
        return preOrder(root);
    }

    //Recorrido: N-L-R
    private String preOrder(BTreeNode<T> node) {
        String result = "";
        if (node != null) {
            result = node.data + " ";
            result += preOrder(node.left);
            result += preOrder(node.right);
        }
        return result;
    }

    @Override
    public String toString() {
        if (isEmpty()) return "Binary Tree is empty";
        String result = "Binary Tree Tour\n";
        try {
            result += "PreOrder (N-L-R): " + preOrder(root) + "\n";
            result += "InOrder (L-N-R): " + inOrder() + "\n";
            result += "PostOrder (L-R-N): " + postOrder() + "\n";
        } catch (TreeException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}

