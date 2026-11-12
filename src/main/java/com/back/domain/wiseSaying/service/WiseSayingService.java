package com.back.domain.wiseSaying.service;

import com.back.domain.AppContext;
import com.back.domain.wiseSaying.entity.WiseSaying;
import com.back.domain.wiseSaying.repository.WiseSayingRepository;

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

    public boolean delete(int id) {
        return wiseSayingRepository.delete(id);
    }
}
