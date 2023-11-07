/**
 *
 * @author Conner Childers (11/7/2023)
 */
public class BinaryTree {

    public static void main(String[] args) {
        // Testing binary tree
        System.out.println("Testing binary tree\n-------------------");
        BasicBinaryTree tree = new BasicBinaryTree();
        tree.root = new Node("Cat", 
                        new Node("Dog",
                                new Node("Hamster"),
                                new Node("Mouse")),
                        new Node("Chicken"));
        System.out.println(tree.getTree());
        
        System.out.println("Testing Pre/In/Postorder tree printing\n--------------------------------------");
        BasicBinaryTree tree2 = new BasicBinaryTree();
        tree2.root = new Node("A",
                        new Node("B",
                            new Node("D"),
                            new Node("E")),
                        new Node("C",
                            new Node("F",
                                new Node("H"),
                                new Node("I")),
                            new Node("G")));
        System.out.println(tree2.root.getTree(0));
        
        System.out.println("Preorder:");
        System.out.println(tree2.root.getPreorder());
        System.out.println("Inorder:");
        System.out.println(tree2.root.getInorder());
        System.out.println("Postorder:");
        System.out.println(tree2.root.getPostorder());
    }
    
}

class Node {
    
    private String value;
    private Node left;
    private Node right;
    
    public Node(String value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }
    
    public Node(String value, Node left, Node right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }
    
    public String getPreorder() {
        String result = "";
        result += value + " ";
        if(left != null) {
            result += left.getPreorder();
        }
        if(right != null) {
            result += right.getPreorder();
        }
        
        return result;
    }
    
    public String getInorder() {
        String result = "";
        if(left != null) {
            result += left.getInorder();
        }
        result += value + " ";
        if(right != null) {
            result += right.getInorder();
        }
        
        return result;
    }
    
    public String getPostorder() {
        String result = "";
        if(left != null) {
            result += left.getPostorder();
        }
        if(right != null) {
            result += right.getPostorder();
        }
        result += value + " ";
        
        return result;
    }
    
    public String getTree(int indent) {
        String result = "";
        
        for(int i=0; i < indent*2; i++){
            result += " ";
        }
        result += "+---";
        result += value + "\n";
        
        if(left != null) {
            result += left.getTree(indent+1);
        }
        if(right != null) {
            result += right.getTree(indent+1);
        }
        
        return result;
    }
}

class BasicBinaryTree {
    public Node root;
    
    public String about() {
        return "This a basic binary tree written by Conner Childers";
    }
    
    public String getPreorder() {
        return root.getPreorder();
    }
    
    public String getInorder() {
        return root.getInorder();
    }
    
    public String getPostorder() {
        return root.getPostorder();
    }
    
    public String getTree() {
        return root.getTree(0);
    }
}
