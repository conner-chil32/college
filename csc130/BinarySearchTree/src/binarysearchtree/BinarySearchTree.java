
package binarysearchtree;
import java.io.*;
/**
 *
 * @author Conner C.
 */
public class BinarySearchTree {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        String filepath = "C:\\Users\\fishb\\OneDrive\\Documents\\GitHub\\college2\\college\\csc130\\BinarySearchTree\\src\\binarysearchtree\\years.txt"; //change the path to read the file correctly
        BufferedReader br = new BufferedReader(new FileReader(new File(filepath)));
        BinaryTree binarytree = new BinaryTree();
        String str;
        
        while((str = br.readLine()) != null) {
            binarytree.add(Integer.parseInt(str), br.readLine());
        }
        
        int keyToFind = 1964; //replace this with the key that you want to find in the binary tree
        
        // Test Cases
        
        System.out.println(binarytree.getTree());
        System.out.println(binarytree.find(keyToFind));
        System.out.println(binarytree.getPreorder());
        System.out.println(binarytree.getInorder());
        System.out.println(binarytree.getPostorder());
        
    }
    
}

class Node {
    
    public String value;
    public Node left;
    public Node right;
    public int key;
    
    public Node(String value, int key) {
        this.value = value;
        this.left = null;
        this.right = null;
        this.key = key;
    }
    
    public Node(String value, int key, Node left, Node right) {
        this.value = value;
        this.left = left;
        this.right = right;
        this.key = key;
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
        result += "+--- ";
        result += key + ": " + value + "\n";
        
        if(left != null) {
            result += left.getTree(indent+1);
        }
        if(right != null) {
            result += right.getTree(indent+1);
        }
        
        return result;
    }
    
    public void add(int key, String value) {
        if(key < this.key) {
            if(left != null) {
                left.add(key, value);
            } else {
                left = new Node(value, key);
            }
        }
        
        if(key > this.key) {
            if(right != null) {
                right.add(key, value);
            } else {
                right = new Node(value, key);
            }
        }
    }
    
    public String find(int key) {
        String output = "There is no value";
        if(key == this.key) {
            output = value;
        }
        if(key < this.key && left != null) {
            output = left.find(key);
        }
        if(key > this.key && right != null) {
            output = right.find(key);
        }
        return output;
    }
}

class BinaryTree {
    private Node root;
    
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
    
    public void add(int key, String value) {
        if(root == null) {
            root = new Node(value, key);
        } else {
            root.add(key, value);
        }
    }
    
    public String find(int key) {
        return root.find(key);
    }
}
