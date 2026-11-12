package com.back.domain.wiseSaying.service;

import com.back.domain.AppContext;
import com.back.domain.wiseSaying.entity.WiseSaying;
import com.back.domain.wiseSaying.repository.WiseSayingRepository;
import com.back.standard.dto.Page;

import java.util.List;

public class WiseSayingService {
    WiseSayingRepository wiseSayingRepository = AppContext.wiseSayingRepository;

    public WiseSaying write(String author, String content) {
        WiseSaying item = new WiseSaying(author, content);

        wiseSayingRepository.save(item);

        return item;
    }

    public WiseSaying findById(int id) {
        return wiseSayingRepository.findById(id);
    }

    public void edit(WiseSaying item, String author, String content) {
        wiseSayingRepository.edit(item, author, content);
    }

    public List<WiseSaying> reverseList() {
        return wiseSayingRepository.reverseList();
    }

    public Page<WiseSaying> findForList(String keywordType, String keyword, int page, int pageSize) {
        return wiseSayingRepository.findForList(keywordType, keyword, page, pageSize);
    }

    public void makeSampleData(int count) {
        if (wiseSayingRepository.count() > 0) return;

        for (int i = 1; i <= count; i++) {
            write("작자미상 " + i, "명언 " + i);
        }
    }

    public boolean delete(int id) {
        return wiseSayingRepository.delete(id);
    }
}
