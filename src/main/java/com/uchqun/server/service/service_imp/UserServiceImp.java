package com.uchqun.server.service.service_imp;

import com.uchqun.server.exception.CustomNotFoundException;
import com.uchqun.server.model.entity.User;
import com.uchqun.server.model.enums.Role;
import com.uchqun.server.model.requestDto.UserRequest;
import com.uchqun.server.model.responseDto.UserResponse;
import com.uchqun.server.payload.ApiResponse;
import com.uchqun.server.repository.UserRepository;
import com.uchqun.server.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public ApiResponse save(UserRequest user) {
        Optional<User> byUsername = userRepository.findByUsername(user.getUsername());
        if(byUsername.isPresent()){
            return new ApiResponse("User already exist", HttpStatus.CONFLICT);
        }
        User newUser = new User();
        newUser.setFirstName(user.getFirstName());
        if(user.getLastName() != null)
            newUser.setLastName(user.getLastName());
        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setRole(Role.TEACHER);
        userRepository.save(newUser);
        return new ApiResponse("User created", HttpStatus.OK);
    }



    @Override
    public List<UserResponse> findAll() {
        List<User> all = userRepository.findAll();
        return mapToUserResponseDtoList(all);
    }

    @Override
    public UserResponse findOne(Long id) {
        Optional<User> byId = userRepository.findById(id);
        if(byId.isEmpty()){
            throw new CustomNotFoundException("User not found");
        }

        return mapToUserResponseDto().apply(byId.get());
    }

    @Override
    public ApiResponse update(Long id, UserRequest user) {
        Optional<User> byId = userRepository.findById(id);
        if(byId.isEmpty()){
            throw new CustomNotFoundException("User not found");
        }

        User oldUser = byId.get();
        oldUser.setFirstName(user.getFirstName());
        if(user.getLastName() != null)
            oldUser.setLastName(user.getLastName());
        oldUser.setUsername(user.getUsername());
        userRepository.save(oldUser);
        return new ApiResponse("User updated", HttpStatus.OK);
    }




    @Override
    public ApiResponse delete(Long id) {
        if(userRepository.existsById(id)){
            userRepository.deleteById(id);
            return new ApiResponse("User deleted", HttpStatus.OK);
        }
        throw new CustomNotFoundException("User not found");
    }

    private List<UserResponse> mapToUserResponseDtoList(List<User> users) {
        if (users == null) {
            return  Collections.emptyList();
        }
        return users
                .stream()
                .map(mapToUserResponseDto())
                .toList();
    }

    private Function<User, UserResponse> mapToUserResponseDto() {
        return user -> {
            UserResponse userResponse = new UserResponse();
            userResponse.setId(user.getId());
            userResponse.setFirstName(user.getFirstName());
            userResponse.setLastName(user.getLastName());
            userResponse.setUsername(user.getUsername());
            userResponse.setRole(user.getRole());
            return userResponse;
        };
    }


}
