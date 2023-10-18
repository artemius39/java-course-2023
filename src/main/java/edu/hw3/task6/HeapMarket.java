package edu.hw3.task6;

import java.util.Comparator;
import java.util.Objects;
import java.util.PriorityQueue;

public class HeapMarket implements StockMarket {
    private static final Comparator<Stock> COMPARE_BY_VALUE_DESCENDING = Comparator.comparing(Stock::value).reversed();

    private final PriorityQueue<Stock> stocks;

    public HeapMarket() {
        stocks = new PriorityQueue<>(COMPARE_BY_VALUE_DESCENDING);
    }

    @Override
    public void add(Stock stock) {
        stocks.add(Objects.requireNonNull(stock, "Can't add null stock"));
    }

    @Override
    public void remove(Stock stock) {
        stocks.remove(Objects.requireNonNull(stock, "Can't remove null stock"));
    }

    @Override
    public Stock mostValuableStock() {
        return stocks.peek();
    }
}
