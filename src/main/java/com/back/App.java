package com.back;

import com.back.domain.AppContext;
import com.back.domain.system.controller.SystemController;
import com.back.domain.wiseSaying.controller.WiseSayingController;

import java.util.Scanner;

public class App {
    private static final int SAMPLE_DATA_COUNT = 10;

    private final Scanner sc;
    private final boolean initSampleData;

    public App(Scanner sc) {
        this(sc, true);
    }

    public App(Scanner sc, boolean initSampleData) {
        AppContext.renew(sc);
        this.sc = AppContext.sc;
        this.initSampleData = initSampleData;
    }

    public void run() {
        if (initSampleData) AppContext.wiseSayingService.makeSampleData(SAMPLE_DATA_COUNT);

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
                case "목록" -> wiseSayingController.actionList(rq);
                case "삭제" -> wiseSayingController.actionDelete(rq);
                case "수정" -> wiseSayingController.actionEdit(rq);
            }
        }
    }
}
