package org.opennuri.study.regexp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegExpression1 {
    private static final Logger log = LoggerFactory.getLogger(RegExpression1.class);

    public static void main(String[] args) {
        log.info("hello, logback");
    }
    //사용자 아이디는 문자로 시작
    //사용자아이디는 a-z, A-Z, 0-9, _ 만 사용
    //사용자 아이디는 문자가 2개이상 반복 금지
    //이메일은 @를 포함
    //어떤 문자하나에 대한 정규표현식을 확인
    //h로 시작하고 a가 1개이상 반복되고 t로 끝나는 문자
    //h로 시작하고 h가 0개이상 반복되고 t로 끝나는 문자
    //h로 시작하고 t로 끝나며 그 사이에 어떤 문자가 0개 이상
    //h로 시작하고 t로 끝나며 그 사이에 어떤 문자가 1개 이상
    //abc 중 하나
    //a부터 z까지 중 하나
    //A부터 Z까지 중 하나
    //0부터 9까지 중 하나
    //특수문자 중 하나
    //a-z, A-Z, 0-9, _ 중 하나
    //0-9 중 여러개
    //0-9를 제외한 문자 1개
    //0-9 중 3개
    //0-9 중 3개 이상
    //앞 문자가 0또는 이상 반복
    //앞 문자가 1또는 이상 반복
    //앞 문자가 3번 반복
    //앞 문자가 3번 이상 반복
    //앞 문자가 3번 이상 5번 이하 반복
    //(na)가 0개이상 반복
    //ba(na)가 1개이상 반복
    //괄호로 묶인 그룹 내에서 | 로 나누어진 여러 패턴중 일치하는 것 중 하나
    //(h.t)가 0개이상 반복
    //h로 시작하고 t로 끝나는 그룹 2개
    //연속된 문자가 있는 지 확인
    //2개이상 연속된 문자가 있는지 확인
    //특수문자가 있는지 확인






}
