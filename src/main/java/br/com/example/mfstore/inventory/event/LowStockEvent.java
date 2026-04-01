package br.com.example.mfstore.inventory.event;

public record LowStockEvent(Long productId, int quantityAvailable) {
}
