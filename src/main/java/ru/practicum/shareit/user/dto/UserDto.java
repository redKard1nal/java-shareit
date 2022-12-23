package ru.practicum.shareit.user.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Data
public class UserDto {
    String name;
    @Email
    String email;
}
