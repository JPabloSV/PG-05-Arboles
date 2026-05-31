package ucr.lab.pg05;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.tree.AVL;
import model.tree.BTreeNode;
import model.tree.TreeException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class HelloController {

    @FXML private TextField txtValueSimple;
    @FXML private Button btnInsertSimple;
    @FXML private Button btnDeleteSimple;
    @FXML private Button btnClearSimple;
    @FXML private ComboBox<String> cmbTraversalSimple;
    @FXML private Button btnPlaySimple;
    @FXML private TextArea txtAreaOutputSimple;
    @FXML private Label lblStatsSimple;
    @FXML private Label lblEliminadoSimple;
    @FXML private Pane treeCanvasSimple;

    // Nodo interno para el árbol simple
    private static class Node {
        int value;
        Node left, right;
        Node(int value) { this.value = value; }
    }
    private Node root = null;


    @FXML private TextField txtValueAVL;
    @FXML private Button btnInsertAVL;
    @FXML private Button btnSearchAVL;
    @FXML private Button btnDeleteAVL;
    @FXML private Button btnClearAVL;
    @FXML private ComboBox<String> cmbTraversalAVL;
    @FXML private Button btnPlayAVL;
    @FXML private TextArea txtAreaOutputAVL;
    @FXML private Label lblStatsAVL;
    @FXML private Label lblStatusAVL;
    @FXML private Label lblRotacionAVL;
    @FXML private Pane avlTreePane;

    private AVL<Integer> avl = new AVL<>();

    @FXML
    public void initialize() {

        // ── Tab Simple ──────────────────────────────────
        cmbTraversalSimple.setItems(FXCollections.observableArrayList(
                "PreOrder", "InOrder", "PostOrder", "BFS (Por Niveles)"
        ));
        cmbTraversalSimple.setValue("PostOrder");

        btnInsertSimple.setOnAction(e -> handleInsert());
        btnDeleteSimple.setOnAction(e -> handleDelete());
        btnClearSimple.setOnAction(e -> handleClear());
        btnPlaySimple.setOnAction(e -> handleTraversal());
        lblEliminadoSimple.setText("");
        drawTree();


        cmbTraversalAVL.setItems(FXCollections.observableArrayList(
                "InOrder", "PreOrder", "PostOrder", "BFS (Por Niveles)"
        ));
        cmbTraversalAVL.setValue("InOrder");

        lblRotacionAVL.setText("Sin rotación necesaria");
        lblStatusAVL.setText("");
        lblStatsAVL.setText("Nodos: 0  |  Altura: 0  |  Balanceado: ✔");

        btnInsertAVL.setOnAction(e -> handleInsertAVL());
        btnDeleteAVL.setOnAction(e -> handleDeleteAVL());
        btnSearchAVL.setOnAction(e -> handleSearchAVL());
        btnClearAVL.setOnAction(e -> handleClearAVL());
        btnPlayAVL.setOnAction(e -> handleTraversalAVL());
    }


    private void handleInsert() {
        String input = txtValueSimple.getText().trim();
        if (input.isEmpty()) return;
        try {
            int value = Integer.parseInt(input);
            if (root == null) {
                root = new Node(value);
            } else {
                Queue<Node> queue = new LinkedList<>();
                queue.add(root);
                while (!queue.isEmpty()) {
                    Node current = queue.poll();
                    if (current.left == null) { current.left = new Node(value); break; }
                    else queue.add(current.left);
                    if (current.right == null) { current.right = new Node(value); break; }
                    else queue.add(current.right);
                }
            }
            txtValueSimple.clear();
            lblEliminadoSimple.setText("");
            updateUI();
        } catch (NumberFormatException ex) {
            txtAreaOutputSimple.setText("Error: ingrese un número entero válido.");
        }
    }

    private void handleDelete() {
        String input = txtValueSimple.getText().trim();
        if (input.isEmpty() || root == null) return;
        try {
            int targetValue = Integer.parseInt(input);
            Node targetNode = null, deepestNode = null, deepestParent = null;
            Queue<Node> queue = new LinkedList<>();
            queue.add(root);
            while (!queue.isEmpty()) {
                Node current = queue.poll();
                if (current.value == targetValue) targetNode = current;
            if (current.left != null) { deepestParent = current; queue.add(current.left); }
            if (current.right != null) { deepestParent = current; queue.add(current.right); }
            deepestNode = current;
        }
            if (targetNode != null) {
                lblEliminadoSimple.setText("✕ Eliminado: " + targetValue);
                if (root == deepestNode) { root = null; }
                else {
                    targetNode.value = deepestNode.value;
                    if (deepestParent.right == deepestNode) deepestParent.right = null;
                    else if (deepestParent.left == deepestNode) deepestParent.left = null;
                }
                txtValueSimple.clear();
                updateUI();
            } else {
                lblEliminadoSimple.setText("No encontrado: " + targetValue);
            }
        } catch (NumberFormatException ex) {
            txtAreaOutputSimple.setText("Error: ingrese un entero para eliminar.");
        }
    }

    private void handleClear() {
        root = null;
        lblEliminadoSimple.setText("Árbol limpio");
        txtAreaOutputSimple.setText("");
        updateUI();
    }

    private void handleTraversal() {
        if (root == null) { txtAreaOutputSimple.setText("El árbol está vacío."); return; }
        String type = cmbTraversalSimple.getValue();
        List<Integer> result = new ArrayList<>();
        switch (type) {
            case "PreOrder"         -> preOrder(root, result);
            case "InOrder"          -> inOrder(root, result);
            case "PostOrder"        -> postOrder(root, result);
            case "BFS (Por Niveles)"-> bfs(root, result);
        }
        txtAreaOutputSimple.setText(type + ": " + result);
    }

    private void preOrder(Node node, List<Integer> list) {
        if (node == null) return;
        list.add(node.value); preOrder(node.left, list); preOrder(node.right, list);
    }
    private void inOrder(Node node, List<Integer> list) {
        if (node == null) return;
        inOrder(node.left, list); list.add(node.value); inOrder(node.right, list);
    }
    private void postOrder(Node node, List<Integer> list) {
        if (node == null) return;
        postOrder(node.left, list); postOrder(node.right, list); list.add(node.value);
    }
    private void bfs(Node node, List<Integer> list) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(node);
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            list.add(current.value);
            if (current.left != null) queue.add(current.left);
            if (current.right != null) queue.add(current.right);
        }
    }

    private void updateUI() { updateStats(); drawTree(); }

    private void updateStats() {
        lblStatsSimple.setText("Nodos: " + countNodes(root) + "  |  Altura: " + calculateHeight(root));
    }
    private int countNodes(Node node) {
        if (node == null) return 0;
        return 1 + countNodes(node.left) + countNodes(node.right);
    }
    private int calculateHeight(Node node) {
        if (node == null) return -1;
        return 1 + Math.max(calculateHeight(node.left), calculateHeight(node.right));
    }

    private void drawTree() {
        treeCanvasSimple.getChildren().clear();
        if (root == null) return;
        double startX = treeCanvasSimple.getWidth() > 0 ? treeCanvasSimple.getWidth() / 2 : 300;
        double hGap   = treeCanvasSimple.getWidth() > 0 ? treeCanvasSimple.getWidth() / 4 : 150;
        drawNode(root, startX, 40, hGap, 70);
    }

    private void drawNode(Node node, double x, double y, double hGap, double vGap) {
        if (node == null) return;
        if (node.left != null) {
            double cx = x - hGap, cy = y + vGap;
            addLine(treeCanvasSimple, x, y, cx, cy);
            drawNode(node.left, cx, cy, hGap / 2, vGap);
        }
        if (node.right != null) {
            double cx = x + hGap, cy = y + vGap;
            addLine(treeCanvasSimple, x, y, cx, cy);
            drawNode(node.right, cx, cy, hGap / 2, vGap);
        }
        Color color = (node == root) ? Color.web("#3498DB")
                : (node.left == null && node.right == null) ? Color.web("#2ECC71")
                : Color.web("#1F3868");
        addCircleWithLabel(treeCanvasSimple, x, y, 20, color, String.valueOf(node.value), null);
    }

    private void handleInsertAVL() {
        String input = txtValueAVL.getText().trim();
        if (input.isEmpty()) return;
        try {
            int value = Integer.parseInt(input);

            // Snapshot ANTES de insertar
            String snapAntes = snapshotArbol(avl.root);
            avl.add(value);
            // Snapshot DESPUÉS de insertar
            String snapDespues = snapshotArbol(avl.root);

            // Si la estructura cambió hubo rotación
            String rotacion;
            if (snapAntes.equals(snapDespues) || snapAntes.equals("null")) {
                rotacion = "Sin rotación necesaria";
            } else {
                rotacion = detectarTipoRotacion(avl.root, value);
            }

            txtValueAVL.clear();
            lblRotacionAVL.setText(rotacion);
            lblStatusAVL.setText("✔ Insertado: " + value);
            updateUIAvl();
        } catch (NumberFormatException ex) {
            lblStatusAVL.setText("Error: ingrese un número entero válido.");
        }
    }

    private void handleDeleteAVL() {
        String input = txtValueAVL.getText().trim();
        if (input.isEmpty()) return;
        try {
            int value = Integer.parseInt(input);
            if (avl.isEmpty()) { lblStatusAVL.setText("El árbol está vacío."); return; }
            try {
                avl.remove(value);
                txtValueAVL.clear();
                lblRotacionAVL.setText("Sin rotación necesaria");
                lblStatusAVL.setText("✔ Eliminado: " + value);
                updateUIAvl();
            } catch (TreeException ex) {
                lblStatusAVL.setText("No encontrado: " + value);
            }
        } catch (NumberFormatException ex) {
            lblStatusAVL.setText("Error: ingrese un número entero válido.");
        }
    }

    private void handleSearchAVL() {
        String input = txtValueAVL.getText().trim();
        if (input.isEmpty()) return;
        try {
            int value = Integer.parseInt(input);
            if (avl.isEmpty()) { lblStatusAVL.setText("El árbol está vacío."); return; }
            try {
                boolean found = avl.contains(value);
                lblStatusAVL.setText(found ? "✔ Encontrado: " + value : "✘ No encontrado: " + value);
                drawAVLTree(value); // resalta el nodo encontrado
            } catch (TreeException ex) {
                lblStatusAVL.setText("Error al buscar.");
            }
        } catch (NumberFormatException ex) {
            lblStatusAVL.setText("Error: ingrese un número entero válido.");
        }
    }

    private void handleClearAVL() {
        avl.clear();
        avlTreePane.getChildren().clear();
        lblStatsAVL.setText("Nodos: 0  |  Altura: 0  |  Balanceado: ✔");
        lblStatusAVL.setText("");
        lblRotacionAVL.setText("Sin rotación necesaria");
        txtAreaOutputAVL.setText("");
    }

    private void handleTraversalAVL() {
        if (avl.isEmpty()) { txtAreaOutputAVL.setText("El árbol está vacío."); return; }
        String type = cmbTraversalAVL.getValue();
        try {
            String result = switch (type) {
                case "InOrder"           -> "InOrder: ["   + avl.inOrder().trim()   + "]";
                case "PreOrder"          -> "PreOrder: ["  + avl.preOrder().trim()  + "]";
                case "PostOrder"         -> "PostOrder: [" + avl.postOrder().trim() + "]";
                case "BFS (Por Niveles)" -> "BFS: " + bfsAVL(avl.root);
                default -> "";
            };
            txtAreaOutputAVL.setText(result);
        } catch (TreeException ex) {
            txtAreaOutputAVL.setText("Error al recorrer el árbol.");
        }
    }

    private String bfsAVL(BTreeNode<Integer> root) {
        if (root == null) return "[]";
        List<Integer> result = new ArrayList<>();
        Queue<BTreeNode<Integer>> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            BTreeNode<Integer> current = queue.poll();
            result.add(current.data);
            if (current.left != null) queue.add(current.left);
            if (current.right != null) queue.add(current.right);
        }
        return result.toString();
    }

    private void updateUIAvl() {
        updateStatsAVL();
        drawAVLTree(-1);
    }

    private void updateStatsAVL() {
        try {
            int nodos = avl.isEmpty() ? 0 : avl.size();
            int altura = avl.isEmpty() ? 0 : avl.height();
            boolean balanceado = avl.isBalanced();
            lblStatsAVL.setText("Nodos: " + nodos + "  |  Altura: " + altura
                    + "  |  Balanceado: " + (balanceado ? "✔" : "✘"));
        } catch (TreeException ex) {
            lblStatsAVL.setText("Nodos: 0  |  Altura: 0  |  Balanceado: ✔");
        }
    }

    private void drawAVLTree(int searchValue) {
        avlTreePane.getChildren().clear();
        if (avl.isEmpty()) return;
        double startX = avlTreePane.getWidth() > 0 ? avlTreePane.getWidth() / 2 : 380;
        double hGap   = avlTreePane.getWidth() > 0 ? avlTreePane.getWidth() / 4 : 190;
        drawAVLNode(avl.root, startX, 50, hGap, 80, searchValue);
    }

    private void drawAVLNode(BTreeNode<Integer> node, double x, double y,
                             double hGap, double vGap, int searchValue) {
        if (node == null) return;

        if (node.left != null) {
            double cx = x - hGap, cy = y + vGap;
            addLine(avlTreePane, x, y, cx, cy);
            drawAVLNode(node.left, cx, cy, hGap / 2, vGap, searchValue);
        }
        if (node.right != null) {
            double cx = x + hGap, cy = y + vGap;
            addLine(avlTreePane, x, y, cx, cy);
            drawAVLNode(node.right, cx, cy, hGap / 2, vGap, searchValue);
        }

        int fe = avl.getBalanceFactor(node);
        Color color;
        if (node == avl.root)      color = Color.web("#3498DB"); // azul — raíz
        else if (searchValue == node.data) color = Color.web("#E8A020"); // ámbar — buscado
        else if (fe == 0)          color = Color.web("#1ABC9C"); // verde — perfecto
        else if (fe == 1 || fe == -1) color = Color.web("#2C6B8A"); // azul gris — aceptable
        else                       color = Color.web("#E74C3C"); // rojo — desbalanceado

        // Etiqueta FE encima del círculo
        String feLabel = "FE=" + fe;
        Text feText = new Text(feLabel);
        feText.setFill(Color.web("#BDC3C7"));
        feText.setFont(Font.font("Consolas", FontWeight.BOLD, 10));
        feText.setX(x - feText.getLayoutBounds().getWidth() / 2 - 4);
        feText.setY(y - 24);
        avlTreePane.getChildren().add(feText);

        // Círculo + valor
        addCircleWithLabel(avlTreePane, x, y, 22, color, String.valueOf(node.data), null);
    }


    private void addLine(Pane pane, double x1, double y1, double x2, double y2) {
        Line line = new Line(x1, y1, x2, y2);
        line.setStroke(Color.web("#4A6FA5"));
        line.setStrokeWidth(1.5);
        pane.getChildren().add(line);
    }

    private void addCircleWithLabel(Pane pane, double x, double y, double radius,
                                    Color fill, String label, String subLabel) {
        Circle circle = new Circle(x, y, radius);
        circle.setFill(fill);
        circle.setStroke(Color.WHITE);
        circle.setStrokeWidth(1.5);

        Text text = new Text(label);
        text.setFill(Color.WHITE);
        text.setFont(Font.font("Calibri", FontWeight.BOLD, 13));
        text.setX(x - text.getLayoutBounds().getWidth() / 2);
        text.setY(y + text.getLayoutBounds().getHeight() / 4);

        pane.getChildren().addAll(circle, text);
    }


    private String snapshotArbol(BTreeNode<Integer> node) {
        if (node == null) return "null";
        return "(" + node.data
                + " L:" + snapshotArbol(node.left)
                + " R:" + snapshotArbol(node.right) + ")";
    }

    // Recorre el árbol ya rotado buscando qué tipo de rotación ocurrió
    private String detectarTipoRotacion(BTreeNode<Integer> node, int value) {
        if (node == null) return "Sin rotación necesaria";

        // Primero buscar en subárboles
        String left  = detectarTipoRotacion(node.left,  value);
        String right = detectarTipoRotacion(node.right, value);
        if (!left.equals("Sin rotación necesaria"))  return left;
        if (!right.equals("Sin rotación necesaria")) return right;

        int fe = avl.getBalanceFactor(node);

        if (fe >= 2 && node.left != null) {
            if (value < node.left.data) return "Rotación Simple Derecha (II)";
            else                        return "Rotación Doble Izquierda-Derecha (ID)";
        }
        if (fe <= -2 && node.right != null) {
            if (value > node.right.data) return "Rotación Simple Izquierda (DD)";
            else                         return "Rotación Doble Derecha-Izquierda (DI)";
        }

        return "Sin rotación necesaria";
    }
}