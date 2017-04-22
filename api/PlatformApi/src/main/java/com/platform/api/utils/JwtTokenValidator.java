package com.platform.api.utils;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 12/20/16.
 */
public class JwtTokenValidator {
    private final static Logger logger = LoggerFactory.getLogger(JwtTokenValidator.class);


    /**
     * Tries to parse specified String as a JWT token. If successful, returns User object with username, id and role prefilled (extracted from token).
     * If unsuccessful (token is invalid or not containing all required user properties), simply returns null.
     *
     * @param token the JWT token to parse
     * @return the User object extracted from specified token or null if a token is invalid.
     */
    public UserDto parseToken(String token) {
        UserDto u = null;

        try {
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

            Long id = Long.parseLong(String.valueOf(body.get("userId")));
            String email = body.getSubject();
            Role role = Role.valueOf(String.valueOf(body.get("role")));
            ZonedDateTime credentialsExpirationDate = ZonedDateTime.ofInstant(
                    Instant.ofEpochSecond(Long.parseLong(String.valueOf(body.get("credentialsExpirationDate")))),
                    ZoneId.systemDefault());

            u = new UserDto(id, email, token, true, false, false,
                    credentialsExpirationDate, role);

        } catch (JwtException | IllegalArgumentException | NullPointerException e) {
            logger.warn("Could not validate the auth token.");
        }

        return u;
    }
}

