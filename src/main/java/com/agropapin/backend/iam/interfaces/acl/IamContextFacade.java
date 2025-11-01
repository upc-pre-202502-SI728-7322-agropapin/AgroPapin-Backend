package com.agropapin.backend.iam.interfaces.acl;

import com.agropapin.backend.iam.domain.model.aggregates.User;
import com.agropapin.backend.iam.domain.model.commands.SignUpAdministratorCommand;
import com.agropapin.backend.iam.domain.model.commands.SignUpFarmerCommand;
import com.agropapin.backend.iam.domain.model.queries.GetUserByEmailQuery;
import com.agropapin.backend.iam.domain.model.queries.GetUserByIdQuery;
import com.agropapin.backend.iam.domain.services.UserCommandService;
import com.agropapin.backend.iam.domain.services.UserQueryService;
import com.agropapin.backend.iam.infrastructure.authorization.sfs.model.UserDetailsImpl;
import com.agropapin.backend.iam.interfaces.rest.resources.UserResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private final UserQueryService userQueryService;

    public IamContextFacade(UserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }

    /**
     * Fetches the id of the user with the given email.
     * @param email The email of the user.
     * @return The id of the user.
     */
    public String fetchUserIdByEmail(String email) {
        var getUserByEmailQuery = new GetUserByEmailQuery(email);
        var result = userQueryService.handle(getUserByEmailQuery);
        if (result.isEmpty()) return null;
        return result.get().getId();
    }

    public User getUserById(String userId) {
        var getUserByIdQuery = new GetUserByIdQuery(userId);
        var user = this.userQueryService.handle(getUserByIdQuery);
        return user.orElse(null);
    }

    public String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new SecurityException("No context authentication found");
        }

        if (!authentication.isAuthenticated()) {
            throw new IllegalStateException("User not authenticated");
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String email = userDetails.getUsername();

        User user = userQueryService.handle(new GetUserByEmailQuery(email))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return user.getId();
    }

    public User getCurrentUser() {
        String currentUserId = getCurrentUserId();
        return getUserById(currentUserId);
    }

    public boolean hasCurrentUserRole(String role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_" + role));
    }
}
