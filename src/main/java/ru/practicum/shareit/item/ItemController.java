package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemMapper;
import ru.practicum.shareit.item.service.ItemService;

import java.util.Collection;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemMapper itemMapper;
    private final ItemService itemService;

    @Autowired
    public ItemController(ItemMapper itemMapper, ItemService itemService) {
        this.itemMapper = itemMapper;
        this.itemService = itemService;
    }

    @PostMapping
    public Item addItem(@RequestHeader("X-Sharer-User-Id") int ownerId,
                        @RequestBody ItemDto itemDto) {
        validate(itemDto);
        return itemService.addItem(ownerId, itemDto);
    }

    @PatchMapping("/{itemId}")
    public Item updateItem(@RequestHeader("X-Sharer-User-Id") int ownerId,
                           @RequestBody ItemDto itemDto,
                           @PathVariable int itemId) {
        return itemService.updateItem(ownerId, itemDto, itemId);
    }

    @GetMapping("/{itemId}")
    public Item getItemById(@PathVariable int itemId) {
        return itemService.getItemById(itemId);
    }


    @GetMapping
    public Collection<Item> getMyItems(@RequestHeader("X-Sharer-User-Id") int ownerId) {
        return itemService.getMyItems(ownerId);
    }

    @GetMapping("/search")
    public Collection<Item> searchForItems(@RequestParam(value = "text") String text) {
        return itemService.searchForItems(text);
    }

    private void validate(ItemDto itemDto) throws ValidationException {
        if (itemDto.getName() == null || itemDto.getName().isBlank()) {
            throw new ValidationException("Не указано название предмета");
        }
        if (itemDto.getDescription() == null || itemDto.getDescription().isBlank()) {
            throw new ValidationException("Не указано описание предмета");
        }
        if (itemDto.getAvailable() == null) {
            throw new ValidationException("Не указана доступность предмета");
        }
    }
}
