package model.tree;

import java.util.Comparator;

public class AVL <T extends Comparable<T>> extends BST<T>{

    @Override
    public void add(T element) {
        this.root = add(root, element, "root");
    }

    public BTreeNode<T> add(BTreeNode<T> node, T element, String path) {
        if (node == null) {
            node = new BTreeNode<>(element, path);
        } else if (compareElements(element, node.data)<0)
            node.left = add(node.left, element, "Added as: "+path+"/left");
        else if (compareElements(element,node.data)>0)
            node.right = add(node.right, element, "Added as: "+path+"/rigth");

        //Obtenemos el factor de balanceo de este ancestro
        int balance = getBalanceFactor(node);
        //Una vez optenido el factor de balanceo revisamos los 4 casos posibles
        if (balance> 1 && compareElements(element,node.left.data) < 0) {
            node.path = path+", Simple Rigth Rotate";
            return rigthRotate(node);
        }
        return  node;
    }

    @Override
    public void remove(T element) throws TreeException {
        super.remove(element);
    }
}
