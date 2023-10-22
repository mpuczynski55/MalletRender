package com.agh.mallet.domain.user.user.boundary;

import com.agh.api.UserDetailDTO;
import com.agh.api.UserLogInDTO;
import com.agh.api.UserRegistrationDTO;
import com.agh.mallet.domain.user.user.control.service.ConfirmationTokenService;
import com.agh.mallet.domain.user.user.control.service.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User Resource")
@RestController
@RequestMapping(path = "user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserResource {

    private static final String REGISTRATION_PATH = "/registration";
    private static final String EMAIL_CONFIRMATION_PATH = REGISTRATION_PATH + "/confirm";
    private static final String LOGIN_PATH = "/login";

    private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;

    public UserResource(UserService userService, ConfirmationTokenService confirmationTokenService) {
        this.userService = userService;
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
    @PutMapping(EMAIL_CONFIRMATION_PATH)
    @ResponseBody
    public void confirm(@RequestParam("token") String token) {
        confirmationTokenService.confirmToken(token);

        //TODO ZWRACANIE HTMLA
      /*  modelAndView.setViewName("accountConfirmed");
        return modelAndView;*/
    }

    @Operation(
            summary = "Log in user"
    )
    @PostMapping(path = LOGIN_PATH)
    public ResponseEntity<UserDetailDTO> logIn(@RequestBody UserLogInDTO userLogInDTO) {
        UserDetailDTO userDetailDTO = userService.logIn(userLogInDTO);

        return new ResponseEntity<>(userDetailDTO, HttpStatus.OK);
    }

}
