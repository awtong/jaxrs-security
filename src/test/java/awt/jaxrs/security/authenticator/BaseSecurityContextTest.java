package awt.jaxrs.security.authenticator;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.*;

import javax.ws.rs.core.SecurityContext;

import org.junit.Test;

public class BaseSecurityContextTest {

    @Test
    public void testIsUserInRole() {
	final List<String> roles = Arrays.asList("Role1", "Role2");
	final SecurityContext ctx = new BaseSecurityContext(null, roles, null, true);
	assertThat(ctx.isUserInRole("notfound"), is(false));
	assertThat(ctx.isUserInRole("Role1"), is(true));
	assertThat(ctx.isUserInRole("Role2"), is(true));
    }

    @Test
    public void testNullRolesParameterShouldNotThrowNPE() {
	// null roles parameter should not throw NPE when calling isUserInRole
	final SecurityContext ctx = new BaseSecurityContext(null, null, null, true);
	assertThat(ctx.isUserInRole("somerole"), is(false));
    }
}
