package fplhn.udpm.identity.infrastructure.security.oauth2;


import com.fasterxml.jackson.core.JsonProcessingException;
import fplhn.udpm.identity.entity.Client;
import fplhn.udpm.identity.entity.Module;
import fplhn.udpm.identity.infrastructure.security.repository.ModuleAuthRepository;
import fplhn.udpm.identity.infrastructure.security.response.TokenUriResponse;
import fplhn.udpm.identity.infrastructure.security.service.RefreshTokenService;
import fplhn.udpm.identity.infrastructure.security.service.TokenProvider;
import fplhn.udpm.identity.util.CookieUtils;
import fplhn.udpm.identity.util.GenerateClientUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Optional;

import static fplhn.udpm.identity.infrastructure.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private TokenProvider tokenProvider;

    private RefreshTokenService refreshTokenService;

    private HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    private final ModuleAuthRepository moduleAuthRepository;

    @Value("${app.default-target-url-identity}")
    private String DEFAULT_TARGET_URL_IDENTITY;


    @Autowired
    OAuth2AuthenticationSuccessHandler(
            TokenProvider tokenProvider,
            HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository,
            ModuleAuthRepository moduleAuthRepository,
            RefreshTokenService refreshTokenService
    ) {
        this.tokenProvider = tokenProvider;
        this.httpCookieOAuth2AuthorizationRequestRepository = httpCookieOAuth2AuthorizationRequestRepository;
        this.moduleAuthRepository = moduleAuthRepository;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        try {
            Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                    .map(Cookie::getValue);
            logger.info("Redirect URI: " + redirectUri.get());
            if (redirectUri.isEmpty()) {
                return UriComponentsBuilder
                        .fromUriString(DEFAULT_TARGET_URL_IDENTITY)
                        .queryParam("error", "Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication")
                        .build().toUriString();
            }

            Optional<Module> module = moduleAuthRepository.findByUrl(redirectUri.get());
            if (module.isEmpty()) {
                return UriComponentsBuilder
                        .fromUriString(DEFAULT_TARGET_URL_IDENTITY)
                        .queryParam("error", "Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication")
                        .build().toUriString();
            }

            Optional<Client> client = Optional.ofNullable(module.get().getClient());

            if (client.isEmpty()) {
                return UriComponentsBuilder
                        .fromUriString(DEFAULT_TARGET_URL_IDENTITY)
                        .queryParam("error", "Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication")
                        .build().toUriString();
            }

            SecretKey tokenSecret = GenerateClientUtils.generateJwtSecretKey(client.get().getClientId(), client.get().getClientSecret());
            tokenProvider.setTokenSecret(tokenSecret);
            String targetUrl = redirectUri.orElse(getDefaultTargetUrl());
            String token = tokenProvider.createToken(authentication, module.get().getUrl());
            String refreshToken = refreshTokenService.createRefreshToken(authentication).getRefreshToken();
            TokenUriResponse tokenUriResponse = new TokenUriResponse(token, refreshToken);
            return UriComponentsBuilder.fromUriString(targetUrl)
                    .queryParam("Token", tokenUriResponse.getTokenAuthorization())
                    .build().toUriString();
        } catch (BadRequestException | JsonProcessingException e) {
            e.printStackTrace(System.out);
            return UriComponentsBuilder
                    .fromUriString(DEFAULT_TARGET_URL_IDENTITY)
                    .queryParam("error", "Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication")
                    .build().toUriString();
        }
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

}
