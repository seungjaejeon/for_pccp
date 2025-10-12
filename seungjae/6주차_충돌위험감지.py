from typing import Tuple, Union, List
from collections import Counter 

class Robot:
    def __init__(self, points):
        # 자신이 갈 곳들을 좌표로 저장
        self.points: List[List[int, int]] = points

        # meta
        self.progress = 0 # 로봇은 p -> p+1 좌표로 이동 중
        self.n = len(points) - 1 # 최대 p
        self.pos: List[int, int] = points[0] # 시작 좌표

    @property
    def active(self) -> bool:
        # 나에게 갈 목표가 있는가?
        return self.progress < self.n

    def move(self):
        # 목표를 향해 이동 시도
        if not self.active:
            return

        # 문제에서 제시한 우선순위에 따라 좌표 변경
        x_t, y_t = self.points[self.progress+1]
        x, y = self.pos

        if x_t < x:
            self.pos[0] -= 1
        elif x < x_t:
            self.pos[0] += 1
        elif y_t < y:
            self.pos[1] -= 1
        elif y < y_t:
            self.pos[1] += 1

        # 도착했으면 다음 목표로 전환
        if self.pos == self.points[self.progress+1]:
            self.progress += 1


def solution(points, routes):
    t = 0
    answer = 0
    robots: list[Robot] = []

    for route in routes:
        # Robot 객체에 굳이 routes를 안 실어도 되게
        # 미리 points로 변환해서 __init__에 전달
        # [4,2] -> [[1, 4], [6, 4]]
        p = list()
        for idx in route:
            x, y = points[idx - 1]
            p.append([x, y])

        robots.append(Robot(p))

    # 움직일 수 있는 로봇이 남아있는 동안 반복
    while robots:
        # 1. 현재 로봇들의 좌표를 수집
        c = Counter()
        for robot in robots:
            c[tuple(robot.pos)] += 1 # list는 hashable하지 않으니 tuple로 변환

        # 2개 이상의 로봇이 있는 좌표의 수를 찾고 answer에 더함
        for k, v in c.items():
            if v >= 2:
                answer += 1

        # 2. 목표를 이룬 로봇들은 갈 곳이 없으니 제거
        robots = [r for r in robots if r.active]

        # 3. 모든 로봇들에게 이동 명령
        for robot in robots:
            result = robot.move()

    return answer
