package model.tree;

import org.junit.jupiter.api.Test;

import java.util.Random;

class BSTTest {

    @Test
    void testadd() {
        BST<Integer> bst = new BST<>();
        for (int i = 0; i < 10; i++) {
            int value = new Random().nextInt(1, 30);
            bst.add(value);
        }
        System.out.println(bst);
        try {
            System.out.println("Tree size: " + bst.size());
            System.out.println("min value: " + bst.min());
            System.out.println("max value: " + bst.max());
            for (int i = 0; i < 10; i++) {
                int value = new Random().nextInt(10, 50);
                System.out.println(
                        bst.contains(value)
                                ? "[" + value + "] exists. Height: " + bst.height(value)
                                : "[" + value + "] not exists"
                );
            }
        } catch (TreeException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testRemove() throws TreeException {
        BST<Integer> bst = new BST<>();
        for (int i = 0; i < 10; i++) {
            int value = new Random().nextInt(1, 30);
            bst.add(value);
        }
        System.out.println(bst);
        try {
            for (int i = 0; i < 15; i++) {
                int value = new Random().nextInt(1, 30);
                if (bst.contains(value)) {
                    bst.remove(value);
                    System.out.println("Removed value " + value);
                }
            }
            System.out.println(bst);
        } catch (TreeException e) {
            throw new RuntimeException(e);
        }
    }
}


