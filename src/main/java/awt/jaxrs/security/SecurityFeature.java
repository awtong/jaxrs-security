package awt.jaxrs.security;

import java.lang.reflect.Method;
import java.util.Arrays;

import javax.ws.rs.container.*;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

import org.slf4j.*;

/**
 * Dynamically adds security filter to any JAX-RS method tagged with the
 * <code>Secured</code> annotation.
 *
 * @author awt
 */
@Provider
public class SecurityFeature implements DynamicFeature {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityFeature.class);

    @Override
    public void configure(final ResourceInfo info, final FeatureContext context) {
	final Method method = info.getResourceMethod();
	final Secured[] authentications = method.getAnnotationsByType(Secured.class);
	if (authentications.length == 0) {
	    // no authentication required by method
	    return;
	}

	final SecurityFilter filter = new SecurityFilter(Arrays.asList(authentications));
	context.register(filter);

	for (final Secured authentication : authentications) {
	    LOGGER.debug("{}#{} secured by scheme '{}' using class '{}'", info.getResourceClass().getName(),
		    method.getName(), authentication.scheme(), authentication.authenticator().getName());
	}
    }
}
