package com.back;

import com.back.domain.AppContext;
import com.back.domain.system.controller.SystemController;
import com.back.domain.wiseSaying.controller.WiseSayingController;

import java.util.Scanner;

public class App {
    private final Scanner sc;

    public App(Scanner sc) {
        AppContext.renew(sc);
        this.sc = AppContext.sc;
    }

    public void run() {
        System.out.println("== 명언 앱 ==");

        SystemController systemController = AppContext.systemController;
        WiseSayingController wiseSayingController = AppContext.wiseSayingController;

        while (true) {
            System.out.print("명령) ");
            Rq rq = new Rq(sc.nextLine().trim());
            String cmd = rq.getActionName();

            switch (cmd) {
                case "종료" -> {
                    systemController.actionExit();
                    return;
                }
                case "등록" -> wiseSayingController.actionRegister();
                case "목록" -> wiseSayingController.actionList();
                case "삭제" -> wiseSayingController.actionDelete(rq);
                case "수정" -> wiseSayingController.actionEdit(rq);
            }
        }
    }
}
