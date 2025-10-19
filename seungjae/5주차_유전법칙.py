def solution(queries):
    def find_trait(n, p):
        if n == 1:  # 1세대는 항상 Rr
            return "Rr"

        parent_p = (p - 1) // 4 + 1   # 부모 인덱스
        child_idx = (p - 1) % 4 + 1   # 부모의 몇 번째 자식인지

        parent_trait = find_trait(n - 1, parent_p)

        if parent_trait == "RR":
            return "RR"
        elif parent_trait == "rr":
            return "rr"
        else:  # Rr
            if child_idx == 1:
                return "RR"
            elif child_idx in (2, 3):
                return "Rr"
            else:
                return "rr"

    return [find_trait(n, p) for n, p in queries]
