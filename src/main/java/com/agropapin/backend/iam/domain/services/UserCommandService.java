package com.agropapin.backend.iam.domain.services;

import com.agropapin.backend.iam.domain.model.aggregates.User;
import com.agropapin.backend.iam.domain.model.commands.SignInCommand;
import com.agropapin.backend.iam.domain.model.commands.SignUpAdministratorCommand;
import com.agropapin.backend.iam.domain.model.commands.SignUpFarmerCommand;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Optional;


public interface UserCommandService {
    Optional<ImmutablePair<User, String>> handle(SignInCommand command);
    //Optional<User> handle(SignUpCommand command);
    Optional<User> handle(SignUpFarmerCommand command);
    Optional<User> handle(SignUpAdministratorCommand command);
}
