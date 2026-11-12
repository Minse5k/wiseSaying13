package com.back;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WiseSayingListTest {
    @Test
    @DisplayName("내용 검색: 키워드가 들어간 명언만 나오고 검색 조건이 목록 위에 출력된다")
    void t1() {
        String out = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                목록?keywordType=content&keyword=과거
                종료
                """);

        assertThat(out)
                .containsSubsequence(
                        "검색타입 : content",
                        "검색어 : 과거",
                        "번호 / 작가 / 명언",
                        "2 / 작자미상 / 과거에 집착하지 마라."
                )
                .doesNotContain("1 / 작자미상 / 현재를 사랑하라.");
    }

    @Test
    @DisplayName("작가 검색: 작가에 키워드가 들어간 명언만 나온다")
    void t2() {
        String out = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                나의 죽음을 적들에게 알리지 말라!
                이순신
                목록?keywordType=author&keyword=작자
                종료
                """);

        assertThat(out)
                .containsSubsequence(
                        "검색타입 : author",
                        "검색어 : 작자",
                        "1 / 작자미상 / 현재를 사랑하라."
                )
                .doesNotContain("2 / 이순신 / 나의 죽음을 적들에게 알리지 말라!");
    }

    @Test
    @DisplayName("명언이 0개면 앱 시작 시 샘플 명언 10개를 만든다")
    void t3() {
        String out = AppTestRunner.runWithSampleData("""
                목록?page=2
                종료
                """);

        assertThat(out).contains("1 / 작자미상 1 / 명언 1", "페이지 : 1 / [2]");
    }

    @Test
    @DisplayName("page 생략 시 1페이지: 10~6번이 최신순으로 나온다")
    void t4() {
        String out = AppTestRunner.runWithSampleData("""
                목록
                종료
                """);

        assertThat(out)
                .containsSubsequence(
                        "10 / 작자미상 10 / 명언 10",
                        "9 / 작자미상 9 / 명언 9",
                        "8 / 작자미상 8 / 명언 8",
                        "7 / 작자미상 7 / 명언 7",
                        "6 / 작자미상 6 / 명언 6",
                        "페이지 : [1] / 2"
                )
                .doesNotContain("5 / 작자미상 5 / 명언 5");
    }

    @Test
    @DisplayName("2페이지: 5~1번이 최신순으로 나온다")
    void t5() {
        String out = AppTestRunner.runWithSampleData("""
                목록?page=2
                종료
                """);

        assertThat(out)
                .containsSubsequence(
                        "5 / 작자미상 5 / 명언 5",
                        "4 / 작자미상 4 / 명언 4",
                        "3 / 작자미상 3 / 명언 3",
                        "2 / 작자미상 2 / 명언 2",
                        "1 / 작자미상 1 / 명언 1",
                        "페이지 : 1 / [2]"
                )
                .doesNotContain("6 / 작자미상 6 / 명언 6");
    }

    @Test
    @DisplayName("검색 + 페이징: 내용에 1 검색 시 10번·1번이 한 페이지에 나온다")
    void t6() {
        String out = AppTestRunner.runWithSampleData("""
                목록?keywordType=content&keyword=1&page=1
                종료
                """);

        assertThat(out)
                .containsSubsequence(
                        "검색타입 : content",
                        "검색어 : 1",
                        "10 / 작자미상 10 / 명언 10",
                        "1 / 작자미상 1 / 명언 1",
                        "페이지 : [1]"
                )
                .doesNotContain("9 / 작자미상 9 / 명언 9", "페이지 : [1] / 2");
    }

    @Test
    @DisplayName("검색 결과를 기준으로 페이징한다")
    void t7() {
        // 샘플 10개 + 홍길동 3개 = 13개(전체 3페이지), 작가 '작자' 검색 결과는 10개(2페이지)
        String out = AppTestRunner.runWithSampleData("""
                등록
                명언 11
                홍길동
                등록
                명언 12
                홍길동
                등록
                명언 13
                홍길동
                목록?keywordType=author&keyword=작자&page=2
                종료
                """);

        assertThat(out)
                .containsSubsequence(
                        "검색타입 : author",
                        "검색어 : 작자",
                        "5 / 작자미상 5 / 명언 5",
                        "1 / 작자미상 1 / 명언 1",
                        "페이지 : 1 / [2]"
                )
                .doesNotContain("홍길동 / 명언", "페이지 : 1 / [2] / 3");
    }
}
