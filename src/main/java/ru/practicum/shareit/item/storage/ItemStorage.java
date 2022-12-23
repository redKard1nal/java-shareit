package ru.practicum.shareit.item.storage;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class ItemStorage {

    private final Map<Integer, Item> items = new HashMap<>();
    private int availableId = 0;

    public Item addItem(Item item) {
        items.remove(item.getId());
        items.put(item.getId(), item);
        return item;
    }

    public Optional<Item> getItemById(int id) {
        return Optional.of(items.get(id));
    }

    public Map<Integer, Item> getItems() {
        return items;
    }

    public int getMaxId() {
        return ++availableId;
    }


}
