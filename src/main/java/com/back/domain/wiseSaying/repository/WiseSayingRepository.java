package com.back.domain.wiseSaying.repository;

import com.back.domain.wiseSaying.entity.WiseSaying;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WiseSayingRepository {
    List<WiseSaying> wiseList = new ArrayList<>();
    int lastId = 0;

    public void save(WiseSaying wiseSaying) {
        if(wiseSaying.getId() != 0) return;

        wiseSaying.setId(++lastId);
        wiseList.add(wiseSaying);
    }

    public WiseSaying findById(int id) {
        return wiseList.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<WiseSaying> reverseList() {
        List<WiseSaying> reverseList = new ArrayList<>(wiseList);
        Collections.reverse(reverseList);
        return reverseList;
    }

    public boolean delete(int id) {
        return wiseList.removeIf(e -> e.getId() == id);
    }

    public void edit(WiseSaying item, String author, String content) {
        item.setAuthor(author);
        item.setContent(content);
    }
}
