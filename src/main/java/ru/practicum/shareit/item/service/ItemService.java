package ru.practicum.shareit.item.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.AccessDeniedException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemService {

    private final ItemStorage itemStorage;
    private final UserService userService;

    @Autowired
    public ItemService(ItemStorage itemStorage, UserService userService) {
        this.itemStorage = itemStorage;
        this.userService = userService;
    }

    public Item addItem(int ownerId, ItemDto itemDto) {
        if (!userService.isExist(ownerId)) {
            throw new NotFoundException("Не найден владелец для добавляемой вещи с id: " + ownerId);
        }

        Item item = buildItem(itemDto, generateId(), ownerId);
        return itemStorage.addItem(item);
    }

    public Item updateItem(int ownerId, ItemDto itemDto, int itemId) {
        if (getItemById(itemId).getOwner() != ownerId) {
            throw new AccessDeniedException("Вы не владелец этой вещи!");
        }
        Item item = getItemById(itemId);
        if (itemDto.getAvailable() != null) {
            item.setAvailable(itemDto.getAvailable());
        }
        if (itemDto.getName() != null) {
            item.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null) {
            item.setDescription(itemDto.getDescription());
        }
        return itemStorage.addItem(item);
    }

    public Collection<Item> getMyItems(int ownerId) {
        return itemStorage.getItems().values().stream()
                .filter(item -> item.getOwner() == ownerId)
                .collect(Collectors.toList());
    }

    public Collection<Item> searchForItems(String text) {
        if (text == null || text.isBlank()) {
            return List.of();
        }
        String loweredText = text.toLowerCase();
        return itemStorage.getItems().values().stream()
                .filter(e -> e.isAvailable() &&
                        (e.getName().toLowerCase().contains(loweredText)
                                || e.getDescription().toLowerCase().contains(loweredText)))
                .collect(Collectors.toList());
    }

    public Item getItemById(int id) {
        Optional<Item> item = itemStorage.getItemById(id);
        if (item.isEmpty()) {
            throw new NotFoundException("Нет вещи с id: " + id);
        }
        return item.get();
    }

    private int generateId() {
        return itemStorage.getMaxId();
    }

    private Item buildItem(ItemDto itemDto, int id, int ownerId) {
        return Item.builder()
                .id(id)
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.getAvailable())
                .owner(ownerId)
                .build();
    }

}
