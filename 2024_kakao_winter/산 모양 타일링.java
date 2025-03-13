import java.util.*;

class GiftPrediction {
    public static void main(String[] args) {
        String[] friends1 = { "muzi", "ryan", "frodo", "neo" };
        String[] gifts1 = { "muzi frodo", "muzi frodo", "ryan muzi", "ryan muzi", "ryan muzi", "frodo muzi",
                "frodo ryan", "neo muzi" };
        System.out.println(solution(friends1, gifts1)); // Output: 2

        String[] friends2 = { "joy", "brad", "alessandro", "conan", "david" };
        String[] gifts2 = { "alessandro brad", "alessandro joy", "alessandro conan", "david alessandro",
                "alessandro david" };
        System.out.println(solution(friends2, gifts2)); // Output: 4

        String[] friends3 = { "a", "b", "c" };
        String[] gifts3 = { "a b", "b a", "c a", "a c", "a c", "c a" };
        System.out.println(solution(friends3, gifts3)); // Output: 0
    }

    public static int solution(String[] friends, String[] gifts) {
        int n = friends.length;

        Map<String, Map<String, Integer>> giftMap = new HashMap<>();
        Map<String, Integer> givenGifts = new HashMap<>(), receivedGifts = new HashMap<>();
        for (String f : friends) {
            giftMap.put(f, new HashMap<>());
            givenGifts.put(f, 0);
            receivedGifts.put(f, 0);
        }

        for (String gift : gifts) {
            String[] parts = gift.split(" ");
            String giver = parts[0], receiver = parts[1];
            giftMap.get(giver).put(receiver, giftMap.get(giver).getOrDefault(receiver, 0) + 1);
            givenGifts.put(giver, givenGifts.get(giver) + 1);
            receivedGifts.put(receiver, receivedGifts.get(receiver) + 1);
        }

        Map<String, Integer> giftScores = new HashMap<>();
        for (String f : friends) {
            giftScores.put(f, givenGifts.get(f) - receivedGifts.get(f));
        }

        Map<String, Integer> nextMonthGifts = new HashMap<>();
        for (String f : friends)
            nextMonthGifts.put(f, 0);

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                String f1 = friends[i], f2 = friends[j];
                int f1ToF2 = giftMap.get(f1).getOrDefault(f2, 0);
                int f2ToF1 = giftMap.get(f2).getOrDefault(f1, 0);
                if (f1ToF2 > f2ToF1) {
                    nextMonthGifts.put(f1, nextMonthGifts.get(f1) + 1);
                } else if (f1ToF2 < f2ToF1) {
                    nextMonthGifts.put(f2, nextMonthGifts.get(f2) + 1);
                } else {
                    if (giftScores.get(f1) > giftScores.get(f2)) {
                        nextMonthGifts.put(f1, nextMonthGifts.get(f1) + 1);
                    } else if (giftScores.get(f1) < giftScores.get(f2)) {
                        nextMonthGifts.put(f2, nextMonthGifts.get(f2) + 1);
                    }
                }
            }
        }

        return nextMonthGifts.values().stream().max(Integer::compare).orElse(0);
    }
}