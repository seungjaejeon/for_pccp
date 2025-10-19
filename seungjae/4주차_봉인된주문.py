from collections import defaultdict
import bisect

def solution(n, bans):
    # 거듭제곱 미리
    p26 = [1]
    for _ in range(11): p26.append(p26[-1]*26)

    # 삭제 목록 정리
    bans_by_len = defaultdict(list)
    for s in bans: bans_by_len[len(s)].append(s)
    for L in bans_by_len: bans_by_len[L].sort()

    # 누적합 계산
    cum = [0]*12
    for L in range(1,12): cum[L] = cum[L-1]+p26[L]
    bans_cum = [0]*12
    for L in range(1,12): bans_cum[L]=bans_cum[L-1]+len(bans_by_len.get(L,[]))

    # 길이 결정
    for L in range(1,12):
        if n <= cum[L]-bans_cum[L]:
            length=L; break
    n -= cum[length-1]-bans_cum[length-1]
    lst = bans_by_len.get(length,[])

    def remain(prefix):
        r=length-len(prefix)
        lo=prefix+'a'*r; hi=prefix+'z'*r
        b=bisect.bisect_right(lst,hi)-bisect.bisect_left(lst,lo)
        return p26[r]-b

    # n번째 문자열 조립
    res=''
    for _ in range(length):
        for c in range(26):
            ch=chr(97+c)
            cnt=remain(res+ch)
            if n>cnt: n-=cnt
            else: res+=ch; break
    return res
