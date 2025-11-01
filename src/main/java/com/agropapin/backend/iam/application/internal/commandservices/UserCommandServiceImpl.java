package com.agropapin.backend.iam.application.internal.commandservices;

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
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final OrganizationManagementFacade organizationManagementFacade;


    public UserCommandServiceImpl(UserRepository userRepository, RoleRepository roleRepository, OrganizationManagementFacade organizationManagementFacade) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.organizationManagementFacade = organizationManagementFacade;
    }

    @Override
    public Optional<User> handle(SignUpFarmerCommand command) {
        if (userRepository.existsByUsername(command.email()))
            throw new RuntimeException("User with this email already exists");

//        Role farmerRole = roleRepository.findByName(Roles.valueOf("ROLE_FARMER"))
//                .orElseThrow(() -> new RuntimeException("Farmer role not found"));
//
//        List<Role> roles = List.of(farmerRole);
//
//        var user = new User(command.email(), hashingService.encode(command.password()), roles);
//        userRepository.save(user);

        this.organizationManagementFacade.createFarmer(
                command.email(),
                command.userId()
        );

        return Optional.empty();
    }

    @Override
    public Optional<User> handle(SignUpAdministratorCommand command) {
        if (userRepository.existsByUsername(command.email()))
            throw new RuntimeException("User with this email already exists");

//        Role administratorRole= roleRepository.findByName(Roles.valueOf("ROLE_ADMINISTRATOR"))
//                .orElseThrow(() -> new RuntimeException("Administrator role not found"));
//        List<Role> roles = List.of(administratorRole);
//
//        var user = new User(command.email(), hashingService.encode(command.password()), roles);
//        userRepository.save(user);

        this.organizationManagementFacade.createAdministrator(
                command.email(),
                command.userId()
        );

        return Optional.empty();
    }
}
