class Solution {
    public int solution(int[][] info, int n, int m) {
        int len = info.length;
        boolean[][][] dp = new boolean[len + 1][n][m];
        dp[0][0][0] = true;
        
        for (int i = 0; i < len; i++) {
            int costA = info[i][0];
            int costB = info[i][1];
            for (int a = 0; a < n; a++) {
                for (int b = 0; b < m; b++) {
                    if (!dp[i][a][b]) continue;
                    if (a + costA < n) {
                        dp[i + 1][a + costA][b] = true;
                    }
                    if (b + costB < m) {
                        dp[i + 1][a][b + costB] = true;
                    }
                }
            }
        }
        
        int answer = Integer.MAX_VALUE;
        for (int a = 0; a < n; a++) {
            for (int b = 0; b < m; b++) {
                if (dp[len][a][b]) {
                    answer = Math.min(answer, a);
                }
            }
        }
        return answer == Integer.MAX_VALUE ? -1 : answer;
    }
}