package edu.bbte.pmim2290.vrp.controller;

import edu.bbte.pmim2290.vrp.dto.InUserDTO;
import edu.bbte.pmim2290.vrp.dto.OutUserDTO;
import edu.bbte.pmim2290.vrp.exception.DatabaseException;
import edu.bbte.pmim2290.vrp.exception.EntityNotFoundException;
import edu.bbte.pmim2290.vrp.mapper.UserMapper;
import edu.bbte.pmim2290.vrp.model.User;
import edu.bbte.pmim2290.vrp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private final UserService userService;

    @Autowired
    private UserMapper userMapper;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<OutUserDTO> getUsers()
            throws DatabaseException {
        List<User> users;
        users = userService.getAllUsers();


        return users.stream()
                .map(userMapper::toOutUserDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public OutUserDTO getUser(@PathVariable Long id) throws EntityNotFoundException, DatabaseException {
        Optional<User> user = userService.getUserById(id);
        return userMapper.toOutUserDTO(user.get());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<OutUserDTO> createUser(@Valid @RequestBody InUserDTO inUser)
            throws DatabaseException, EntityNotFoundException {
        User user = userMapper.toUser(inUser);
        URI uri = URI.create("api/Users/" + user.getId());
        return ResponseEntity.created(uri).body(userMapper.toOutUserDTO(userService.createUser(user)));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<OutUserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody InUserDTO inUser)
            throws EntityNotFoundException, DatabaseException {
        User user = userMapper.toUser(inUser);
        user.setId(id);
        return ResponseEntity.ok(userMapper.toOutUserDTO(userService.updateUser(user)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) throws EntityNotFoundException, DatabaseException {
        Optional<User> user = userService.getUserById(id);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("The requested User does not exist");
        }

        userService.deleteUser(id);
    }
}
