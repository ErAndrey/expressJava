package new_task.task_8;

public class Main {
    public int[] twoSum(int[] nums, int target) {
        int[] res = {};
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums.length; j++) {
                if (i == j) continue;
                if (nums[i] + nums[j] == target)  {
                    return new int[]{i, j};
                }
            }
        }
        return res;
    }

    // [2,7,11,15], target = 9

}
