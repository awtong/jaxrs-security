package awt.security;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.lang.reflect.Method;

import javax.ws.rs.container.*;
import javax.ws.rs.core.FeatureContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import awt.jaxrs.security.*;
import awt.jaxrs.security.authenticator.*;

@RunWith(MockitoJUnitRunner.class)
public class SecurityFeatureTest {
    @Mock
    private ResourceInfo info;

    @Mock
    private FeatureContext context;

    @Mock
    private Method method;

    @Test
    public void testNoAuthenticationAnnotations() throws NoSuchMethodException, SecurityException {
	when(this.method.getAnnotationsByType(Secured.class)).thenReturn(new Secured[] {});
	when(this.info.getResourceMethod()).thenReturn(this.method);

	final DynamicFeature feature = new SecurityFeature();
	feature.configure(this.info, this.context);
	verify(this.context, never()).register(any(SecurityFilter.class));
    }

    @Test
    public void testSingle() throws NoSuchMethodException, SecurityException {
	final Secured auth1 = mock(Secured.class);
	when(auth1.scheme()).thenReturn("SCHEME1");
	doReturn(DenyAllAuthenticator.class).when(auth1).authenticator();

	when(this.method.getAnnotationsByType(Secured.class)).thenReturn(new Secured[] { auth1 });
	when(this.method.getName()).thenReturn("testSingle");
	doReturn(this.getClass()).when(this.info).getResourceClass();

	when(this.info.getResourceMethod()).thenReturn(this.method);

	final DynamicFeature feature = new SecurityFeature();
	feature.configure(this.info, this.context);
	verify(this.context, times(1)).register(any(SecurityFilter.class));
    }

    @Test
    public void testMultiple() throws NoSuchMethodException, SecurityException {
	final Secured auth1 = mock(Secured.class);
	when(auth1.scheme()).thenReturn("SCHEME1");
	doReturn(DenyAllAuthenticator.class).when(auth1).authenticator();

	final Secured auth2 = mock(Secured.class);
	when(auth2.scheme()).thenReturn("SCHEME2");
	doReturn(AllowAllAuthenticator.class).when(auth2).authenticator();

	when(this.method.getAnnotationsByType(Secured.class)).thenReturn(new Secured[] { auth1, auth2 });
	when(this.method.getName()).thenReturn("testMultiple");
	doReturn(this.getClass()).when(this.info).getResourceClass();

	when(this.info.getResourceMethod()).thenReturn(this.method);

	final DynamicFeature feature = new SecurityFeature();
	feature.configure(this.info, this.context);
	verify(this.context, times(1)).register(any(SecurityFilter.class));
    }
}
