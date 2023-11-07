/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package linkedlists;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author fishb
 */
public class LinkedListTest {
    
    public LinkedListTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of About method, of class LinkedList.
     */
    @Test
    public void testAbout() {
        System.out.println("About");
        LinkedList instance = new LinkedList();
        String expResult = "";
        String result = instance.About();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isEmpty method, of class LinkedList.
     */
    @Test
    public void testIsEmpty() {
        System.out.println("isEmpty");
        LinkedList instance = new LinkedList();
        Boolean expResult = null;
        Boolean result = instance.isEmpty();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of AddHead method, of class LinkedList.
     */
    @Test
    public void testAddHead() {
        System.out.println("AddHead");
        String value = "";
        LinkedList instance = new LinkedList();
        instance.AddHead(value);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of AddTail method, of class LinkedList.
     */
    @Test
    public void testAddTail() {
        System.out.println("AddTail");
        String value = "";
        LinkedList instance = new LinkedList();
        instance.AddTail(value);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of RemoveHead method, of class LinkedList.
     */
    @Test
    public void testRemoveHead() {
        System.out.println("RemoveHead");
        LinkedList instance = new LinkedList();
        String expResult = "";
        String result = instance.RemoveHead();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of PrintList method, of class LinkedList.
     */
    @Test
    public void testPrintList() {
        System.out.println("PrintList");
        LinkedList instance = new LinkedList();
        instance.PrintList();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
