package com.agropapin.backend.iam.interfaces.acl;

import com.agropapin.backend.iam.domain.model.aggregates.User;
import com.agropapin.backend.iam.domain.model.commands.SignUpAdministratorCommand;
import com.agropapin.backend.iam.domain.model.commands.SignUpFarmerCommand;
import com.agropapin.backend.iam.domain.model.queries.GetUserByEmailQuery;
import com.agropapin.backend.iam.domain.model.queries.GetUserByIdQuery;
import com.agropapin.backend.iam.domain.services.UserCommandService;
import com.agropapin.backend.iam.domain.services.UserQueryService;
import com.agropapin.backend.iam.interfaces.rest.resources.UserResource;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * IamContextFacade
 * <p>
 *     This class is a facade for the IAM context. It provides a simple interface for other bounded contexts to interact with the
 *     IAM context.
 *     This class is a part of the ACL layer.
 * </p>
 */
@Service
public class IamContextFacade {
    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    public IamContextFacade(UserCommandService userCommandService, UserQueryService userQueryService) {
        this.userCommandService = userCommandService;
        this.userQueryService = userQueryService;
    }

    /**
     * Creates a farmer user with the given username, password, and farmer-specific information.
     * @param email The email of the user.
     * @param password The password of the user.
     * @param firstName The first name of the farmer.
     * @param lastName The last name of the farmer.
     * @param country The country of residence of the farmer.
     * @param phone The contact number of the farmer.
     * @return The id of the created user.
     */
    public UUID createFarmerUser(String email, String password, String firstName, String lastName, String country, String phone) {
        var signUpCommand = new SignUpFarmerCommand(email, password, firstName, lastName, country, phone);
        var result = userCommandService.handle(signUpCommand);
        if (result.isEmpty()) return null;
        return result.get().getId();
    }

    /**
     * Creates an administrator user with the provided personal and contact information.
     * @param email The email address of the administrator (used as username).
     * @param password The password for the administrator account.
     * @param firstName The first name of the administrator.
     * @param lastName The last name of the administrator.
     * @param country The country of residence for the administrator.
     * @param phone The phone number of the administrator.
     * @return The id of the created administrator user, or 0L if creation failed.
     */
    public UUID createAdministratorUser(String email, String password, String firstName,  String lastName, String country, String phone) {
        var signUpCommand = new SignUpAdministratorCommand(email, password, firstName, lastName, country, phone);
        var result = userCommandService.handle(signUpCommand);
        if (result.isEmpty()) return null;
        return result.get().getId();
    }

    /**
     * Fetches the id of the user with the given email.
     * @param email The email of the user.
     * @return The id of the user.
     */
    public UUID fetchUserIdByEmail(String email) {
        var getUserByEmailQuery = new GetUserByEmailQuery(email);
        var result = userQueryService.handle(getUserByEmailQuery);
        if (result.isEmpty()) return null;
        return result.get().getId();
    }

    public User getUserById(UUID userId) {
        var getUserByIdQuery = new GetUserByIdQuery(userId);
        var user = this.userQueryService.handle(getUserByIdQuery);
        return user.orElse(null);
    }

    public UUID getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new SecurityException("No context authentication found");
        }

        if (!authentication.isAuthenticated()) {
            throw new IllegalStateException("User not authenticated");
        }
        return ((UserResource) authentication.getPrincipal()).id();
    }

    public User getCurrentUser() {
        UUID currentUserId = getCurrentUserId();
        return getUserById(currentUserId);
    }

    public boolean hasCurrentUserRole(String role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_" + role));
    }
}
