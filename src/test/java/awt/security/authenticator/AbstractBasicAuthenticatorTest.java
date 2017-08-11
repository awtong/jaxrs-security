package awt.security.authenticator;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Base64;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import awt.jaxrs.security.AuthorizationHeader;
import awt.jaxrs.security.authenticator.AbstractBasicAuthenticator;

@RunWith(MockitoJUnitRunner.class)
public class AbstractBasicAuthenticatorTest {

    @Mock
    private AuthorizationHeader header;

    @Test
    public void testValid() {
	final String encoded = Base64.getEncoder().encodeToString("username:password".getBytes());
	when(this.header.getToken()).thenReturn(encoded);

	final AbstractBasicAuthenticator authenticator = mock(AbstractBasicAuthenticator.class, CALLS_REAL_METHODS);
	authenticator.authenticate(this.header);

	verify(authenticator, times(1)).authenticate(any(String.class), any(String.class));
    }
}
