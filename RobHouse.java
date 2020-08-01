class RobHouse {
	public static void main(String[] args) {
	}

	private static int rob(int[] nums) {
		return rob(nums, nums.length -1);
	}

	private static int rob(int[] nums, int n) {
		if (n < 0) return 0;
		return Math.max(rob(nums, n - 2) + nums[n],
						rob(nums, n - 1));
	}

	int[] memo;
	private static int rob2(int[] nums) {
		memo = new int[nums.length + 1];
		Arrays.fill(memo, -1);
		return robRecurse(nums, nums.length -1);
	}
	
	private static int robRecurse(int[] nums, int n) {
		if (n < 0) return 0;
		if (memo[n] >= 0) return memo[n];
		int result = Math.max(robRecurse(nums, n - 2) + nums[n], robRecurse(nums, n -1));
		memo[n] = result;
		return result;
	}

	private static int robIterative(int[] nums) {
		if(nums.length == 0) return 0;

		int memo[] = new int[nums.length+1];
		memo[0]=0;
		memo[1]=nums[0];
		for (int i = 1; i < nums.length; i++) {
			int val = nums[i];
			// set next memo value to be max of current memo 
			// val and val from 2 index behind plus current val
			memo[i+1] = Math.max(memo[i], memo[i-1]+val);
		}
		return memo[nums.length];
	}

	private static int robFinal(int[] nums) {
		if (nums.length == 0) return 0;
		int prev1 = 0;
		int prev2 = 0;
		for (int num : nums) {
			int tmp = prev1;
			prev1 = Math.max(prev2 + num, prev1);
			prev2 = tmp;
		}
		return prev1;
	}
}
		
