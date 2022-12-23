package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exception.BadRequestException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public Item addItem(@RequestHeader("X-Sharer-User-Id") int ownerId,
                        @RequestBody ItemDto itemDto) {
        throwIfNotValid(itemDto);
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
    public List<Item> getMyItems(@RequestHeader("X-Sharer-User-Id") int ownerId) {
        return itemService.getMyItems(ownerId);
    }

    @GetMapping("/search")
    public List<Item> searchForItems(@RequestParam(value = "text") String text) {
        return itemService.searchForItems(text);
    }

    private void throwIfNotValid(ItemDto itemDto) throws BadRequestException {
        if (itemDto.getName() == null || itemDto.getName().isBlank()) {
            throw new BadRequestException("Не указано название предмета");
        }
        if (itemDto.getDescription() == null || itemDto.getDescription().isBlank()) {
            throw new BadRequestException("Не указано описание предмета");
        }
        if (itemDto.getAvailable() == null) {
            throw new BadRequestException("Не указана доступность предмета");
        }
    }
}
