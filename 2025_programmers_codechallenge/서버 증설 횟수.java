class Solution {
    public int solution(int[] players, int m, int k) {
        int n = players.length;
        int[] added = new int[n];
        int totalAdditions = 0;
        
        for (int i = 0; i < n; i++) {
            int active = 0;
            for (int j = Math.max(0, i - k + 1); j <= i; j++) {
                active += added[j];
            }
            
            int required = players[i] / m;
            
            if (active < required) {
                int need = required - active;
                added[i] = need;
                totalAdditions += need;
            }
        }
        
        return totalAdditions;
    }
}
