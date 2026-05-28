package model.tree;


import java.util.Random;

public class BTree<T extends Comparable<T>> implements Tree<T> {
    public BTreeNode<T> root; //representa la unica entrada al árbol

    //Constructor
    public BTree() {
        this.root = null;
    }

    @Override
    public int size() throws TreeException {
        if (isEmpty()) throw new TreeException("Binary Tree is empty");
        return size(root);
    }

    private int size(BTreeNode<T> nodo) {
        if (nodo == null) return 0;
        return size(nodo.left) + size(nodo.right) + 1;
    }

    @Override
    public void clear() {
        this.root = null;

    }

    @Override
    public boolean isEmpty() {
        return this.root == null;
    }

    @Override
    public boolean contains(T element) throws TreeException {
        if (isEmpty()) throw new TreeException("Binary Tree is empty");
        return binarySearch(this.root, element);
    }

    private boolean binarySearch(BTreeNode<T> node, T element) {
        if (node == null) return false;
        else if (equals(node.data, element)) return true;
        else return binarySearch(node.left, element)
                    || binarySearch(node.right, element);
    }

    @Override
    public void add(T element) {
        //this.root = add(root, element);
        this.root = add(root, element, "root");
    }

    private BTreeNode<T> add(BTreeNode<T> node, T element) {
        if (node == null) {
            node = new BTreeNode<>(element);
        } else {
            //debemos establecer algún criterio para insertar elementos
            int value = new Random().nextInt(10);
            if (value % 2 == 0) //si el valor es par inserte por la izq
                node.left = add(node.left, element);
            else
                node.right = add(node.right, element);
        }
        return node;
    }

    BTreeNode<T> add(BTreeNode<T> node, T element, String path) {
        if (node == null) {
            node = new BTreeNode<>(element, path);
        } else {
            //debemos establecer algún criterio para insertar elementos
            int value = new Random().nextInt(10);
            if (value % 2 == 0) //si el valor es par inserte por la izq
                node.left = add(node.left, element, path + "/left");
            else
                node.right = add(node.right, element, path + "/right");
        }
        return node;
    }

    @Override
    public void remove(T element) throws TreeException {
        if (isEmpty()) throw new TreeException("Binary Tree is empty");
        root = remove(root, element);
    }

    //interno
    public BTreeNode<T> remove (BTreeNode<T> node, T element) {
       if (node != null){
           if(equals(node.data, element)){//encontro ele lelemtno a eliminar
               //caso 1, es un nodo que no tiene hijos
               if(node.left == null && node.right == null) return null;
               else{//caso 2 el nodo solo tienen un hijo,en este caso solo se remplaza por todoo el subarbol
                   if(node.left != null && node.right== null){
                       node.left = newPath(node.left, node.path);
                       return node.left; //sube todoo ek subarbol izq
                   }else if(node.right== null && node.right!= null){
                       node.right = newPath(node.right, node.path);
                       return node.right; // sube el subarbol der
                   }else{ //caso 3 el nodo tiene 2 hijos
                       //buscar el valor minimo de subarbol derecho
                       // se reeemplza la data del nodo con ese valor
                       // luego se suprime el valor min de subarbol der
                       T minValue = min(node.right);
                       node.data = minValue;
                       node.right = remove(node.right, minValue);

                   }

               }

           } else {
               node.left = remove(node.left,element);
               node.right = remove(node.right,element);
           }

       } return node; //retorna el arbol con un elemento meno}
    }

    //metodoo para actualizar las rutas de todos los nodos del arbol
    private BTreeNode<T> newPath(BTreeNode<T> node, String path) {
        if(node!= null){
            node.path = path;
            newPath(node.left, path+"/left");
            newPath(node.right, path+"/rigth");
        }
        return node;
    }

    //devuelve la altura de un elemento epeficifo dentro del arbol
    @Override
    public int height(T element) throws TreeException {
        if (isEmpty()) throw new TreeException("binary tree is empty");
        return height(root, element, 0);
    }

    private int height(BTreeNode<T> node, T element, int count) {
        if (node == null) return 0;
        else if (equals(node.data,element)) return count;
        else return Math.max(height(node.left, element, ++count), height(node.right, element, count));

    }
    //de vuelve
    @Override
    public int height() throws TreeException {
        if (isEmpty()) throw new TreeException("binary tree is empty");
        return height(root) -1;//porque la altura de la raiz es cero
    }
    private int height(BTreeNode<T> node) {
        if (node == null) return 0;
        else return Math.max(height(node.left), height(node.right))+1;

    }

    @Override
    public T min() throws TreeException {
        if (isEmpty()) throw new TreeException("Binary Tree is empty");
        return min(root);
    }

    private T min(BTreeNode<T> node) {
        if (node.left != null && node.right != null)
            return minElement(node.data, minElement(min(node.left), min(node.right)));

            //Caso dos
        else if (node.left != null)
            return minElement(node.data, min(node.left));
            //Caso tres
        else if (node.right != null)
            return minElement(node.data, min(node.right));
            //Es una hoja
        else return node.data;
    }

    private T minElement(T node1, T node2) {
        if (node1 == null) return node2;
        else if (node2 == null) return node1;
        return compareElements(node1, node2) <= 0 ? node1 : node2;
    }

    @Override
    public T max() throws TreeException {
        if (isEmpty()) throw new TreeException("Binary Tree is empty");
        return max(root);
    }

    private T max(BTreeNode<T> node) {
        if (node.left != null && node.right != null)
            return maxElement(node.data, maxElement(max(node.left), max(node.right)));

            //Caso dos
        else if (node.left != null)
            return maxElement(node.data, max(node.left));
            //Caso tres
        else if (node.right != null)
            return maxElement(node.data, max(node.right));
            //Es una hoja
        else return node.data;
    }

    private T maxElement(T node1, T node2) {
        if (node1 == null) return node2;
        else if (node2 == null) return node1;
        return compareElements(node1, node2) >= 0 ? node1 : node2;
    }

    @Override
    public String preOrder() throws TreeException {
        if (isEmpty()) throw new TreeException("Binary Tree is empty");
        return preOrder(root);
    }

    //Recorrido: N-L-R
    private String preOrder(BTreeNode<T> node) {
        String result = "";
        if (node != null) {
            result = node.data + "(" + node.path + ") ";
            result += preOrder(node.left);
            result += preOrder(node.right);
        }
        return result;
    }

    @Override
    public String inOrder() throws TreeException {
        if (isEmpty()) throw new TreeException("Binary Tree is empty");
        return inOrder(root);
    }

    //Recorrido: L-N-R
    private String inOrder(BTreeNode<T> node) {
        String result = "";
        if (node != null) {
            result = inOrder(node.left);
            result += node.data + ", ";
            //result += node.data+"("+node.path+") ";
            result += inOrder(node.right);
        }
        return result;
    }

    @Override
    public String postOrder() throws TreeException {
        if (isEmpty()) throw new TreeException("Binary Tree is empty");
        return postOrder(root);
    }

    @Override
    public String nodeHeight() throws TreeException {
        return "";
    }

    //Recorrido: L-R-N
    private String postOrder(BTreeNode<T> node) {
        String result = "";
        if (node != null) {
            result = postOrder(node.left);
            result += postOrder(node.right);
            result += node.data + ",";

        }
        return result;
    }


    public String nodeHeight(BTreeNode<T> node) throws TreeException {
        String result = "";
        if (node!=null){
            result= node.data+", heigth: "+height(node.data)+"\n";
            result += nodeHeight(node.left);
            result += nodeHeight(node.right);
        }
        return result;
    }

    @Override
    public String toString() {
        if (isEmpty()) return "Binary Tree is empty";
        String result = "Binary Tree Tour\n";
        result += "PreOrder (N-L-R): " + preOrder(root) + "\n";
        result += "InOrder (L-N-R): " + inOrder(root) + "\n";
        result += "PostOrder (L-R-N): " + postOrder(root) + "\n";
        return result;
    }

    public boolean equals(T a, T b) {
        return a == null ? b == null : a.equals(b);
    }

    public int compareElements(T node1, T node2) {
        return node1.compareTo(node2);
    }
}
