
/* Yuyang Zhang
 * Dr. Andrew Steinberg
 * COP3503 Summer 2022
 * Programming Assignment 2
 */

class Node<T extends Comparable<T>, U>//generic node
{
	T name;
	U office;
	Node<T, U> parent;
	Node<T, U> left;
	Node<T, U> right;
	int color;
	
	public Node()//default constructor
	{
		this.name = null;
		this.office = null;
		this.parent = null;
		this.left = null;
		this.right = null;
		this.color = 0;
	}
	
	public Node(T name, U office)//constructor with data
	{
		this.name = name;
		this.office = office;
		this.parent = null;
		this.left = null;
		this.right = null;
		this.color = 0;
	}
}

public class AddressBookTree<T extends Comparable<T>, U>
{
	
	private Node<T, U> root;//root
	private Node<T, U> T;//nil
	
	public AddressBookTree()//set root and nil
	{
		T = new Node<T, U>();
	    root = T;
	}
	
	public void insert(T namein, U officein) 
	{
	    Node<T, U> node = new Node<T, U>(namein, officein);//create new node
	    node.left = T;
	    node.right = T;
	    node.color = 1;

	    Node<T, U> y = null;
	    Node<T, U> x = this.root;

	    while(x != T)
	    {
	    	y = x;//find parent
	    	if(node.name.compareTo(x.name) < 0)
	    		x = x.left;
	    	else
	    		x = x.right;
	    }

	    node.parent = y;
	    if (y == null)//determine where to insert node
	    	root = node;
	    else if (node.name.compareTo(y.name) < 0)
	      	y.left = node;
	    else
	    	y.right = node;

	    //skip insertfix
	    if(node.parent == null)
	    {
	    	node.color = 0;
	      	return;
	    }
	    if(node.parent.parent == null)
	    	return;

	    insertFix(node);
	 }
	
	public void insertFix(Node<T, U> x) 
	{
		Node<T, U> u;//uncle
	    while(x.parent.color == 1) 
	    {
	    	//uncle on left or right
	    	if(x.parent == x.parent.parent.right) 
	    	{
	    		u = x.parent.parent.left;
	    		if(u.color == 1)//case 1
	    		{
	    			u.color = 0;
	    			x.parent.color = 0;
	    			x.parent.parent.color = 1;
	    			x = x.parent.parent;
	    		}
	    		else//case 2 or 3
	    		{
	    			if (x == x.parent.left) 
	    			{
	    				x = x.parent;
	    				rightR(x);
	    			}
	    			x.parent.color = 0;
	    			x.parent.parent.color = 1;
	    			leftR(x.parent.parent);
	    		}
	    	} 
	    	else 
	    	{
	    		u = x.parent.parent.right;
	    		if(u.color == 1)//case 1
	    		{
	    			u.color = 0;
	    			x.parent.color = 0;
	    			x.parent.parent.color = 1;
	    			x = x.parent.parent;
	    		}
	    		else//case 2 or 3
	    		{
	    			if(x == x.parent.right) 
	    			{
	    				x = x.parent;
	    				leftR(x);
	    			}
	    			x.parent.color = 0;
	    			x.parent.parent.color = 1;
	    			rightR(x.parent.parent);
	    		}
	    	}
	    	if(x == root)
	    		break;
	    }
	    root.color = 0;
	 }
	
	public void deleteNode(T name)
	{
	    deleteNodeHelper(this.root, name);
	}
	
	public void deleteNodeHelper(Node<T, U> node, T namein) 
	{
		Node<T, U> z = T;
		Node<T, U> x, y;
		
		while(node != T)//find delete node
		{
			if(node.name.compareTo(namein) == 0)
				z = node;
			if(node.name.compareTo(namein) <= 0) 
				node = node.right;
			else
				node = node.left;
		}

		if(z == T)//delete name not in this tree
		{
			System.out.println("Couldn't find name in the tree!");
			return;
		}

		y = z;
		int yColor = y.color;//keep original color
		if(z.left == T)//left child is nil
		{
			x = z.right;
			Transplant(z, z.right);
		} 
		else if(z.right == T)//right child is nil 
		{
			x = z.left;
			Transplant(z, z.left);
		} 
		else//both children are node
		{
			y = minimum(z.right);//find successor
			yColor = y.color;
			x = y.right;
			
			//transplant in different situation
			if(y.parent == z)
				x.parent = y;
			else 
			{
				Transplant(y, y.right);
				y.right = z.right;
				y.right.parent = y;
			}
			Transplant(z, y);
			y.left = z.left;
			y.left.parent = y;
			y.color = z.color;//change y's color to make nodes above y not violate
		}
		if(yColor == 0)
			deleteFix(x);
	}
	
	public void deleteFix(Node<T, U> x) 
	{
	    Node<T, U> s;
	    while(x != root && x.color == 0)
	    {
	    	if(x == x.parent.left)//left side
	    	{
	    		s = x.parent.right;//sibling
	    		if(s.color == 1) //case 1
	    		{
	    			s.color = 0;
	    			x.parent.color = 1;
	    			leftR(x.parent);
	    			s = x.parent.right;
	    		}

	    		if(s.left.color == 0 && s.right.color == 0)//case 2
	    		{
	    			s.color = 1;
	    			x = x.parent;
	    		} 
	    		else 
	    		{
	    			if(s.right.color == 0)//case 3
	    			{
	    				s.left.color = 0;
	    				s.color = 1;
	    				rightR(s);
	    				s = x.parent.right;
	    			}

	    			s.color = x.parent.color;//case 4
	    			x.parent.color = 0;
	    			s.right.color = 0;
	    			leftR(x.parent);
	    			x = root;
	    		}
	    	} 
	    	else//right side
	    	{
	    		s = x.parent.left;//sibling
	    		if(s.color == 1)//case 1
	    		{
	    			s.color = 0;
	    			x.parent.color = 1;
	    			rightR(x.parent);
	    			s = x.parent.left;
	    		}

	    		if(s.right.color == 0 && s.right.color == 0)//case 2
	    		{
	    			s.color = 1;
	    			x = x.parent;
	    		} 
	    		else 
	    		{
	    			if(s.left.color == 0)//case 3
	    			{
	    				s.right.color = 0;
	    				s.color = 1;
	    				leftR(s);
	    				s = x.parent.left;
	    			}

	    			s.color = x.parent.color;//case 4
	    			x.parent.color = 0;
	    			s.left.color = 0;
	    			rightR(x.parent);
	    			x = root;
	    		}
	    	}
	    }
	    x.color = 0;
	 }

	 public void Transplant(Node<T, U> u, Node<T, U> v)//move the child of the original node to another node
	 {
		 if(u.parent == null)
			 root = v;
		 else if(u == u.parent.left)
			 u.parent.left = v;
		 else
			 u.parent.right = v;
		 v.parent = u.parent;
	 }
	
	 public void leftR(Node<T, U> x)//changes the structure in balancing itself by left rotating
	 {
		 Node<T, U> y = x.right;
		 x.right = y.left;//
		 if(y.left != T)
			 y.left.parent = x;

		 y.parent = x.parent;
		 if(x.parent == null)
			 this.root = y;
		 else if(x == x.parent.left)
			 x.parent.left = y;
		 else
			 x.parent.right = y;
		 y.left = x;
		 x.parent = y;
	  }
	
	public void rightR(Node<T, U> x)//changes the structure in balancing itself by right rotating
	{
	    Node<T, U> y = x.left;
	    x.left = y.right;
	    if(y.right != T)
	    	y.right.parent = x;
	    
	    y.parent = x.parent;
	    if(x.parent == null)
	    	this.root = y;
	    else if(x == x.parent.right)
	    	x.parent.right = y;
	    else
	    	x.parent.left = y;
	    
	    y.right = x;
	    x.parent = y;
	 }
	
	public void display() 
	{
		inOrderHelper(this.root);
	}
	
	public void inOrderHelper(Node<T, U> node)//inorder traverse tree and display 
	{
	    if(node != T)
	    {
	      inOrderHelper(node.left);
	      System.out.print(node.name + " " + node.office + "\n");
	      inOrderHelper(node.right);
	    }
	}
	
	public Node<T, U> minimum(Node<T, U> node)//most left node
	{
	    while(node.left != T)
	    	node = node.left;
	    return node;
	}
	
	public Node<T, U> maximum(Node<T, U> node)//most right node
	{
	    while(node.right != T)
	    	node = node.right;
	    return node;
	}
	
	public Node<T, U> successor(Node<T, U> x)//find successor by using minimum
	{
	    if(x.right != T)
	    	return minimum(x.right);

	    Node<T, U> y = x.parent;
	    while (y != T && x == y.right) 
	    {
	    	x = y;
	    	y = y.parent;
	    }
	    return y;
	 }
	
	public Node<T, U> predecessor(Node<T, U> x)//find predecessor by using maximum
	{
	    if(x.left != T)
	    	return maximum(x.left);

	    Node<T, U> y = x.parent;
	    while (y != T && x == y.left)
	    {
	    	x = y;
	    	y = y.parent;
	    }
	    return y;
	}
	
	public int countBlack(Node<T, U> node)//traverse tree by recursion and count black node
	{
		int count = 0;
	    if(node == null)
	        return 0;
	    count += countBlack(node.left);
	    count += countBlack(node.right);

	    if(node.color == 0 && node.name != null)
	        count++;
	    return count;
	}
	
	public int countRed(Node<T, U> node)//traverse tree by recursion and count red node
	{
		int count = 0;
	    if(node == null)
	        return 0;
	    count += countRed(node.left);
	    count += countRed(node.right);

	    if(node.color == 1)
	        count++;
	    return count;
	}
	
	public Node<T, U> getRoot() 
	{
	    return this.root;
	}
}
