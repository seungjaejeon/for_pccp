package PCCP_1_붕대감기;

public class Main {
    public static void main(String[] args) {
        //System.out.println(new Solution().solution(new int[]{5, 1, 5}, 30, new int[][]{{2, 10}, {9, 15}, {10, 5}, {11, 5}}));

        System.out.println(new Solution().solution(new int[]{3, 2, 7}, 20, new int[][]{{1, 15}, {5, 16}, {8, 6}}));
    }


    static class Solution {
        public int solution(int[] bandage, int health, int[][] attacks) {
        /*
        붕대감기는 t초동안 붕대를 감으면서 1초마다 x만큼의 체력을 회복
        t초 연속으로 붕대 감는 데 성공하면 y 만큼의 체력을 추가로 회복
        최대 체력이 존재해 현대 체력이 최대 체력보다 커지는 것은 불가능

        기술을 쓰는 도중 몬스터에게 공격을 당하면 기술이 취소, 공격을 당하는 순간에는 체력 회복 x
        몬스터에게 공격당해 기술이 취소 당하거나 기술이 끝나면 그 즉시 붕대 감기를 다시 사용하며, 연속 성공 시간이 0으로 초기화

        몬스터의 공격을 받으면 정해진 피해량만큼 현재 체력이 줄어든다
        이때, 현재 체력이 0 이하가 되면 캐릭터가 죽으며 더 이상 체력을 회복할 수 없다.

        bandage : 기술 시전 시간, 1초당 회복량, 추가 회복량
        health : 최대 체력
        attacks : 몬스터의 공격 시간과 피해량

        모든 공격이 끝난 직후 남은 체력을 return
        만약 몬스터의 공격을 받고 캐릭터의 체력이 0 이하가 되어 죽는다면 -1 return

        [5, 1, 5]	30	[[2, 10], [9, 15], [10, 5], [11, 5]]

        */


            int maxHealth = health;
            int targetHealTime = bandage[0];
            int healPower = bandage[1];
            int bonusHeal = bandage[2];
            int endTime = attacks[attacks.length - 1][0];
            int healingTime = 0;

            int monsterInt = 0;

            int monsterAttackTime = attacks[monsterInt][0];
            int monsterCnt = attacks.length - 1;
            int monsterPower = attacks[monsterInt][1];


            for (int i = 0; i <= endTime; i++) {
                if (i == monsterAttackTime) {
                    health -= monsterPower;

                    if (health <= 0) {
                        return -1;
                    }

                    monsterInt++;

                    if (monsterInt < attacks.length) {
                        monsterAttackTime = attacks[monsterInt][0];
                        monsterPower = attacks[monsterInt][1];
                    }


                    healingTime = 0;
                } else {

                    health += healPower;
                    healingTime++;

                    if (healingTime == targetHealTime) {
                        health += bonusHeal;
                        healingTime = 0;
                    }

                    if (health >= maxHealth) {
                        health = maxHealth;
                    }


                }




                System.out.println(i);
                System.out.println(health);
                System.out.println();
            }





            return health;
        }

    }
}

