package model.tree;

import org.junit.jupiter.api.Test;

import java.util.Random;

class BTreeTest {

    @Test
    void insert() {
        BTree<Integer> bTree = new BTree<>();
        bTree.add(10);
        bTree.add(20);
        bTree.add(30);
        bTree.add(40);
        for (int i = 0; i < 10; i++) {
            int value = new Random().nextInt(10, 50);
            bTree.add(value);
        }
        System.out.println(bTree);

        try {
            System.out.println("Tree size: "+bTree.size());
            System.out.println("min value: "+bTree.min());
            System.out.println("max value: "+bTree.max());
            for (int i = 0; i < 10; i++) {
                int value = new Random().nextInt(10, 50);
                System.out.println(
                        bTree.contains(value)
                                ?"["+value+ "] exists. Height: " + bTree.height(value) : "["+value+"] not exists"
                );
            }
        } catch (TreeException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testHeight() {
        BTree<Integer> bTree = new BTree<>();
        for(int i= 0; i < 6; i++){
            int value = new Random().nextInt(1,30);
            bTree.add(value);
        }

        try{
            System.out.println(bTree);
            System.out.println("Tree size: "+bTree.size());
            System.out.println("min value: "+bTree.min());
            System.out.println("max value: "+bTree.max());
            System.out.println("Tree height: "+bTree.height());
        }catch (TreeException e){
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testRemove() throws TreeException {
        BTree<Integer>bTree = new BTree<>();
        for(int i =0; i < 10; i++){
            int value = new Random().nextInt(1, 30);
            bTree.add(value);
        }
        System.out.println(bTree);
        for(int i = 0; i < 15; i ++){
            int value = new Random().nextInt(1,30);
            if(bTree.contains(value)){
                bTree.remove(value);
            }

        }
    }

    @Test
    void testNodeHeigth() {
        BTree<Integer> btree = new BTree<>();
        for (int i = 0; i <10;i++) {
            int value = new Random().nextInt(1,30);
            btree.add(value);
        }
        System.out.println(btree);
        try {
            System.out.println(btree.nodeHeight());
        }catch (TreeException e) {
            throw new RuntimeException(e);
        }
    }

}







