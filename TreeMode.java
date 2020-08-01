import Tree.TreeNode;

public class TreeMode {
	Integer prev = null; // tree prev value
	int count = 1;      // current count value need to start at 1
	int max = 0;

	public int[] findMode(TreeNode root) {
		if (root == null) return new int[0];

		List<Integer> list = new ArrayList<>();

		traverse(root, list);

		int[] res = new int[list.size()];

		// convert list to array for result
		for (int i = 0; i < list.size(); ++i) res[i] = list.get(i);
		
		return res;
	}

	private void traverse(TreeNode root, List<Integer> list) {
		if (root == null) return;

		traverse(root.left, list);

		if (prev != null) {
			// only increment if cur val is same as prev val
			if (root.val == prev) count ++;
			// else reset count
			else count = 1;
		}

		// keep track of the max
		// add the value of current node if greater than max and clear / add to list
		// if there is a second mode also add to the list
		if (count > max) {
			max = count;
			list.clear();
			list.add(root.val);
		} else if (count == max) {
			list.add(root.val);
		}

		// set prev to root val before recurse on right tree
		prev = root.val;
		traverse(root.right, list);
	}
}
	
