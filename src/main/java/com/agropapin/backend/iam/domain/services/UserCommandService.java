package com.agropapin.backend.iam.domain.services;

import com.fromzero.backend.iam.domain.model.aggregates.User;
import com.fromzero.backend.iam.domain.model.commands.SignInCommand;
import com.fromzero.backend.iam.domain.model.commands.SignUpDeveloperCommand;
import com.fromzero.backend.iam.domain.model.commands.SignUpEnterpriseCommand;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Optional;


public interface UserCommandService {
    Optional<ImmutablePair<User, String>> handle(SignInCommand command);
    //Optional<User> handle(SignUpCommand command);
    Optional<User> handle(SignUpDeveloperCommand command);
    Optional<User> handle(SignUpEnterpriseCommand command);
}
