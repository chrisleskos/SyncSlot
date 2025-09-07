package com.syncslot.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping("/syncslot/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;
    private final LogoutService logoutService;

//    @Value("${spring.security.oauth2.client.registration.google.client-id}")
//    private String googleClientId;
//    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
//    private String googleClientSecret;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

//    @PostMapping("/refresh-token")
//    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        authService.refreshToken(request, response);
//    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        logoutService.logout(request, response, null);
        return ResponseEntity.ok("Logout");
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> checkIfLogged(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.ok(authService.checkIfLogged(request));
    }

/*    @GetMapping("/google")
    public ResponseEntity<AuthenticationResponse> getGoogleOAuthCode(@RequestParam("code") String code, @RequestParam("redirectUri") String redirectUri) {
        System.out.println("HIII xixixixiix\nYour code is: " + code);
        //TODO:
        //  Remove try catch and handle each error accordingly
        //  Implement the following in a service class

        // Get access token from code
        String tokenEndpoint = "https://oauth2.googleapis.com/token";

// Create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

// Define the body of the request including client_id, client_secret, code, redirect_uri, and grant_type
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("client_id", "yourClientId");
        requestBody.add("client_secret", "yourClientSecret");
        requestBody.add("code", code);
        requestBody.add("redirect_uri", "yourRedirectUri");
        requestBody.add("grant_type", "authorization_code");

// Create the HTTP entity
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

// Make the exchange
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(tokenEndpoint, HttpMethod.POST, requestEntity, String.class);

// Extract the access token from the response
        System.out.println(response.getBody());


        // Build RegisterRequest instance
        RegisterRequest regReq = RegisterRequest.builder().build();

        return ResponseEntity.ok(authService.authenticateGoogleAccount(regReq));
    }*/
}
