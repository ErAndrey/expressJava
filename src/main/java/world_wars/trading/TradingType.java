package world_wars.trading;

public enum TradingType {
    BUY, // (1:N) 1 покупает 💎 за 5💰/шт -> 2 дает 💎 и получает 5💰
    SELL, // (1:N) 1 продает 🌳 за 1🧱/шт -> 2 дает 1🧱 и получает 🌳
    SWAP; // (N:N) 1 меняет 10🍒 на 8🌳 -> 2 дает 8🌳 и получает 10🍒
}
