package model.tree;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Random;

class AVLTest {

    @Test
    void testAVL() throws TreeException {
        AVL<Integer> avl = new AVL<>();
        Random random = new Random();
        ArrayList<Integer> inserted = new ArrayList<>();

        while (inserted.size() < 30) {
            int value = random.nextInt(20, 201);
            if (!inserted.contains(value)) {
                avl.add(value);
                inserted.add(value);
            }
        }

        System.out.println("ÁRBOL AVL INICIAL");
        System.out.println(avl);

        System.out.println("Tamaño: " + avl.size());
        System.out.println("Mínimo: " + avl.min());
        System.out.println("Máximo: " + avl.max());

        System.out.println("\n¿Está balanceado? " + avl.isBalanced());

        System.out.println("\nEliminando 5 elementos");
        for (int i = 0; i < 5; i++) {
            int toRemove = inserted.get(i);
            System.out.println("Eliminando: " + toRemove);
            avl.remove(toRemove);
        }


        System.out.println("\nÁRBOL TRAS ELIMINACIONES");
        System.out.println(avl);

        System.out.println("¿Está balanceado? " + avl.isBalanced());

        System.out.println("\nÁRBOL FINAL");
        System.out.println(avl);

        System.out.println("¿Está balanceado? " + avl.isBalanced());
        System.out.println("Altura final: " + avl.height());
    }
}