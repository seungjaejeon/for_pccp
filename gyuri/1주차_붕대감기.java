class Solution {
    /**
     * bandage : 시전 시간, 초당 회복량, 추가 회복량
     * health : 최대 체력
     * attacks : 공격 시간, 피해량
     */
    public int solution(int[] bandage, int health, int[][] attacks) {

        int maxHealth = health;
        int sec = 0;
        int cnt = 0;
        int attackCnt = 0;

        while(attackCnt < attacks.length) {
            sec++;

            int monsterAttackTime = attacks[attackCnt][0];

            if(sec == monsterAttackTime) { //몬스터가 공격했을 경우
                health -= attacks[attackCnt][1];

                cnt = 0; //연속공격 초기화
                attackCnt++; //공격횟수 추가

                if(health <= 0) return -1;

                continue;
            }

            cnt++; //연속 회복
            health += bandage[1]; //초당 회복

            if(cnt == bandage[0]) { //연속 회복에 성공하면
                health += bandage[2];
                cnt = 0;
            }

            if(health > maxHealth) { //최대 회복량을 벗어나면
                health = maxHealth;
            }
        }



        return health;
    }
}