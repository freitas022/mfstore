package br.com.example.mfstore.catalog.domain.event;

public record LowStockEvent(Long productId, int quantityAvailable) {
}
