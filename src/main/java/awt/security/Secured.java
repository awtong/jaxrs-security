package awt.security;

import java.lang.annotation.*;

import awt.security.authenticator.Authenticator;

/**
 * Holds information that maps an Authorization scheme to code to be executed if
 * that scheme is provided.
 *
 * Example
 * <p>
 * Given the following:
 * </p>
 * <p>
 * <code>
 *   &#64;Secured(scheme = "Basic", realm = "MY BASIC REALM", authenticator = StandardBasicAuthenticator.class)
 *   &#64;Secured(scheme = "Bearer", realm = "MY JWT REALM", authenticator = StandardJwtAuthenticator.class)
 * </code>
 * </p>
 *
 * <p>
 * If the request header "Authorization" has the "Basic scheme, it will execute
 * StandardBasicAuthenticator. If the aforementioned header has the "Bearer", it
 * will execute the StandardJwtAuthenticator code.
 * </p>
 *
 * @author awt
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
@Repeatable(Secureds.class)
public @interface Secured {
    /**
     * Scheme to associate.
     *
     * @return
     */
    String scheme();

    String realm();

    /**
     * Authentication code to run.
     *
     * @return
     */
    Class<? extends Authenticator> authenticator();

    Authorization[] authorizers() default {};
}
