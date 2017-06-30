package awt.security;

import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.*;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SecurityFilterTest {
    private static final Collection<String> VALID_SCHEMES = new ArrayList<>();
    static {
	VALID_SCHEMES.add("SUPPORTED_SCHEME");
    }

    @Mock
    private ContainerRequestContext context;

    @Test
    public void testNullAuthorizationHeaderWithNullAuthentications() throws IOException {
	when(this.context.getHeaderString(HttpHeaders.AUTHORIZATION)).thenReturn(null);
	try {
	    new SecurityFilter(null).filter(this.context);
	    fail("Should throw NotAuthorizedException");
	} catch (final NotAuthorizedException expected) {
	    final List<Object> challenges = expected.getChallenges();
	    assertThat(challenges, is(nullValue()));

	    final Response response = expected.getResponse();
	    assertThat(response, is(notNullValue()));
	    assertThat(response.getStatus(), is(UNAUTHORIZED.getStatusCode()));
	}
    }

    @Test
    public void testNullAuthorizationHeaderWithSingleAuthentication() throws IOException {
	when(this.context.getHeaderString(HttpHeaders.AUTHORIZATION)).thenReturn(null);
	try {
	    final Secured auth1 = mock(Secured.class);
	    when(auth1.scheme()).thenReturn("SCHEME1");
	    when(auth1.realm()).thenReturn("REALM1");

	    final List<Secured> auths = new ArrayList<>();
	    auths.add(auth1);

	    new SecurityFilter(auths).filter(this.context);
	    fail("Should throw NotAuthorizedException");
	} catch (final NotAuthorizedException expected) {
	    final List<Object> challenges = expected.getChallenges();
	    assertThat(challenges, is(notNullValue()));
	    assertThat(challenges.size(), is(1));

	    final Response response = expected.getResponse();
	    assertThat(response, is(notNullValue()));
	    assertThat(response.getStatus(), is(UNAUTHORIZED.getStatusCode()));
	}
    }

    @Test
    public void testNullAuthorizationHeaderWithMultipleAuthentications() throws IOException {
	when(this.context.getHeaderString(HttpHeaders.AUTHORIZATION)).thenReturn(null);
	try {
	    final Secured auth1 = mock(Secured.class);
	    when(auth1.scheme()).thenReturn("SCHEME1");
	    when(auth1.realm()).thenReturn("REALM1");

	    final Secured auth2 = mock(Secured.class);
	    when(auth2.scheme()).thenReturn("SCHEME2");
	    when(auth2.realm()).thenReturn("REALM2");

	    final List<Secured> auths = new ArrayList<>();
	    auths.add(auth1);
	    auths.add(auth2);

	    new SecurityFilter(auths).filter(this.context);
	    fail("Should throw NotAuthorizedException");
	} catch (final NotAuthorizedException expected) {
	    final List<Object> challenges = expected.getChallenges();
	    assertThat(challenges, is(notNullValue()));
	    assertThat(challenges.size(), is(2));

	    final Response response = expected.getResponse();
	    assertThat(response, is(notNullValue()));
	    assertThat(response.getStatus(), is(UNAUTHORIZED.getStatusCode()));
	}
    }

    @Test
    public void testEmptyAuthorizationHeader() throws IOException {
	when(this.context.getHeaderString(HttpHeaders.AUTHORIZATION)).thenReturn("");
	try {
	    new SecurityFilter(Collections.emptyList()).filter(this.context);
	    fail("Should throw NotAuthorizedException");
	} catch (final NotAuthorizedException expected) {

	}
    }
}
