import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

/**
 * Basic Test Cases for
 * Adelson-Velskii and Landis' Tree
 * Student Edition
 *
 * @author Jonathan Jemson
 * @version 1.1
 *
 * Changelog
 * 1.1: Fix addLeftHeavy() to actually be left heavy.
 */
public class BasicAVLTest {

	private AVL<Integer> avl;

	@Before
	public void setup() {
		avl = new AVL<Integer>();
	}

	@Test(timeout = 200)
    public void testAdd() {
    	// no rotation
    	avl.add(2);
    	avl.add(4);

    	// Data Checks
    	assertEquals((Integer) 2, avl.getRoot().getData());
    	assertEquals((Integer) 4, avl.getRoot().getRight().getData());

        // Height and Balance Factor Checks
        assertEquals(2, avl.getRoot().getHeight());
        assertEquals(-1, avl.getRoot().getBalanceFactor());
        assertEquals(1, avl.getRoot().getRight().getHeight());
        assertEquals(0, avl.getRoot().getRight().getBalanceFactor());
    }

	@Test(timeout = 200)
	public void testRemove() {
		avl.add(4);
		avl.add(20);
		avl.add(-200);
		avl.remove(4);

		assertEquals((Integer) 20, avl.getRoot().getData());
		assertEquals((Integer) (-200), avl.getRoot().getLeft().getData());
	}
	
    @Test(timeout = 200)
	public void testGet() {
		avl.add(2);
		avl.add(54);
		avl.add(420);
		assertEquals((Integer) 420, avl.get(420));
	}
	
    @Test(timeout = 200)
    public void testContains() {
    	avl.add(3);
    	avl.add(2);
    	avl.add(1000000);
    	assertTrue(avl.contains(3));
    	assertTrue(avl.contains(1000000));
    }
		
	@Test(timeout = 200)
	public void testIsEmpty() {
		avl.add(2);
		avl.add(5);
		assertFalse(avl.isEmpty());
	}
	
	@Test(timeout = 200)
	public void testSize() {
		avl.add(3);
		avl.add(6);
		avl.add(9);
		assertEquals(3, avl.size());
	}

	@Test(timeout = 200)
	public void testClear() {
		avl.clear();
		assertNull(avl.getRoot());
	}

    /*
     * Additional add/remove test cases
     * For AVL rotations
     */
	
	public void addRightHeavy() {
        // Left Rotation
	    avl.add(2);
	    avl.add(4);
	    avl.add(6);
	}
	
	@Test(timeout = 200)
	public void testRightHeavyAdd() {
    	addRightHeavy();

        // Data Checks
        assertEquals((Integer) 4, avl.getRoot().getData());
       	assertEquals((Integer) 6, avl.getRoot().getRight().getData());
        assertEquals((Integer) 2, avl.getRoot().getLeft().getData());

        // Height and Balance Factor Checks
        assertEquals(2, avl.getRoot().getHeight());
        assertEquals(0, avl.getRoot().getBalanceFactor());
        assertEquals(1, avl.getRoot().getRight().getHeight());
        assertEquals(0, avl.getRoot().getRight().getBalanceFactor());
        assertEquals(1, avl.getRoot().getLeft().getHeight());
        assertEquals(0, avl.getRoot().getLeft().getBalanceFactor());
    }
    public void addLeftHeavy() {
        // Right Rotation
        avl.add(6);
        avl.add(4);
        avl.add(2);
    }
    @Test(timeout = 200)
    public void testLeftHeavyAdd() {
        addLeftHeavy();

        // Data Checks
        assertEquals((Integer) 4, avl.getRoot().getData());
        assertEquals((Integer) 6, avl.getRoot().getRight().getData());
        assertEquals((Integer) 2, avl.getRoot().getLeft().getData());

        // Height and Balance Factor Checks
        assertEquals(2, avl.getRoot().getHeight());
        assertEquals(0, avl.getRoot().getBalanceFactor());
        assertEquals(1, avl.getRoot().getRight().getHeight());
        assertEquals(0, avl.getRoot().getRight().getBalanceFactor());
        assertEquals(1, avl.getRoot().getLeft().getHeight());
        assertEquals(0, avl.getRoot().getLeft().getBalanceFactor());
    }

    public void addRightLeft() {
        // Right-Left Rotation
        avl.add(3);
        avl.add(8);
        avl.add(7);
    }
    @Test(timeout = 200)
    public void testRightLeftAdd() {
        addRightLeft();

        // Data Checks
        assertEquals((Integer) 7, avl.getRoot().getData());
        assertEquals((Integer) 8, avl.getRoot().getRight().getData());
        assertEquals((Integer) 3, avl.getRoot().getLeft().getData());

        // Height and Balance Factor Checks
        assertEquals(2, avl.getRoot().getHeight());
        assertEquals(0, avl.getRoot().getBalanceFactor());
        assertEquals(1, avl.getRoot().getRight().getHeight());
        assertEquals(0, avl.getRoot().getRight().getBalanceFactor());
        assertEquals(1, avl.getRoot().getLeft().getHeight());
        assertEquals(0, avl.getRoot().getLeft().getBalanceFactor());
    }
    public void addLeftRight() {
        // Left Right Rotation
        avl.add(8);
        avl.add(3);
        avl.add(7);
    }
    @Test(timeout = 200)
    public void testLeftRightAdd() {
        addLeftRight();

        // Data Checks
        assertEquals((Integer) 7, avl.getRoot().getData());
        assertEquals((Integer) 8, avl.getRoot().getRight().getData());
        assertEquals((Integer) 3, avl.getRoot().getLeft().getData());

        // Height and Balance Factor Checks
        assertEquals(2, avl.getRoot().getHeight());
        assertEquals(0, avl.getRoot().getBalanceFactor());
        assertEquals(1, avl.getRoot().getRight().getHeight());
        assertEquals(0, avl.getRoot().getRight().getBalanceFactor());
        assertEquals(1, avl.getRoot().getLeft().getHeight());
        assertEquals(0, avl.getRoot().getLeft().getBalanceFactor());
    }
    public void addForRemoveLeftRight() {
        // Adds a balanced tree to unbalance
        // by remove for a left-right rotation
        avl.add(8);
        avl.add(6);
        avl.add(10);
        avl.add(7);
    }
    @Test(timeout = 200)
    public void testRemoveLeftRight() {
        addForRemoveLeftRight();

        // Data Checks
        assertEquals((Integer) 8, avl.getRoot().getData());
        assertEquals((Integer) 10, avl.getRoot().getRight().getData());
        assertEquals((Integer) 7, avl.getRoot().getLeft().getRight().getData());
        assertEquals((Integer) 6, avl.getRoot().getLeft().getData());

        // Height and Balance Factor Checks
        assertEquals(3, avl.getRoot().getHeight());
        assertEquals(1, avl.getRoot().getBalanceFactor());
        assertEquals(1, avl.getRoot().getRight().getHeight());
        assertEquals(0, avl.getRoot().getRight().getBalanceFactor());
        assertEquals(1, avl.getRoot().getLeft().getRight().getHeight());
        assertEquals(0, avl.getRoot().getLeft().getRight().getBalanceFactor());
        assertEquals(2, avl.getRoot().getLeft().getHeight());
        assertEquals(-1, avl.getRoot().getLeft().getBalanceFactor());

        // Remove
        assertEquals((Integer) 10, avl.remove(10));

        // Data Checks
        assertEquals((Integer) 7, avl.getRoot().getData());
        assertEquals((Integer) 8, avl.getRoot().getRight().getData());
        assertEquals((Integer) 6, avl.getRoot().getLeft().getData());

        // Height and Balance Factor Checks
        assertEquals(2, avl.getRoot().getHeight());
        assertEquals(0, avl.getRoot().getBalanceFactor());
        assertEquals(1, avl.getRoot().getRight().getHeight());
        assertEquals(0, avl.getRoot().getRight().getBalanceFactor());
        assertEquals(1, avl.getRoot().getLeft().getHeight());
        assertEquals(0, avl.getRoot().getLeft().getBalanceFactor());
    }
    public void addForRemoveRightLeft() {
        // Adds a balanced tree to unbalance
        // by remove for a right-left rotation
        avl.add(8);
        avl.add(6);
        avl.add(10);
        avl.add(9);
    }
    @Test(timeout = 200)
    public void testRemoveRightLeft() {
        addForRemoveRightLeft();

        // Data Checks
        assertEquals((Integer) 8, avl.getRoot().getData());
        assertEquals((Integer) 10, avl.getRoot().getRight().getData());
        assertEquals((Integer) 6, avl.getRoot().getLeft().getData());
        assertEquals((Integer) 9, avl.getRoot().getRight().getLeft().getData());

        // Height and Balance Factor Checks
        assertEquals(3, avl.getRoot().getHeight());
        assertEquals(-1, avl.getRoot().getBalanceFactor());
        assertEquals(2, avl.getRoot().getRight().getHeight());
        assertEquals(1, avl.getRoot().getRight().getBalanceFactor());
        assertEquals(1, avl.getRoot().getRight().getLeft().getHeight());
        assertEquals(0, avl.getRoot().getRight().getLeft().getBalanceFactor());
        assertEquals(1, avl.getRoot().getLeft().getHeight());
        assertEquals(0, avl.getRoot().getLeft().getBalanceFactor());

        // Remove
        assertEquals((Integer) 6, avl.remove(6));

        // Data Checks
        assertEquals((Integer) 9, avl.getRoot().getData());
        assertEquals((Integer) 10, avl.getRoot().getRight().getData());
        assertEquals((Integer) 8, avl.getRoot().getLeft().getData());

        // Height and Balance Factor Checks
        assertEquals(2, avl.getRoot().getHeight());
        assertEquals(0, avl.getRoot().getBalanceFactor());
        assertEquals(1, avl.getRoot().getRight().getHeight());
        assertEquals(0, avl.getRoot().getRight().getBalanceFactor());
        assertEquals(1, avl.getRoot().getLeft().getHeight());
        assertEquals(0, avl.getRoot().getLeft().getBalanceFactor());
    }
    @Test
    public void testOne() {
        avl.add(20);
        avl.add(4);
        avl.add(15);
        assertEquals((Integer) 15, avl.getRoot().getData());
        assertEquals((Integer) 4, avl.getRoot().getLeft().getData());
        assertEquals((Integer) 20, avl.getRoot().getRight().getData());
        assertEquals(2, avl.getRoot().getHeight());
        assertEquals(0, avl.getRoot().getBalanceFactor());
    }
    @Test
    public void testTwo() {
        avl.add(20);
        avl.add(4);
        avl.add(26);
        avl.add(3);
        avl.add(9);
        avl.add(15);
        assertEquals((Integer) 9, avl.getRoot().getData());
        assertEquals((Integer) 4, avl.getRoot().getLeft().getData());
        assertEquals((Integer) 20, avl.getRoot().getRight().getData());
        assertEquals(3, avl.getRoot().getHeight());
        assertEquals(0, avl.getRoot().getBalanceFactor());
    }
    @Test
    public void testThree() {
        avl.add(20);
        avl.add(4);
        avl.add(26);
        avl.add(3);
        avl.add(9);
        avl.add(21);
        avl.add(30);
        avl.add(2);
        avl.add(7);
        avl.add(11);
        avl.add(15);
        assertEquals((Integer) 9, avl.getRoot().getData());
        assertEquals((Integer) 4, avl.getRoot().getLeft().getData());
        assertEquals((Integer) 20, avl.getRoot().getRight().getData());
        assertEquals(4, avl.getRoot().getHeight());
        assertEquals(0, avl.getRoot().getBalanceFactor());
    }
    @Test
    public void testFour() {
        avl.add(20);
        avl.add(4);
        avl.add(8);
        assertEquals((Integer) 8, avl.getRoot().getData());
        assertEquals((Integer) 4, avl.getRoot().getLeft().getData());
        assertEquals((Integer) 20, avl.getRoot().getRight().getData());
    }
    @Test
    public void testFive() {
        avl.add(20);
        avl.add(4);
        avl.add(26);
        avl.add(3);
        avl.add(9);
        avl.add(8);
        assertEquals((Integer) 9, avl.getRoot().getData());
        assertEquals((Integer) 4, avl.getRoot().getLeft().getData());
        assertEquals((Integer) 20, avl.getRoot().getRight().getData());
        assertEquals(3, avl.getRoot().getHeight());
        assertEquals(0, avl.getRoot().getBalanceFactor());
    }
    @Test
    public void testSix() {
        avl.add(20);
        avl.add(4);
        avl.add(26);
        avl.add(3);
        avl.add(9);
        avl.add(21);
        avl.add(30);
        avl.add(2);
        avl.add(7);
        avl.add(11);
        avl.add(8);
        assertEquals((Integer) 9, avl.getRoot().getData());
        assertEquals((Integer) 4, avl.getRoot().getLeft().getData());
        assertEquals((Integer) 20, avl.getRoot().getRight().getData());
        assertEquals(4, avl.getRoot().getHeight());
        assertEquals(0, avl.getRoot().getBalanceFactor());
    }  
    @Test
    public void testSeven() {
        avl.add(2);
        avl.add(1);
        avl.add(4);
        avl.add(3);
        avl.add(5);
        avl.remove(1);
        assertEquals((Integer) 4, avl.getRoot().getData());
        assertEquals((Integer) 2, avl.getRoot().getLeft().getData());
        assertEquals((Integer) 5, avl.getRoot().getRight().getData());
        assertEquals(3, avl.getRoot().getHeight());
        assertEquals(1, avl.getRoot().getBalanceFactor());
    } 
    @Test
    public void testEight() {
        avl.add(6);
        avl.add(2);
        avl.add(9);
        avl.add(1);
        avl.add(4);
        avl.add(8);
        avl.add(14);
        avl.add(3);
        avl.add(5);
        avl.add(7);
        avl.add(10);
        avl.add(20);
        avl.add(44);
        avl.remove(1);
        assertEquals(5, avl.getRoot().getHeight());
        assertEquals(-1, avl.getRoot().getBalanceFactor());
    }
    @Test
    public void testNine() {
        avl.add(5);
        avl.add(2);
        avl.add(8);
        avl.add(1);
        avl.add(3);
        avl.add(7);
        avl.add(11);
        avl.add(4);
        avl.add(6);
        avl.add(9);
        avl.add(17);
        avl.add(20);
        avl.remove(1);
        assertEquals((Integer) 8, avl.getRoot().getData());
        assertEquals((Integer) 5, avl.getRoot().getLeft().getData());
        assertEquals((Integer) 11, avl.getRoot().getRight().getData());
        assertEquals(4, avl.getRoot().getHeight());
        assertEquals(0, avl.getRoot().getBalanceFactor());
    }  
}

