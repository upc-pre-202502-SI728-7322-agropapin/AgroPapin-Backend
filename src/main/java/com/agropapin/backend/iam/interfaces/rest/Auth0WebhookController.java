package com.agropapin.backend.iam.interfaces.rest;

import com.agropapin.backend.iam.domain.services.UserCommandService;
import com.agropapin.backend.iam.interfaces.rest.resources.*;
import com.agropapin.backend.iam.interfaces.rest.transform.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AuthenticationController
 * <p>
 *     This controller is responsible for handling authentication requests.
 *     It exposes two endpoints:
 *     <ul>
 *         <li>POST /api/v1/auth/sign-in</li>
 *         <li>POST /api/v1/auth/sign-up</li>
 *     </ul>
 * </p>
 */
@RestController
@RequestMapping("/internal/webhooks")
public class Auth0WebhookController {
    private final UserCommandService userCommandService;

    public Auth0WebhookController(UserCommandService userCommandService) {
        this.userCommandService = userCommandService;
    }


    @PostMapping("/user-registered")
    public ResponseEntity<Void> handleUserRegistration(@RequestBody Auth0RegistrationWebhookResource registrationData) {

        try {
            String roleType = registrationData.roleType().toUpperCase();

            switch (roleType) {
                case "FARMER":
                    var signUpCommand = SignUpFarmerCommandFromResourceAssembler.toCommandFromResource(registrationData);
                    var user = userCommandService.handle(signUpCommand);

                    break;
                case "ADMINISTRATOR":
                    var signUpAdminCommand = SignUpAdministratorCommandFromResourceAssembler.toCommandFromResource(registrationData);
                    var adminUser = userCommandService.handle(signUpAdminCommand);
                    break;
                default:
                    System.err.println("Webhook recibido con ROLE_TYPE no válido: " + registrationData.roleType());
                    return ResponseEntity.badRequest().build();
            }
        } catch (Exception e) {
            System.err.println("Error al procesar el Webhook de registro: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok().build();
    }
}
