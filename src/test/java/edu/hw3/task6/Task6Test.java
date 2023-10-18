package edu.hw3.task6;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Task6Test {
    @Test
    @DisplayName("Null stock")
    void nullStock() {
        StockMarket market = new HeapMarket();

        assertThrows(NullPointerException.class, () -> market.add(null));
        assertThrows(NullPointerException.class, () -> market.remove(null));
    }

    @Test
    @DisplayName("Empty market")
    void emptyMarket() {
        StockMarket market = new HeapMarket();

        assertThat(market.mostValuableStock()).isNull();
    }

    @Test
    @DisplayName("One stock")
    void oneStock() {
        StockMarket market = new HeapMarket();
        Stock stock = new Stock(1, "Apple");

        market.add(stock);
        Stock mostValuableStock = market.mostValuableStock();

        assertThat(mostValuableStock).isNotNull().isEqualTo(stock);
    }

    @Test
    @DisplayName("Multiple stocks")
    void multipleStocks() {
        StockMarket market = new HeapMarket();
        Stock meta = new Stock(10, "meta");
        Stock tinkoff = new Stock(Integer.MAX_VALUE, "tinkoff");
        Stock apple = new Stock(20, "apple");

        market.add(meta);
        market.add(tinkoff);
        market.add(apple);
        Stock mostValuableStock = market.mostValuableStock();

        assertThat(mostValuableStock).isEqualTo(tinkoff);
    }

    @Test
    @DisplayName("Removing stocks")
    void removingStocks() {
        StockMarket market = new HeapMarket();
        Stock jetbrains = new Stock(42, "jetbrains");
        Stock huawei = new Stock(12, "huawei");

        market.add(jetbrains);
        market.add(huawei);
        Stock mostValuableStockBeforeRemoval = market.mostValuableStock();
        market.remove(jetbrains);
        Stock mostValuableStockAfterRemoval = market.mostValuableStock();

        assertThat(mostValuableStockBeforeRemoval).isEqualTo(jetbrains);
        assertThat(mostValuableStockAfterRemoval).isEqualTo(huawei);
    }

    @Test
    @DisplayName("Removing the same stock twice")
    void removingTheSameStockTwice() {
        StockMarket market = new HeapMarket();
        Stock stock = new Stock(128, "IKEA");

        market.remove(stock);

        assertThatNoException().isThrownBy(() -> market.remove(stock));
    }

    @Test
    @DisplayName("Adding the same stock twice")
    void addingTheSameStockTwice() {
        StockMarket market = new HeapMarket();
        Stock stock = new Stock(42, "GazProm");

        market.add(stock);
        market.add(stock);
        market.remove(stock);

        // ???
    }

    @Test
    @DisplayName("Two stocks of the same price")
    void twoStocksOnePrice() {
        StockMarket market = new HeapMarket();
        Stock stock1 = new Stock(1, "stock1");
        Stock stock2 = new Stock(1, "stock2");

        market.add(stock1);
        market.add(stock2);
        market.remove(stock1);
        Stock mostValuableStock = market.mostValuableStock();

        assertThat(mostValuableStock).isNotNull().isEqualTo(stock2);
    }
}
