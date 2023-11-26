package edu.hw8.task1;

import java.util.List;

public final class QuoteRepository {
    private static final List<String> QUOTES = List.of(
            "Не переходи на личности там, где их нет",
            "Если твои противники перешли на личные оскорбления, будь уверена — твоя победа не за горами",
            "А я тебе говорил, что ты глупый? Так вот, я забираю свои слова обратно... Ты просто бог идиотизма.",
            "Чем ниже интеллект, тем громче оскорбления"
    );

    public static List<String> find(String keyword) {
        return QUOTES.stream()
                .filter(quote -> quote.contains(keyword))
                .toList();
    }

    private QuoteRepository() {
    }
}
