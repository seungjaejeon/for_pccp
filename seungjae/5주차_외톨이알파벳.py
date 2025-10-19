def solution(input_string):
    answer = ''
    alphabet = [[] for _ in range(97, 123)]
    l = len(input_string)

    if l == 1:
        return 'N'
    else:
        for i in range(l):
            alphabet[ord(input_string[i]) - 97].append(i)
    for i in range(26):
        if len(alphabet[i]) >= 2:
            for j in range(len(alphabet[i])-1):
                if alphabet[i][j+1] - alphabet[i][j] > 1:
                    answer += chr(i + 97)
                    break


    if answer == '':
        return 'N'
    else:
        return answer

