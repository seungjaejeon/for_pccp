# N진법 변환기
def int2N(num, N):
    if num < N:
        return num

    temp = ''
    while num:
        temp += str(num % N)
        num //= N

    return int(temp[::-1])
# 10진법 변환기
def N2int(num, N):
    if num == 0:  # 0일 경우 처리
        return 0
    if int(max(str(num))) >= N:
        return False

    temp = 0
    i = 0
    while num:
        temp += (num % 10) * N ** i
        i += 1
        num //= 10

    return temp


def isPossibleN(N, expression):
    exList = expression.split(' ')

    a = N2int(int(exList[0]), N)
    b = N2int(int(exList[2]), N)

    if exList[4] != 'X':
        c = N2int(int(exList[4]), N)
        if a is not False and b is not False and c is not False:
            if (exList[1] == '+' and a + b == c) or (exList[1] == '-' and a - b == c):
                return True
            else:
                return False
        else:
            return False
    else:
        return a is not False and b is not False

def getSolX(NList, solExpression):
    exList = solExpression.split(' ')
    cList = []

    for N in NList:
        a = N2int(int(exList[0]), N)
        b = N2int(int(exList[2]), N)

        if exList[1] == '+':
            c = int2N(a + b, N)
        else:
            c = int2N(a - b, N)

        if c not in cList:
            cList.append(c)

    if len(cList) == 1:
        exList[4] = str(cList[0])
    else:
        exList[4] = '?'

    return ' '.join(exList)

def solution(expressions):
    NList = [2, 3, 4, 5, 6, 7, 8, 9]

    for N in NList[:]:
        for expression in expressions:
            if not isPossibleN(N, expression):
                NList.remove(N)
                break

    solExpressions = []
    for expression in expressions:
        if expression[-1] == 'X':
            solExpressions.append(expression)

    for i in range(len(solExpressions)):
        solExpressions[i] = getSolX(NList, solExpressions[i])

    answer = solExpressions
    return answer  
