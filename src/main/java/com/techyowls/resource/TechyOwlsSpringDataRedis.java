package com.techyowls.resource;

import com.techyowls.eniity.User;
import com.techyowls.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static com.techyowls.resource.ResponseUtil.resourceUri;

@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TechyOwlsSpringDataRedis {
    private final UserRepository userRepository;

    @PostMapping("/user")
    public ResponseEntity<User> saveUser(
            @Valid @RequestBody User request
    ) {
        return Optional.of(request)
                .map(userRepository::save)
                .map(
                        user -> ResponseEntity
                                .created(resourceUri(user.getId()))
                                .body(user)
                )
                .orElseThrow(IllegalArgumentException::new);

    }

    @GetMapping("/user/findByFirstName/{firstName}")
    private ResponseEntity<List<User>> findByFirstName(@PathVariable("firstName") String firstName) {
        return ResponseEntity.ok().body(userRepository.findByFirstName(firstName));
    }
}
