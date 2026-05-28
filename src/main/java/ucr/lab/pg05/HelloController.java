package ucr.lab.pg05;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

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

    private Node root = null;

    //Clase interna para los nodos del árbol
    private static class Node {
        int value;
        Node left, right;

        Node(int value) {
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    @FXML
    public void initialize() {
        //Configurar las opciones del ComboBox de Recorridos
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

                    if (current.left == null) {
                        current.left = new Node(value);
                        break;
                    } else {
                        queue.add(current.left);
                    }

                    if (current.right == null) {
                        current.right = new Node(value);
                        break;
                    } else {
                        queue.add(current.right);
                    }
                }
            }

            txtValueSimple.clear();
            lblEliminadoSimple.setText("");
            updateUI();

        } catch (NumberFormatException ex) {
            txtAreaOutputSimple.setText("Error: Por favor ingrese un número entero válido.");
        }
    }

    private void handleDelete() {
        String input = txtValueSimple.getText().trim();
        if (input.isEmpty() || root == null) return;

        try {
            int targetValue = Integer.parseInt(input);

            Node targetNode = null;
            Node deepestNode = null;
            Node deepestParent = null;

            Queue<Node> queue = new LinkedList<>();
            queue.add(root);


            while (!queue.isEmpty()) {
                Node current = queue.poll();

                if (current.value == targetValue) {
                    targetNode = current;
                }

                if (current.left != null) {
                    deepestParent = current;
                    queue.add(current.left);
                }
                if (current.right != null) {
                    deepestParent = current;
                    queue.add(current.right);
                }
                deepestNode = current;
            }


            if (targetNode != null) {
                lblEliminadoSimple.setText("✕ Eliminado: " + targetValue);

                if (root == deepestNode) {
                    root = null;
                } else {

                    targetNode.value = deepestNode.value;
                    if (deepestParent.right == deepestNode) {
                        deepestParent.right = null;
                    } else if (deepestParent.left == deepestNode) {
                        deepestParent.left = null;
                    }
                }
                txtValueSimple.clear();
                updateUI();
            } else {
                lblEliminadoSimple.setText("No se encontró el valor " + targetValue);
            }

        } catch (NumberFormatException ex) {
            txtAreaOutputSimple.setText("Error: Ingrese un entero para eliminar.");
        }
    }

    private void handleClear() {
        root = null;
        lblEliminadoSimple.setText("Árbol limpio");
        txtAreaOutputSimple.setText("");
        updateUI();
    }


    private void handleTraversal() {
        if (root == null) {
            txtAreaOutputSimple.setText("El árbol está vacío.");
            return;
        }

        String type = cmbTraversalSimple.getValue();
        List<Integer> result = new ArrayList<>();

        switch (type) {
            case "PreOrder":
                preOrder(root, result);
                break;
            case "InOrder":
                inOrder(root, result);
                break;
            case "PostOrder":
                postOrder(root, result);
                break;
            case "BFS (Por Niveles)":
                bfs(root, result);
                break;
        }

        txtAreaOutputSimple.setText(type + ": " + result.toString());
    }

    //Métodos Auxiliares de Recorridos
    private void preOrder(Node node, List<Integer> list) {
        if (node == null) return;
        list.add(node.value);
        preOrder(node.left, list);
        preOrder(node.right, list);
    }

    private void inOrder(Node node, List<Integer> list) {
        if (node == null) return;
        inOrder(node.left, list);
        list.add(node.value);
        inOrder(node.right, list);
    }

    private void postOrder(Node node, List<Integer> list) {
        if (node == null) return;
        postOrder(node.left, list);
        postOrder(node.right, list);
        list.add(node.value);
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

    //Lógica de Actualización y Renderizado Visual

    private void updateUI() {
        updateStats();
        drawTree();
    }

    private void updateStats() {
        int nodeCount = countNodes(root);
        int height = calculateHeight(root);
        lblStatsSimple.setText("Nodos: " + nodeCount + "  |  Altura: " + height);
    }

    private int countNodes(Node node) {
        if (node == null) return 0;
        return 1 + countNodes(node.left) + countNodes(node.right);
    }

    private int calculateHeight(Node node) {
        if (node == null) return -1; // Definición: Árbol con raíz sola tiene altura 0
        return 1 + Math.max(calculateHeight(node.left), calculateHeight(node.right));
    }

    private void drawTree() {
        treeCanvasSimple.getChildren().clear();
        if (root == null) return;

        double startX = treeCanvasSimple.getWidth() > 0 ? treeCanvasSimple.getWidth() / 2 : 300;
        double startY = 40;
        double hGap = treeCanvasSimple.getWidth() > 0 ? treeCanvasSimple.getWidth() / 4 : 150;
        double vGap = 60;

        drawNode(root, startX, startY, hGap, vGap);
    }

    private void drawNode(Node node, double x, double y, double hGap, double vGap) {
        if (node == null) return;

        if (node.left != null) {
            double childX = x - hGap;
            double childY = y + vGap;
            Line line = new Line(x, y, childX, childY);
            line.setStroke(Color.web("#1F3868"));
            line.setStrokeWidth(2);
            treeCanvasSimple.getChildren().add(line);
            drawNode(node.left, childX, childY, hGap / 2, vGap);
        }

        if (node.right != null) {
            double childX = x + hGap;
            double childY = y + vGap;
            Line line = new Line(x, y, childX, childY);
            line.setStroke(Color.web("#1F3868"));
            line.setStrokeWidth(2);
            treeCanvasSimple.getChildren().add(line);
            drawNode(node.right, childX, childY, hGap / 2, vGap);
        }
        Color nodeColor;
        if (node == root) {
            nodeColor = Color.web("#3498DB");
        } else if (node.left == null && node.right == null) {
            nodeColor = Color.web("#2ECC71");
        } else {
            nodeColor = Color.web("#1F3868");
        }

        Circle circle = new Circle(x, y, 18);
        circle.setFill(nodeColor);
        circle.setStroke(Color.WHITE);
        circle.setStrokeWidth(1);


        Text text = new Text(String.valueOf(node.value));
        text.setFill(Color.WHITE);
        text.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");

        text.setX(x - (text.getLayoutBounds().getWidth() / 2));
        text.setY(y + (text.getLayoutBounds().getHeight() / 4));

        treeCanvasSimple.getChildren().addAll(circle, text);
    }
}
