import java.util.*;

class Solution {
    public int[] solution(int[][] dice) {
        int n = dice.length;
        int m = n / 2;
        List<List<Integer>> combinations = new ArrayList<>();
        combine(0, n, m, new ArrayList<>(), combinations);
        
        int halfSize = combinations.size() / 2;
        long bestWinCount = -1;
        List<Integer> bestCombination = null;

        for (int i = 0; i < halfSize; i++) {
            List<Integer> comb = combinations.get(i);
            
            Set<Integer> chosen = new HashSet<>(comb);
            List<Integer> complement = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                if (!chosen.contains(j)) {
                    complement.add(j);
                }
            }

            Map<Integer, Long> distA = getDistribution(dice, comb);
            Map<Integer, Long> distB = getDistribution(dice, complement);

            long winCount = getWinCount(distA, distB);
            long loseCount = getWinCount(distB, distA);

            if (winCount > loseCount) {
                if (winCount > bestWinCount) {
                    bestWinCount = winCount;
                    bestCombination = comb;
                }
            } else {
                if (loseCount > bestWinCount) {
                    bestWinCount = loseCount;
                    bestCombination = complement;
                }
            }
        }

        Collections.sort(bestCombination);
        int[] result = new int[bestCombination.size()];
        for (int i = 0; i < bestCombination.size(); i++) {
            result[i] = bestCombination.get(i) + 1;
        }
        return result;
    }
    
    private void combine(int start, int n, int m, List<Integer> current, List<List<Integer>> results) {
        if (current.size() == m) {
            results.add(new ArrayList<>(current));
            return;
        }
        for (int i = start; i < n; i++) {
            current.add(i);
            combine(i + 1, n, m, current, results);
            current.remove(current.size() - 1);
        }
    }
    
    private Map<Integer, Long> getDistribution(int[][] dice, List<Integer> indices) {
        Map<Integer, Long> distribution = new HashMap<>();
        distribution.put(0, 1L);
        
        for (int index : indices) {
            Map<Integer, Long> newDistribution = new HashMap<>();
            for (Map.Entry<Integer, Long> entry : distribution.entrySet()) {
                int currentSum = entry.getKey();
                long ways = entry.getValue();
                for (int face : dice[index]) {
                    int newSum = currentSum + face;
                    newDistribution.put(newSum, newDistribution.getOrDefault(newSum, 0L) + ways);
                }
            }
            distribution = newDistribution;
        }
        return distribution;
    }
    
    private long getWinCount(Map<Integer, Long> distA, Map<Integer, Long> distB) {
        long winCount = 0;
        for (Map.Entry<Integer, Long> entryA : distA.entrySet()) {
            int sumA = entryA.getKey();
            long waysA = entryA.getValue();
            for (Map.Entry<Integer, Long> entryB : distB.entrySet()) {
                int sumB = entryB.getKey();
                if (sumA > sumB) {
                    winCount += waysA * entryB.getValue();
                }
            }
        }
        return winCount;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        int[][] dice1 = {
            {1, 2, 3, 4, 5, 6},
            {3, 3, 3, 3, 4, 4},
            {1, 3, 3, 4, 4, 4},
            {1, 1, 4, 4, 5, 5}
        };
        System.out.println(Arrays.toString(sol.solution(dice1)));
        
        int[][] dice2 = {
            {1, 2, 3, 4, 5, 6},
            {2, 2, 4, 4, 6, 6}
        };
        System.out.println(Arrays.toString(sol.solution(dice2)));
        
        int[][] dice3 = {
            {40, 41, 42, 43, 44, 45},
            {43, 43, 42, 42, 41, 41},
            {1, 1, 80, 80, 80, 80},
            {70, 70, 1, 1, 70, 70}
        };
        System.out.println(Arrays.toString(sol.solution(dice3)));
    }
}
