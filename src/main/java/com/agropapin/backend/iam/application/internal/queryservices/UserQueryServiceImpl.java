package com.agropapin.backend.iam.application.internal.queryservices;

import com.agropapin.backend.iam.domain.model.aggregates.User;
import com.agropapin.backend.iam.domain.model.queries.GetAllUsersQuery;
import com.agropapin.backend.iam.domain.model.queries.GetUserByEmailQuery;
import com.agropapin.backend.iam.domain.model.queries.GetUserByIdQuery;
import com.agropapin.backend.iam.domain.services.UserQueryService;
import com.agropapin.backend.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserQueryServiceImpl implements UserQueryService {

    private final UserRepository userRepository;

    public UserQueryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public List<User> handle(GetAllUsersQuery query) {
        return userRepository.findAll();
    }

    /**
     * This method is used to handle {@link GetUserByIdQuery} query.
     * @param query {@link GetUserByIdQuery} instance.
     * @return {@link Optional} of {@link User} instance.
     * @see GetUserByIdQuery
     */
    @Override
    public Optional<User> handle(GetUserByIdQuery query) {
        return userRepository.findById(query.userId());
    }

    /**
     * Retrieves a user by their email address.
     * @param query {@link GetUserByEmailQuery} containing the email address to search for.
     * @return {@link Optional} containing the found {@link User} if exists, otherwise empty Optional.
     */
    @Override
    public Optional<User> handle(GetUserByEmailQuery query) {
        return userRepository.findByUsername(query.email());
    }
}