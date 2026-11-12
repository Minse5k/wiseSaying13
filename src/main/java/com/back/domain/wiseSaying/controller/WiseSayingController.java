package com.back.domain.wiseSaying.controller;

import com.back.Rq;
import com.back.domain.AppContext;
import com.back.domain.wiseSaying.entity.WiseSaying;
import com.back.domain.wiseSaying.service.WiseSayingService;
import com.back.standard.dto.Page;

import java.util.*;

public class WiseSayingController {
    private static final int PAGE_SIZE = 5;

    private final WiseSayingService wiseSayingService = AppContext.wiseSayingService;
    private final Scanner sc = AppContext.sc;

    public void actionRegister() {
        System.out.print("명언 : ");
        String content = sc.nextLine();

        System.out.print("작가 : ");
        String author = sc.nextLine();

        WiseSaying item = wiseSayingService.write(author, content);

        System.out.printf("%d번 명언이 등록되었습니다.\n", item.getId());
    }

    public void actionList(Rq rq) {
        String keywordType = rq.getParam("keywordType", "content");
        String keyword = rq.getParam("keyword", "");
        int page = Math.max(1, rq.getParamAsInt("page", 1));

        if (!keyword.isBlank()) {
            System.out.println("----------------------");
            System.out.println("검색타입 : " + keywordType);
            System.out.println("검색어 : " + keyword);
            System.out.println("----------------------");
        }

        Page<WiseSaying> wisePage = wiseSayingService.findForList(keywordType, keyword, page, PAGE_SIZE);

        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");

        wisePage.getContent().forEach(e -> System.out.println(e.toListString()));

        System.out.println("----------------------");
        printPageMenu(wisePage);
    }

    private void printPageMenu(Page<WiseSaying> wisePage) {
        StringBuilder sb = new StringBuilder("페이지 : ");

        for (int i = 1; i <= wisePage.getTotalPages(); i++) {
            if (i > 1) sb.append(" / ");
            sb.append(i == wisePage.getPage() ? "[" + i + "]" : String.valueOf(i));
        }

        System.out.println(sb);
    }

    public void actionDelete(Rq rq) {
        int id = rq.getParamAsInt("id", -1);

        boolean isDelete = wiseSayingService.delete(id);

        System.out.println(id + "번 명언" + (isDelete ? "이 삭제되었습니다." : "은 존재하지 않습니다."));
    }



    public void actionEdit(Rq rq) {
        int id = rq.getParamAsInt("id", -1);

        WiseSaying item = wiseSayingService.findById(id);

        if (item == null) {
            System.out.println(id + "번 명언은 존재하지 않습니다.");
            return;
        }

        System.out.println("명언(기존) : " + item.getContent());
        System.out.printf("명언 : ");
        String content = sc.nextLine();

        System.out.println("작가(기존) : " + item.getAuthor());
        System.out.printf("작가 : ");
        String author = sc.nextLine();

        wiseSayingService.edit(item, author, content);
    }
}
