package com.agh.mallet.domain.user.user.boundary;

import com.agh.api.GroupBasicDTO;
import com.agh.api.UserDTO;
import com.agh.api.UserDetailDTO;
import com.agh.api.UserLogInDTO;
import com.agh.api.UserRegistrationDTO;
import com.agh.mallet.domain.user.user.control.service.UserGroupService;
import com.agh.mallet.domain.user.user.control.service.ConfirmationTokenService;
import com.agh.mallet.domain.user.user.control.service.UserService;
import com.agh.mallet.domain.user.user.control.utils.EmailTemplateProvider;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@Tag(name = "User Resource")
@RestController
@RequestMapping(path = "user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserResource {

    private static final String REGISTRATION_PATH = "/registration";
    private static final String EMAIL_CONFIRMATION_PATH = REGISTRATION_PATH + "/confirm";
    private static final String LOGIN_PATH = "/login";
    private static final String GROUP_PATH = "/group";

    private final UserService userService;
    private final UserGroupService userGroupService;
    private final ConfirmationTokenService confirmationTokenService;

    public UserResource(UserService userService, UserGroupService userGroupService, ConfirmationTokenService confirmationTokenService) {
        this.userService = userService;
        this.userGroupService = userGroupService;
        this.confirmationTokenService = confirmationTokenService;
    }

    @Operation(
            summary = "Register user"
    )
    @PostMapping(REGISTRATION_PATH)
    public ResponseEntity<Object> register(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {
        userService.signUp(userRegistrationDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Hidden
    @GetMapping(value = EMAIL_CONFIRMATION_PATH, produces = MediaType.TEXT_HTML_VALUE)
    public String confirm(@RequestParam("token") String token) {
        confirmationTokenService.confirmToken(token);

        return EmailTemplateProvider.getEmailConfirmedTemplate();
    }

    @Operation(
            summary = "Log in user"
    )
    @PostMapping(path = LOGIN_PATH)
    public ResponseEntity<UserDetailDTO> logIn(@RequestBody UserLogInDTO userLogInDTO) {
        UserDetailDTO userDetailDTO = userService.logIn(userLogInDTO);

        return new ResponseEntity<>(userDetailDTO, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete user providing username"
    )
    @GetMapping
    public ResponseEntity<List<UserDTO>> get(@RequestParam("username") String username) {
        List<UserDTO> userDTOS = userService.get(username);

        return new ResponseEntity<>(userDTOS, HttpStatus.OK);
    }

    @Operation(
            summary = "Get users providing username"
    )
    @DeleteMapping
    public ResponseEntity<List<UserDTO>> delete(@RequestParam("id") long id, Principal principal) {
         userService.delete(id, principal.getName());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "Get all user's groups",
            description = "Get all user's groups. If the result exceeds limit param then next chunk uri is returned"
    )
    @GetMapping(path = GROUP_PATH)
    @ResponseBody
    public ResponseEntity<GroupBasicDTO> get(@RequestParam(name = "startPosition", defaultValue = "0") int startPosition,
                                             @RequestParam(name = "limit", defaultValue = "10") int limit,
                                             Principal principal) {
        GroupBasicDTO groupBasicDTO = userGroupService.get(startPosition, limit, principal.getName());

        return new ResponseEntity<>(groupBasicDTO, HttpStatus.OK);
    }
}
