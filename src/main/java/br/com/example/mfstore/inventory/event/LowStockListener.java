package br.com.example.mfstore.inventory.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class LowStockListener {

    @EventListener
    public void onLowStock(LowStockEvent event) {
        // TODO implements
    }
}
