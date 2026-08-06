package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.assembler.UserModelAssembler;
import org.example.dto.UserRequestDto;
import org.example.dto.UserResponseDto;
import org.example.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/users")
@Tag(name = "Пользователи")
public class UserController {
    private final UserService userService;
    private final UserModelAssembler assembler;

    public UserController(UserService userService, UserModelAssembler assembler) {
        this.userService = userService;
        this.assembler = assembler;
    }


    @GetMapping
    @Operation(
            summary = "Получить всех пользователей",
            description = "Возвращает список всех пользователей из базы данных"
    )
    @ApiResponse(responseCode = "200", description = "Список пользователей успешно получен")
    public List<UserResponseDto> getUsers() {
        return userService.findAll()
                .stream()
                .map(assembler::toModel)
                .toList();
    }

    @PostMapping
    @Operation(
            summary = "Создать пользователя",
            description = "Создать нового пользователя в базе данных"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Пользователь успешно создан"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные пользователя")
    })
    public void createUser(@RequestBody UserRequestDto request) {
        userService.createUser(request.getName(), request.getEmail(), request.getAge());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получить пользователя",
            description = "Получить пользователя по указанному идентификатору"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Пользователь успешно найден"),
            @ApiResponse(responseCode = "400", description = "Некорректный идентификатор"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
    })
    public UserResponseDto getUser(
            @Parameter(description = "Идентификатор пользователя", example = "1")
            @PathVariable Long id) {
        return assembler.toModel(userService.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Обновить пользователя",
            description = "Обновляет данные существующего пользователя по указанному идентификатору"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Пользователь успешно обновлен"),
            @ApiResponse(responseCode = "400", description = "Некорректный идентификатор"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
    })
    public ResponseEntity<Void> updateUser(
            @Parameter(description = "Идентификатор пользователя", example = "1")
            @PathVariable Long id,
            @RequestBody UserRequestDto request) {

        userService.updateUser(id, request.getName(), request.getEmail(), request.getAge());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удалить пользователя",
            description = "Удаляет пользователя по указанному идентификатору"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Пользователь удален"),
            @ApiResponse(responseCode = "400", description = "Некорректный идентификатор"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
    })
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "Идентификатор пользователя", example = "1")
            @PathVariable Long id) {

        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
