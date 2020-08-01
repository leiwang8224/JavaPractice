// print the tree traversal reverse direction
import java.util.*;
import Tree.TreeNode;
class Practice {

	public static void main(String[] args) {
		System.out.println("Hello world");
		//TreeNode head = new TreeNode(0);
		//head.left = new TreeNode(1);
		//head.right = new TreeNode(2);
		//levelOrderTraversal(head);
		shiftLeftOnZero();
		System.out.println("updateBit result = " + updateBit(6,2,false));
		System.out.println(printBinary(6));
		System.out.println(parity(3));
		System.out.println("before " + printBinary(5) + " after " + printBinary(clearBits0ThruI(5,1)));
		System.out.println("before " + printBinary(5) + " after " + printBinary(clearBitsMSBThruI(5,1)));
		System.out.println("before " + printBinary(5) + " after " + getBit(5,3));
		System.out.println("swapBits before " + printBinary(5) + " after " + printBinary(swapBits(5,0,2)));
		System.out.println("reverseBits before " + printBinary(10) + " after " + printBinary(reverseBits(10)));
		System.out.println("decodeAtIndex " + decodeAtIndex("scat4or3", 17));
	}
	
	private static void shiftLeftOnZero() {
		int num = 0;
		for (int i = 0; i < 32; i ++) {
			num = num << 1;
			if (num < 1) num = num + 1;
			System.out.println(num);
		}
		return;
	}

	private void pushTreeValStack(TreeNode root, int level, Stack<Integer> s) {
		if (root == null) return;
		if (level == 1) s.push(root.val);
		else if (level > 1) {
			pushTreeValStack(root.left, level-1, s);
			pushTreeValStack(root.right, level-1, s);
		} 
	}

	private List<List<Integer>> levelOrderBottom(TreeNode root) {
		List<List<Integer>> list = new ArrayList<>();
		int h = height(root);
		
		Stack<Integer> s = new Stack<>();
		pushTreeValStack(root, h, s);
			
			
		
		return list;
	}

	private int height(TreeNode root) {
		if (root == null) {
			return 0;
		} else {
			int leftHeight = height(root.left);
			int rightHeight = height(root.right);

			// use larger one
			if (leftHeight > rightHeight) return leftHeight + 1;
			else return rightHeight + 1;
		}
	}

	private static void levelOrderTraversal(TreeNode root) {
		if (root == null) return;
	
		Queue<TreeNode> queue = new LinkedList<>();
		queue.add(root);

		TreeNode curr = null;

		while (!queue.isEmpty()) {
			curr = queue.poll();
			
			if (curr.left != null) {
				queue.add(curr.left);
			}

			if (curr.right != null) {
				queue.add(curr.right);
			}
		}
	}

	// this traversal method does not use extra memory for traversal
	// however it does write to the tree so the tree is modified
	// and reverted to its original structure in the end
	private static void morrisTraversalPreOrder(TreeNode node) {
		while (node != null) {
			// if left child is null, print the current node, move to right child
			if (node.left == null) {
				System.out.println(node.val);
				node = node.right;
			} else {
				// find inorder predecessor
				TreeNode cur = node.left;
				// move all the way to the rightmost children (cur ptr)
				while (cur.right != null && cur.right != node) {
					cur = cur.right;
				}

				// if the right child of inorder predecessor
				// already points to this node
				if (cur.right == node) {
					cur.right = null;
					node = node.right;
				}

				// if right child doesn't point to this node, then print
				// this node and make right child point to this node
				else {
					System.out.println(node.val);
					cur.right = node;
					node = node.left;
				}
			}
		}
	}
	
	private static void morris(TreeNode root) {
		TreeNode cur, prev;

		if (root == null) return;
		
		cur = root;
		while (cur != null) {
			if (cur.left == null) {
				System.out.print(cur.val);
				cur = cur.right;	
			} else {
				// find the previous of cur
				prev = cur.left;
				// traverse to the rightmost node of the left tree
				while (prev.right != null && prev.right != cur)
					prev = prev.right;
				
				// make cur as right child of its prev
				if (prev.right == null) {
					prev.right = cur;
					cur = cur.left;
				}

				//fix the right child of prev
				else {
					prev.right = null;					
					System.out.print(cur.val);
					cur = cur.right;
				}
			}
		}
	}

	private static int updateBit(int n, int pos, boolean bitChange) {
		int value = bitChange ? 1 : 0;  // translate boolean to integer
		System.out.println("updateBit(), value = " + printBinary(value));
		int mask = 1 << pos;			// mask with the pos
		System.out.println("updateBit(), mask = " + printBinary(mask));
		System.out.println("updateBit()," + printBinary( (n&mask))+ ", number = " + printBinary(n));
		return (n & ~mask) | ((value << pos) & mask);  // unset the bit with mask and set with new val
	}

	private static String printBinary(int number) {
	    boolean signbit = true;	
		if (number < 0) signbit = false; 
		StringBuilder sb = new StringBuilder();
	    if (number == 0) {
			return "0";
		}	
		Stack<Integer> str = new Stack<>();
		while (Math.abs(number) > 0) {
			str.push(Math.abs(number % 2));
			number = number / 2;
		}

		if (!signbit) sb.append("1");
		while (!str.empty()) {
			sb.append(str.pop());
		}
		return sb.toString();
	}
		
	// O(k)
	private static short parity(long x) {
		short result = 0;
		// idea is to bit XOR each other and accumulate
		// b(0) XOR b(1) XOR b(2) ...
		while (x != 0) {
			result ^= 1;	// result only updates to 1 when different from 1
			x &= (x - 1);	// move bits to the left
		}
		return result;
	}

	// O(n)
	private static short parity2(long x) {
		short result = 0;
		while (x != 0) {
			result ^= (x & 1);
			x >>>= 1;
		}
		return result;
	}

	private static int clearBits0ThruI(int number, int pos) {
		// starting from LSB use -1 as mask and +1 to pos
		int mask = number & (-1 << (pos + 1));
		System.out.println("clearBits0ThruI mask = " + printBinary(mask)); 
		return number & (-1 <<(pos + 1));
	}

	private static int clearBitsMSBThruI(int number, int pos) {
		// starting from MSB use 1 as mask and -1 to pos
		int mask = (1 << pos) - 1;
		System.out.println("clearBitsMSBThruI mask = " + printBinary(mask));
		return number & ((1 << pos) - 1);
	}

	private static boolean getBit(int number, int pos) {
		return (number & (1 << pos ))!= 0;	
	}

	private static int clearBit(int number, int pos) {
		return number & ~(1 << pos);
	}
	
	private static int swapBits(int number, int i, int j) {
		if (((number >>> i) & 1) != ((number >>> j) & 1)) {
			int bitMask = (1 << i) | (1 << j);
			number ^= bitMask;
		}
		return number;
	} 

	private static int setBit(int number, int pos) {
		return number & (1<<pos);
	}

	private static int reverseBits(int number) {
		int rev = 0;
		while (number > 0) {
			rev <<= 1; // accumulation in rev by shifting left
			if ((int)(number & 1) == 1) // if last digit is 1, XOR with 1 to toggle it (0)
				rev ^= 1;
			number >>= 1; // extract digits from number by shifting right
		}
		return rev;
	}
	
	private static double power(double x, int y) {
		long result = 1L;
		long power = y;
		if (y < 0) {
			power = -power;
			x = 1.0 / x;
		}

		while (power != 0) {
			if ((power & 1) != 0) {
				result *= x;
			}
			x *= x;		
			power >>>= 1;
		}
		return result;
	}

	private static boolean isPalindrome(int x) {
		if (x < 0) return false;
		final int numDigits = (int)(Math.floor(Math.log10(x))) + 1;
		int msdMask = (int)Math.pow(10, numDigits -1 ); // extract MSD
		for (int i = 0; i < (numDigits / 2); ++i) { // iterate through half of the digits
			if (x / msdMask != x % 10) {   // extract MSD and LSD and compare
				return false;
			}
			x %= msdMask;	// remove the most significant digit of x
			x /= 10; // remove the least significant digit of x
			msdMask /= 100;  // since LSD and MSD are removed, move left 2 digits
		}
		return true;			
	}

	private static String decodeAtIndex(String S, int K) {
		long charCount = 0L;
		int charIndex;
		char[] chs = S.toCharArray();
		for (charIndex = 0; charCount < K; charIndex++) {
			if (chs[charIndex] >= '0' && chs[charIndex] <= '9') {
				charCount = charCount * (chs[charIndex] - '0');
				System.out.println("charCount processing = " + charCount);
			} else {
				charCount = charCount + 1;
			}
		}
		System.out.println("charCount = " + charCount + " charIndex = "+ charIndex + " K = " + K);
		charIndex++;
		// loop going backwards
		while (true) {
			if (chs[charIndex] >= '0' && chs[charIndex] <= '9') {
				charCount = charCount / (chs[charIndex] - '0');
				K = K % (int)charCount;
				System.out.println("going backwards hitting a number K = " + K + " charCount = " + charCount + "charIndex = " + charIndex);
			} else if (K % charCount == 0) {
				return "" + chs[charIndex];
			} else {
				System.out.println("going backwards hitting a letter K = " + K + " charCount = " + charCount + "charIndex = " + charIndex);
				charCount--;
			}
		}
		
	}
				
}
