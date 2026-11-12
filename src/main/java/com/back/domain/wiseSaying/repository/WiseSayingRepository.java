package com.back.domain.wiseSaying.repository;

import com.back.domain.wiseSaying.entity.WiseSaying;
import com.back.standard.dto.Page;

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

    public Page<WiseSaying> findForList(String keywordType, String keyword, int page, int pageSize) {
        List<WiseSaying> filtered = reverseList().stream()
                .filter(e -> matches(e, keywordType, keyword))
                .toList();

        int from = Math.min((page - 1) * pageSize, filtered.size());
        int to = Math.min(from + pageSize, filtered.size());

        return new Page<>(filtered.subList(from, to), filtered.size(), page, pageSize);
    }

    private boolean matches(WiseSaying wiseSaying, String keywordType, String keyword) {
        if (keyword == null || keyword.isBlank()) return true;

        return switch (keywordType) {
            case "author" -> wiseSaying.getAuthor().contains(keyword);
            case "content" -> wiseSaying.getContent().contains(keyword);
            default -> wiseSaying.getAuthor().contains(keyword) || wiseSaying.getContent().contains(keyword);
        };
    }

    public int count() {
        return wiseList.size();
    }

    public boolean delete(int id) {
        return wiseList.removeIf(e -> e.getId() == id);
    }

    public void edit(WiseSaying item, String author, String content) {
        item.setAuthor(author);
        item.setContent(content);
    }
}
