package ru.aston.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.aston.model.User;
import ru.aston.model.dto.UserDto;
import ru.aston.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users/")
    public ResponseEntity<UserDto> addUser(@RequestBody final UserDto userDto) {
        User user = userService.addUser(User.from(userDto));
        return new ResponseEntity<>(UserDto.from(user), HttpStatus.OK);
    }

    @GetMapping("/users/")
    public ResponseEntity<List<UserDto>> getUsers() {
        List<User> users = userService.getUsers();
        List<UserDto> usersDto = users.stream().map(UserDto::from).collect(Collectors.toList());
        return new ResponseEntity<>(usersDto, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable final Long id) {
        User user = userService.getUser(id);
        return new ResponseEntity<>(UserDto.from(user), HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable final Long id) {
        User user = userService.deleteUser(id);
        return new ResponseEntity<>(UserDto.from(user), HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserDto> editUser(@PathVariable final Long id,
                                            @RequestBody final UserDto userDto) {
        User user = userService.editUser(id, User.from(userDto));
        return new ResponseEntity<>(UserDto.from(user), HttpStatus.OK);
    }

    @PostMapping("/users/{userId}/cards/{cardId}/add")
    public ResponseEntity<UserDto> addBankCarToUser(@PathVariable final Long userId,
                                                    @PathVariable final Long cardId) {
        User user = userService.addBankCardToUser(userId, cardId);
        return new ResponseEntity<>(UserDto.from(user), HttpStatus.OK);
    }

    @DeleteMapping("/users/{userId}/cards/{cardId}/remove")
    public ResponseEntity<UserDto> removeBankCardFromUser(@PathVariable final Long userId,
                                                          @PathVariable final Long cardId) {
        User user = userService.removeBankCardFromUser(userId, cardId);
        return new ResponseEntity<>(UserDto.from(user), HttpStatus.OK);
    }
}
