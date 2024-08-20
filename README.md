
# 정규식 문법 기호 모음
## 정규식 기본 기호
### . 임의의 문자를 의미한다.
### ^ 시작을 의미한다. []안에 있으면 일치하지않는 부정을 의미한다. 
  * ^a: a로 시작하는 단어
  * [^a]: a가 아닌 문자 1개
### $ $앞의 문자로 끝나는지 의미한다.
  * a$: a로 끝나는 단어
### []: 괄호안의 문자가 있는지를 확인한다. 
  * [ab][cd]: a,b 중 한문자와 c,d 중 한 문자
  * ac, ad, bc, bc
### [^] []괄호 안에 ^가 있으면 제외를 뜻한다.
  * 대괄호 안에 ^가 쓰이면 제외의 뜻
  * 대괄호 밖에 ^가 쓰이면 시작점의 뜻
  * [^a-z]: 알파벳 소문자 a부터 z까지를 제외한 모든 문자
### - 사이의 문자 혹은 숫자를 의미한다.
  * [a-z]: 알파벳 소문자 a부터 z까지 하나
  * [a-z0-9]: 알파벳 소문자 전체, 0-9 중 한문자
### | 또는 
  * [a|b]: a 혹은 b
### () 그룹
  * 01(0|1): 01 뒤에 0또는 1이 들어간다.
### {} 개수
  * a{3}b: a가 3번 온 후 b가 온다. 
  * aab(x), aaab(o), aaaab(o)
### \b 공백, 탭 \ "" 등을 의미한다. 
  * apple\b: apple뒤에 공백 탭 등이 있다. 
  * apple juice(0), apple.com(x)
### \B \b의 부정 공백, 탭등이 아닌 문자인 경우 매치한다.
  * apple\B 
  * apple.com(o)
### \d 0-9 사이의 숫자
  * [0-9]와 동일
### \D \d의 부정
  * [^0-9]와 동일
### \s 공백, 탭
### \S 공백, 탭이 아닌 문자
### \w 알파벳 대문자 + 소문자 + 숫자 + "_"
  * [a-zA-Z_0-9]와 동일
### \W \w의 부정
  * [^a-zA-Z_0-9]와 동일

### ? 앞의 표현식이 없거나 최소 한개만 있다.
  * a1?: 1이 최대 한개이거나 없을 수도 있다. 
  * a(o), a1(0), a11(x), a111(x)
### * 앞의 표현식이 없거나 여러개 일수 있다.
  * a1*: 1이 있을 수도, 없을 수도 있다.
  * a(o), a1(o), a11(o), a111(o)
### + 앞의 표현식이 1개이상 or 여러개
  * a1+: 1이 1개이상 여러개 올 수 있다.
  * a(x), aa(x), a1(o), a111(o)
### {n} 딱 n 개있다. 
  * a{3}: a가 딱 3개 있다. 
  * a(x), aa(x), aaa(o), aaaa(x)
### {n, m} n개 이상, m개 이하
  * a{3,5}: a가 3개이상, 5개 이하있다.
  * a(x), aaa(o), aaaa(o), aaaaa(o), aaaaaa(x)
### {n,} n개 이상
  * a{3,}: a가 3개 이상 있다. 
  * aa(x), aaa(o), aaaa(o), aaaaaa(o)

## 정규식 그룹 캡쳐 기호
### () 그룹 및 갭처
### (?:) 찾지만 그룹에 포함 않됨
### (?=) =앞문자를 기준으로 전방탐색
### (?<=) =뒤 문자를 기준으로 후방 탐색

## 자주사용하는 정규식 샘플
### 숫자 : ^[0-9]*$ 
### 영문자: ^[a-zA-Z]*$
### 한글: ^[가-힣]*$
### 이메일: \\w+@\\w+(\\.\\w+)?
### 전화번호: ^\d{2,3}-\d{3,4}-\d{4}$
### 휴대폰번호: ^01(?:0|1[6-9])-(?:\d{3}|\d{4})-\d{4}$
### 주민등록번호: \d{6}-[1-4]\d{6}
### 우편번호: ^\d{3}-\d{2}$