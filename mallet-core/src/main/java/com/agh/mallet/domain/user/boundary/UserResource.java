package com.agh.mallet.domain.user.boundary;

import com.agh.mallet.api.UserInformationDTO;
import com.agh.mallet.api.UserLogInDTO;
import com.agh.mallet.api.UserRegistrationDTO;
import com.agh.mallet.domain.user.control.service.ConfirmationTokenService;
import com.agh.mallet.domain.user.control.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "user",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
public class UserResource {

    private static final String REGISTRATION_PATH = "/registration";
    private static final String EMAIL_CONFIRMATION_PATH = REGISTRATION_PATH + "/confirm";
    private static final String LOGIN_PATH = "/logIn";

    private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;

    public UserResource(UserService userService, ConfirmationTokenService confirmationTokenService) {
        this.userService = userService;
        this.confirmationTokenService = confirmationTokenService;
    }

    @PostMapping(REGISTRATION_PATH)
    public ResponseEntity<Object> register(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {
        userService.signUp(userRegistrationDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(EMAIL_CONFIRMATION_PATH)
    @ResponseBody
    public void confirm(@RequestParam("token") String token) {
        confirmationTokenService.confirmToken(token);

        //TODO ZWRACANIE HTMLA
      /*  modelAndView.setViewName("accountConfirmed");
        return modelAndView;*/
    }

    @PostMapping(path = LOGIN_PATH)
    public ResponseEntity<UserInformationDTO> logIn(@RequestBody UserLogInDTO userLogInDTO) {
        UserInformationDTO userInformationDTO = userService.logIn(userLogInDTO);

        return new ResponseEntity<>(userInformationDTO, HttpStatus.OK);
    }

}
