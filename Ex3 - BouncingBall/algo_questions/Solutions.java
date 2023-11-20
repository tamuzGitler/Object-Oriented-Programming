package algo_questions;

import java.util.Arrays;

/**
 * Algorthim solution class.
 * this class computes 4 different solutions to 4 different problems.
 * @author Tamuz Gitler
 */
public class Solutions {

    /**
     * Method computing the maximal amount of tasks out of n tasks that can be completed with m time
     * slots.
     * A task can only be completed in a time slot if the length of the time slot is grater than the no.
     * of hours needed to complete the task.
     * Solution - Greedy Algorithm
     * @param tasks array of integers of length n.
     *             tasks[i] is the time in hours required to complete task i.
     * @param timeSlots array of integersof length m. timeSlots[i] is the length in hours of the slot i.
     * @return maximal amount of tasks that can be completed
     */
    public static int alotStudyTime(int[] tasks, int[] timeSlots){
        Arrays.sort(tasks);
        Arrays.sort(timeSlots);
        int accomplishedTaskCounter = 0;
        for (int timeOfTask : tasks){
            for (int i = 0; i < timeSlots.length; i++){
                if (timeOfTask <= timeSlots[i]){
                    accomplishedTaskCounter++;
                    timeSlots[i] = -1;
                    break;
                }
            }
        }
        return accomplishedTaskCounter;
    }

    /**
     *Method computing the nim amount of leaps a frog needs to jumb across n waterlily leaves,
     *  from leaf 1 to leaf n.
     *  The leaves vary in size and how stable they are, so some leaves allow larger leaps than others.
     *  leapNum[i] is an integer telling you how many leaves ahead you can jump from leaf i.
     *  If leapNum[3]=4, the frog can jump from leaf 3, and land on any of the leaves 4, 5, 6 or 7.
     *  Solution - Greedy Algorithm
     * @param leapNum  array of ints. leapNum[i] is how many leaves ahead you can jump from leaf i.
     * @return minimal no. of leaps to last leaf.
     */
    public static int minLeap(int[] leapNum){
        if (leapNum.length == 0 || leapNum.length == 1){
            return 0;
        }
        int leafUsed = 1;
        int indexOfLeaf = 0;
        while(indexOfLeaf + leapNum[indexOfLeaf] < leapNum.length - 1){
            /* checks if it is possible to leap from current leaf*/
            if (leapNum[indexOfLeaf] == 0){
                return -1;
            }
            indexOfLeaf = findMaxLeap(indexOfLeaf, leapNum[indexOfLeaf] + indexOfLeaf, leapNum);
            leafUsed ++;
        }
        return leafUsed ;
    }

    /**
     * Finds the best leaf to leap to from start leaf till end leaf in leapNum array
     * Solution - Dynamic Programing Algorithm
     * @param start leaf to leap from
     * @param end last leap that is possible to reach to
     * @param leapNum array of ints. leapNum[i] is how many leaves ahead you can jump from leaf i.
     * @return best leaf to leap to
     */
    private static int findMaxLeap(int start, int end, int[] leapNum){
        int maxLeafValue = 0;
        int indexOfLeaf = 0;
        for (int j = start; j <= end; j++){
            int curLeafValue = leapNum[j] + j;
            if( maxLeafValue < j + leapNum[j]){
                maxLeafValue = curLeafValue;
                indexOfLeaf = j;
            }
        }
        return indexOfLeaf;
    }
    /**
     * Method computing the solution to the following problem:
     * A boy is filling the water trough for his father's cows in their village.
     * The trough holds n liters of water. With every trip to the village well,
     * he can return using either the 2 bucket yoke, or simply with a single bucket.
     * A bucket holds 1 liter. In how many different ways can he fill the water trough?
     * n can be assumed to be greater or equal to 0, less than or equal to 48.
     * Solution - Dynamic Programing Algorithm
     * @param n liters of water
     * @return valid output of algorithm.
     */
    public static int bucketWalk(int n){
        if (n == 0 || n ==1){
            return 1;
        }
        int[] grid = new int[n+1];

        /* init base case */
        grid[0] = 1;
        grid[1] = 1; // carries with 1 bucket
        grid[2] = 2; // can carry 2 buckets together, or carry 2 buckets separate

        /* fills the grid */
        for(int i = 2; i < n+1; i++){
            grid[i] = grid[i-2] + grid[i-1];
        }
        return grid[n];

    }

    /**
     * Method computing the solution to the following problem: Given an integer n,
     * return the number of structurally unique BST's (binary search trees)
     * which has exactly n nodes of unique values from 1 to n.
     * You can assume n is at least 1 and at most 19.
     * (Definition: two trees S and T are structurally distinct if one can not be obtained
     * from the other by renaming of the nodes.) (credit: LeetCode)
     * Solution - Dynamic Programing Algorithm
     * @param n nodes of unique values from 1 to n
     * @return valid output of algorithm.
     */
    public static int numTrees(int n)
    {
        int[] uniqueBst = new int[n+1]; //default value are 0
        /* init base case */
        uniqueBst[0] = 1;
        uniqueBst[1] = 1;

        /* fills the grid */
        for (int i = 2; i < n + 1; i++)
        {
            for (int j = 0; j < i; j++){
                uniqueBst[i] += uniqueBst[j]*uniqueBst[i-j-1];
            }
        }
        return uniqueBst[n];
    }
}
