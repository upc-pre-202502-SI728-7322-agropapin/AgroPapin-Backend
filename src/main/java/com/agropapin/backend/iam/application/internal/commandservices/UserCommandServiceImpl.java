package com.agropapin.backend.iam.application.internal.commandservices;

import com.agropapin.backend.iam.application.internal.outboundservices.hashing.HashingService;
import com.agropapin.backend.iam.application.internal.outboundservices.tokens.TokenService;
import com.agropapin.backend.iam.domain.model.aggregates.User;
import com.agropapin.backend.iam.domain.model.commands.SignInCommand;
import com.agropapin.backend.iam.domain.model.commands.SignUpAdministratorCommand;
import com.agropapin.backend.iam.domain.model.commands.SignUpFarmerCommand;
import com.agropapin.backend.iam.domain.model.entities.Role;
import com.agropapin.backend.iam.domain.model.valueobjects.Roles;
import com.agropapin.backend.iam.domain.services.UserCommandService;
import com.agropapin.backend.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import com.agropapin.backend.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import com.agropapin.backend.organizationManagement.interfaces.acl.OrganizationManagementFacade;
import jakarta.transaction.Transactional;
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
    private final OrganizationManagementFacade organizationManagementFacade;


    public UserCommandServiceImpl(UserRepository userRepository, RoleRepository roleRepository, HashingService hashingService, TokenService tokenService, OrganizationManagementFacade organizationManagementFacade) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.hashingService = hashingService;
        this.tokenService = tokenService;
        this.organizationManagementFacade = organizationManagementFacade;
    }

    @Override
    public Optional<ImmutablePair<User, String>> handle(SignInCommand command) {
        var user = userRepository.findByEmail(command.email());
        if (user.isEmpty())
            throw new RuntimeException("User not found");
        if (!hashingService.matches(command.password(), user.get().getPassword()))
            throw new RuntimeException("Invalid password");
        var token = tokenService.generateToken(user.get().getEmail());
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
    @Transactional
    public Optional<User> handle(SignUpFarmerCommand command) {
        if (userRepository.existsByEmail(command.email()))
            throw new RuntimeException("User with this email already exists");

        Role farmerRole = roleRepository.findByName(Roles.valueOf("ROLE_FARMER"))
                .orElseThrow(() -> new RuntimeException("Farmer role not found"));

        List<Role> roles = List.of(farmerRole);

        var user = new User(command.email(), hashingService.encode(command.password()), roles);
        userRepository.save(user);

        this.organizationManagementFacade.createFarmer(
                command.firstName(),
                command.lastName(),
                command.country(),
                command.phone(),
                user.getId()
        );

        return Optional.of(user);
    }

    @Override
    public Optional<User> handle(SignUpAdministratorCommand command) {
        if (userRepository.existsByEmail(command.email()))
            throw new RuntimeException("User with this email already exists");

        Role administratorRole= roleRepository.findByName(Roles.valueOf("ROLE_ADMINISTRATOR"))
                .orElseThrow(() -> new RuntimeException("Administrator role not found"));
        List<Role> roles = List.of(administratorRole);

        var user = new User(command.email(), hashingService.encode(command.password()), roles);
        userRepository.save(user);

        this.organizationManagementFacade.createAdministrator(
                command.firstName(),
                command.lastName(),
                command.country(),
                command.phone(),
                user.getId()
        );

        return Optional.of(user);
    }
}
