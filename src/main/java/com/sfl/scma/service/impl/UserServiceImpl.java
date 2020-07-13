package com.sfl.scma.service.impl;

import com.sfl.scma.domain.User;
import com.sfl.scma.entity.OrderEntity;
import com.sfl.scma.entity.TableEntity;
import com.sfl.scma.entity.UserEntity;
import com.sfl.scma.enums.Role;
import com.sfl.scma.mapper.UserMapper;
import com.sfl.scma.repository.OrderRepository;
import com.sfl.scma.repository.TableRepository;
import com.sfl.scma.repository.UserRepository;
import com.sfl.scma.security.jwt.domain.ScmaUserPrincipal;
import com.sfl.scma.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final TableRepository tableRepository;
    private final OrderRepository orderRepository;

    @Override
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserEntity userEntity = userMapper.toEntity(user);
        userRepository.save(userEntity);
        user.setId(userEntity.getId());
        return user;
    }

    @Override
    public List<User> getAllScmaUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(userMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> getUserById(Long userId) {
        return userRepository
                .findById(userId)
                .map(userMapper::toDomain);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.toDomain(userRepository.findByUsername(username));
        return new ScmaUserPrincipal(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);

        if (Role.ROLE_WAITER.name().equals(userEntity.getRole())) {
            List<TableEntity> tables = tableRepository.findByWaiter(userEntity);
            tables.forEach(tableEntity -> {
                tableEntity.setWaiter(null);
                tableRepository.save(tableEntity);
            });

            List<OrderEntity> orders = orderRepository.findByWaiter(userEntity);
            orders.forEach(orderEntity -> {
                orderEntity.setWaiter(null);
                orderRepository.save(orderEntity);
            });
        }

        userRepository.deleteById(userId);
    }

}