import java.util.*;

class Problem {

    public static int[] solution(int[][] edges) {
        int[] answer = new int[4];

        Map<Integer, int[]> exchangeCnts = new HashMap<>();

        for (int[] edge : edges) {
            int a = edge[0], b = edge[1];

            exchangeCnts.putIfAbsent(a, new int[]{0, 0});
            exchangeCnts.putIfAbsent(b, new int[]{0, 0});

            exchangeCnts.get(a)[0] += 1;
            exchangeCnts.get(b)[1] += 1;
        }

        for (Map.Entry<Integer, int[]> entry : exchangeCnts.entrySet()) {
            int key = entry.getKey();
            int[] exchangeCnt = entry.getValue();

            if (exchangeCnt[0] >= 2 && exchangeCnt[1] == 0) {
                answer[0] = key;
            } else if (exchangeCnt[0] == 0 && exchangeCnt[1] > 0) {
                answer[2] += 1;
            } else if (exchangeCnt[0] >= 2 && exchangeCnt[1] >= 2) {
                answer[3] += 1;
            }
        }

        answer[1] = exchangeCnts.get(answer[0])[0] - answer[2] - answer[3];

        return answer;
    }

    public static void main(String[] args) {

        int[][] edges1 = {{2, 3}, {4, 3}, {1, 1}, {2, 1}};
        System.out.println(Arrays.toString(solution(edges1)));

        int[][] edges2 = {
            {4, 11}, {1, 12}, {8, 3}, {12, 7}, {4, 2}, {7, 11}, {4, 8}, {9, 6}, {10, 11},
            {6, 10}, {3, 5}, {11, 1}, {5, 3}, {11, 9}, {3, 8}
        };
        System.out.println(Arrays.toString(solution(edges2)));
    }
}
