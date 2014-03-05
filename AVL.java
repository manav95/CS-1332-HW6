import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
/**
 * This class is an implementation of an AVL tree. AVL trees are trees that balance
 * themselves.
 * @author Manav Dutta
 * @version 2.0
 */
public class AVL<T extends Comparable<? super T>> implements BSTInterface<T> {
	private Node<T> root;
	private int size;
	
	@Override
	/**
	 * This adds the data to the tree. After that, 
	 * the tree will rebalance itself. If the data
	 * is null, it throws an IllegalArgumentException
	 * @param data- the data to add to the tree
	 */
	public void add(T data) {
		if (data == null) {
            throw new IllegalArgumentException();
        }
        else if (contains(data)) {
            return;
        }
        else {
           if (root == null) {
               setRoot(new Node(data));
               root.setHeight(1);
               root.setBalanceFactor(0);
               rebalancer(root);
               size = size + 1;
           }
           else {
               recursiveInsertion(root, data); 
               root = rebalancer(root);
               calcHeightAndBF(root);
           }
        }
	}
	private void calcHeightAndBF(Node<T> node) {
	    int left = 0;
	    int right = 0;
	    if (node.getLeft() != null) {
	          left = node.getLeft().getHeight();
	    }
	    if (node.getRight() != null) {
              right = node.getRight().getHeight();
        }
        if (node.getRight() != null) {
                    right = node.getRight().getHeight();
        }
        if (left > right) {
           node.setHeight(left + 1);
        }
        else {
           node.setHeight(right + 1);
        }
        node.setBalanceFactor(left - right);
    }
    private void recursiveInsertion(Node<T> node, T data) {
        if (data.compareTo(node.getData()) < 0) {
            if (node.getLeft() == null) {
                node.setLeft(new Node(data));
                node.getLeft().setHeight(1);
                node.getLeft().setBalanceFactor(0);
                calcHeightAndBF(node);
                size = size + 1;
            }
            else {
                recursiveInsertion(node.getLeft(), data);
                calcHeightAndBF(node);
            }
        }
        else {
            if (node.getRight() == null) {
                node.setRight(new Node(data));
                node.getRight().setHeight(1);
                node.getRight().setBalanceFactor(0);
                calcHeightAndBF(node);
                size = size + 1;
            }
            else {
                recursiveInsertion(node.getRight(), data);
                calcHeightAndBF(node);
            }
        }
    }
	@Override
	/**
	 * Adds all the elements of the collection
	 * to the binary search tree.
	 * @param c- the collection of elements
	 */
	public void addAll(Collection<T> c) {
	   if (c == null) {
            throw new IllegalArgumentException();
       }
       for (T data : c) {
           if (data == null) {
               throw new IllegalArgumentException();
           }
           else {
               add(data);
           }
       }
	}

	@Override
	/**
	 * Removes the data from the tree and 
	 * rebalances as necessary.
	 * Throws IllegalArgumentException if the data is null.
	 * @param data- the data to be removed
	 * @return the data being removed
	 */
	public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }
        else if (root == null) {
            return null;
        }
        else if (!contains(data)) {
            return null;
        }
        else {
            ArrayList<Node<T>> array = new ArrayList<Node<T>>();
            T oldData = removeFinder(null, root, data, " ");
            calcHeightAndBF(root);
            root = rebalancer(root);
            calcHeightAndBF(root);
            return oldData;
        }
    }
    private T removeFinder(Node<T> parent, Node<T> node, T data, String direction) {
        if (node.getData().equals(data)) {
            if ((node.getLeft() == null && node.getRight() == null) && parent != null) {
                T oldData = node.getData();
                if (direction.equals("left")) {
                    parent.setLeft(null);
                }
                else {
                    parent.setRight(null);
                }
                node = null;
                size = size - 1;
                return oldData;
            }
            else if (node.getData().equals(root.getData()) && node.getLeft() == null) {
                root = node.getRight();
                T oldData = node.getData();
                node = null;
                size = size - 1;
                return oldData;
            }
            else if (node.getData().equals(root.getData()) && node.getRight() == null) {
                root = node.getLeft();
                T oldData = node.getData();
                node = null;
                size = size - 1;
                return oldData;
            }
            else if (node.getLeft() == null) {
                if (direction.equals("left")) {
                    parent.setLeft(node.getRight());
                    node = null;
                }
                else {
                    parent.setRight(node.getRight());
                    node = null;
                }
                size = size - 1;
                return data;
            }
            else if (node.getRight() == null) {
                if (direction.equals("left")) {
                    parent.setLeft(node.getLeft());
                    node = null;
                }
                else {
                    parent.setRight(node.getLeft());
                    node = null;
                }
                size = size - 1;
                return data;
            }
            else if (node.getData().equals(root.getData())) {
                Node<T> smallestRightSide = getSmallestRightSide(node.getRight());
                Node<T> oldRight = node.getRight();
                Node<T> oldLeft = node.getLeft();
                remove(smallestRightSide.getData());
                root = smallestRightSide;
                if (!(smallestRightSide.getData().equals(oldRight.getData()))) {
                    root.setRight(oldRight);
                }
                root.setLeft(oldLeft);
                return data;
            }
            else {
               Node<T> smallestRightSide = getSmallestRightSide(node.getRight());
               remove(smallestRightSide.getData());
               smallestRightSide.setLeft(node.getLeft());
               smallestRightSide.setRight(node.getRight());
               node = null;
               size = size - 1;
               return data;
            }
        }
        else {
            if (node.getData().compareTo(data) > 0) {
                T leftData = removeFinder(node, node.getLeft(), data, "left");
                if (node.getLeft() != null) {
                    calcHeightAndBF(node.getLeft());
                    node.setLeft(rebalancer(node.getLeft()));
                }
                else if (node.getRight() != null) {
                    calcHeightAndBF(node.getRight());
                    node.setRight(rebalancer(node.getRight()));
                }
                calcHeightAndBF(node);
                return leftData;
            }
            T rightData = removeFinder(node, node.getRight(), data, "right");
            if (node.getLeft() != null) {
                    calcHeightAndBF(node.getLeft());
                    node.setLeft(rebalancer(node.getLeft()));
            }
            else if (node.getRight() != null) {
                    calcHeightAndBF(node.getRight());
                    node.setRight(rebalancer(node.getRight()));
            }
            calcHeightAndBF(node);
            return rightData;
        }
      }
    private Node<T> getSmallestRightSide(Node<T> node) {
        if (node.getLeft() != null) {
            return getSmallestRightSide(node.getLeft());
        }
        else if (node.getRight() != null) {
            return getSmallestRightSide(node.getRight());
        }
        else {
           return node;
        }
    }
    private Node<T> getLargestLeftSide(Node<T> node) {
        if (node.getRight() != null) {
            return getLargestLeftSide(node.getRight());
        }
        else if (node.getLeft() != null) {
            return getLargestLeftSide(node.getLeft());
        }
        else {
           return node;
        }
    }

	@Override
	/**
	 * This gets the data from the tree.
	 * Throws IllegalArgumentException if the data is null.
	 * @param data- the data to be obtained
	 * @return the data to be obtained
	 */
	public T get(T data) {
		 if (data == null) {
            throw new IllegalArgumentException();
        }
        else if (!contains(data)) {
            return null;
        }
        else {
            return recursiveGetter(root, data);
        }
	}
    private T recursiveGetter(Node<T> node, T data) {
       if (node == null) {
           return null;
       }
       else if (node.getData().equals(data)) {
           return node.getData();
       }
       else if (node.getData().compareTo(data) > 0) {
           return recursiveGetter(node.getLeft(), data);
       }
       else {
           return recursiveGetter(node.getRight(), data);
       }
    }
	@Override
	/**
	 * This method returns if the tree contains
	 * the data.
	 * @param data- the data being checked
	 * @return the data the tree contains
	 */
	public boolean contains(T data) {
		if (data == null) {
            throw new IllegalArgumentException();
        }
        return recursiveFinder(root, data);
	}
    
	private boolean recursiveFinder(Node<T> node, T data) {
       if (node == null) {
           return false;
       }
       else if (node.getData().equals(data)) {
           return true;
       }
       else if (node.getData().compareTo(data) > 0) {
           return recursiveFinder(node.getLeft(), data);
       }
       else {
           return recursiveFinder(node.getRight(), data);
       }
    }
	@Override
	/**
	 * This method returns a list of nodes
	 * that result from a preorder traversal of the tree.
	 * @return the list of preordered nodes
	 */
	public List<T> preOrder() {
        List<T> list = new ArrayList<T>();
        return preOrderIterator(list, root);
    }
    private List<T> preOrderIterator(List<T> list, Node<T> node) {
        if (node == null) {
            return list;
        }
        else {
            list.add(node.getData());
            list = preOrderIterator(list, node.getLeft());
            list = preOrderIterator(list, node.getRight());
            return list;
        }
    }
	@Override
	/**
	 * This method returns a list of nodes 
	 * from an inorder traversal of the tree.
	 * @return the list of in order nodes
	 */
	public List<T> inOrder() {
        List<T> list = new ArrayList<T>();
        return inOrderIterator(list, root);
    }
    private List<T> inOrderIterator(List<T> list, Node<T> node) {
        if (node == null) {
            return list;
        }
        else {
            list = inOrderIterator(list, node.getLeft());
            list.add(node.getData());
            System.out.println(node);
            list = inOrderIterator(list, node.getRight());
            return list;
        }
    }
	@Override
	/**
	 * This method returns a list of nodes
	 * from a postorder traversal of the tree.
	 * @return the list of post order nodes
	 */
	public List<T> postOrder() {
        List<T> list = new ArrayList<T>();
        return postOrderIterator(list, root);
    }
    private List<T> postOrderIterator(List<T> list, Node<T> node) {
        if (node == null) {
            return list;
        }
        else {
            list = postOrderIterator(list, node.getLeft());
            list = postOrderIterator(list, node.getRight());
            list.add(node.getData());
            return list;
        }
    }
	@Override
	/**
	 * This method returns a list of nodes
	 * from a levelorder traversal of the tree.
	 * @return the list of level order nodes
	 */
	public List<T> levelOrder() {
        List<T> list = new ArrayList<T>();
        if (size == 0) {
            return list;
        }
        Queue<Node<T>> queue = new ConcurrentLinkedQueue<Node<T>>();
        queue.add(root);
        while (queue.peek() != null) {
            Node<T> node = queue.poll();
            list.add(node.getData());
            if (node.getLeft() != null) {
                queue.add(node.getLeft());
            }
            if (node.getRight() != null) {
                queue.add(node.getRight());
            }
        }
        return list;
    }

	@Override
	/**
	 * This method returns if the tree is empty.
	 * @return if the tree is empty
	 */
	public boolean isEmpty() {
		 return (size == 0);
	}

	@Override
	/**
	 * This method returns the size of the tree.
	 * @return the size of the tree
	 */
	public int size() {
		return size;
	}

	@Override
	/**
	 * This method clears the whole tree.
	 */
	public void clear() {
        root = null;
        size = 0;
    }
	private Node<T> rotateLeft(Node<T> unbalanced) {
		Node<T> c = unbalanced.getRight();
		unbalanced.setRight(c.getLeft());
		c.setLeft(unbalanced);
		calcHeightAndBF(unbalanced);
		return c;
	}
    private Node<T> rotateRight(Node<T> unbalanced) {
        Node<T> nodeC = unbalanced.getLeft();
        unbalanced.setLeft(nodeC.getRight());
        nodeC.setRight(unbalanced);
        calcHeightAndBF(unbalanced);
        return nodeC;
    }
    private Node<T> rotateLeftRight(Node<T> unbalanced) {
        Node<T> c = unbalanced.getLeft();
        Node<T> closseau = rotateLeft(c);
        unbalanced.setLeft(closseau);
        calcHeightAndBF(unbalanced);
        return rotateRight(unbalanced);
    }
    private Node<T> rotateRightLeft(Node<T> unbalanced) {
        Node<T> c = unbalanced.getRight();
        Node<T> rosseau = rotateRight(c);
        unbalanced.setRight(rosseau);
        calcHeightAndBF(unbalanced);
        return rotateLeft(unbalanced);
    }
    private Node<T> rebalancer(Node<T> node) {
        if (node.getBalanceFactor() > 1) {
            if (node.getLeft() != null && node.getLeft().getBalanceFactor() >= 0) {
                  return rotateRight(node);
            }
            else if (node.getLeft() != null && node.getLeft().getBalanceFactor() < 0) {
                  return rotateLeftRight(node);
            }
        }
        else if (node.getBalanceFactor() < -1) {
            if (node.getRight() != null && node.getRight().getBalanceFactor() <= 0) {
                    return rotateLeft(node);
            }
            else if (node.getRight() != null && node.getRight().getBalanceFactor() > 0) {
                    return rotateRightLeft(node);
            }
        }
        return node;
    }
    /**
     * This returns the root node of the tree.
     * @return the root
     */
	public Node<T> getRoot() {
		return root;
	}
    /**
     * This sets the root node of the tree.
     * @param root- the root node of the tree
     */
	public void setRoot(Node<T> root) {
		this.root = root;
	}
}
