package com.agropapin.backend.iam.application.internal.commandservices;

import com.agropapin.backend.iam.application.internal.outboundservices.hashing.HashingService;
import com.agropapin.backend.iam.application.internal.outboundservices.tokens.TokenService;
import com.agropapin.backend.iam.domain.model.aggregates.User;
import com.agropapin.backend.iam.domain.model.commands.SignInCommand;
import com.agropapin.backend.iam.domain.model.commands.SignUpDeveloperCommand;
import com.agropapin.backend.iam.domain.model.commands.SignUpEnterpriseCommand;
import com.agropapin.backend.iam.domain.model.entities.Role;
import com.agropapin.backend.iam.domain.model.valueobjects.Roles;
import com.agropapin.backend.iam.domain.services.UserCommandService;
import com.agropapin.backend.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import com.agropapin.backend.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final HashingService hashingService;
    private final TokenService tokenService;
    private final DeveloperRepository developerRepository;
    private final EnterpriseRepository enterpriseRepository;


    public UserCommandServiceImpl(UserRepository userRepository, RoleRepository roleRepository, HashingService hashingService, TokenService tokenService , DeveloperRepository developerRepository, EnterpriseRepository enterpriseRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.hashingService = hashingService;
        this.tokenService = tokenService;
        this.developerRepository = developerRepository;
        this.enterpriseRepository = enterpriseRepository;
    }

    @Override
    public Optional<ImmutablePair<User, String>> handle(SignInCommand command) {
        var user = userRepository.findByUsername(command.username());
        if (user.isEmpty())
            throw new RuntimeException("User not found");
        if (!hashingService.matches(command.password(), user.get().getPassword()))
            throw new RuntimeException("Invalid password");
        var token = tokenService.generateToken(user.get().getUsername());
        return Optional.of(ImmutablePair.of(user.get(), token));
    }

//    @Override
//    public Optional<User> handle(SignUpCommand command) {
//        if (userRepository.existsByUsername(command.username()))
//            throw new RuntimeException("Username already exists");
//        var roles = command.roles().stream().map(role -> roleRepository.findByName(role.getName()).orElseThrow(() -> new RuntimeException("Role name not found"))).toList();
//        var user = new User(command.username(), hashingService.encode(command.password()), roles);
//        userRepository.save(user);
//        return userRepository.findByUsername(command.username());
//    }

    @Override
    public Optional<User> handle(SignUpDeveloperCommand command) {
        if (userRepository.existsByUsername(command.username()))
            throw new RuntimeException("Username already exists");

        // Buscar el rol de desarrollador en el repositorio de roles
        Role developerRole = roleRepository.findByName(Roles.valueOf("ROLE_DEVELOPER"))
                .orElseThrow(() -> new RuntimeException("Developer role not found"));

        // Crear una lista con el rol de desarrollador
        List<Role> roles = List.of(developerRole);

        // Crear el usuario con el rol de desarrollador
        var user = new User(command.username(), hashingService.encode(command.password()), roles);
        userRepository.save(user);

        Developer developer = new Developer(
                user,
                command.firstName(),
                command.lastName(),
                "No description provided.",
                "No country provided.",
                "No phone provided.",
                0,
                "No specialties provided.",
                "https://hwqkibwyspmfwkzjlumy.supabase.co/storage/v1/object/public/profile/profile.png"
        );
        developerRepository.save(developer);

        return Optional.of(user);
    }

    @Override
    public Optional<User> handle(SignUpEnterpriseCommand command) {
        if (userRepository.existsByUsername(command.username()))
            throw new RuntimeException("Username already exists");

        Role enterpriseRole= roleRepository.findByName(Roles.valueOf("ROLE_ENTERPRISE"))
                .orElseThrow(() -> new RuntimeException("Enterprise role not found"));
        List<Role> roles = List.of(enterpriseRole);

        var user = new User(command.username(), hashingService.encode(command.password()), roles);
        userRepository.save(user);

        Enterprise enterprise = new Enterprise(
                user,
                command.enterpriseName(),
                "No description provided.",
                "No country provided.",
                "11111111111",
                "999 999 999",
                "No website provided.",
                "https://cdn-icons-png.flaticon.com/512/3237/3237472.png",
                "No sector provided."
        );
        enterpriseRepository.save(enterprise);

        return Optional.of(user);
    }
}
