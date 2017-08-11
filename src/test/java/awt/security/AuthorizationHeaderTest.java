package awt.security;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import javax.ws.rs.BadRequestException;

import org.junit.Test;

import awt.jaxrs.security.AuthorizationHeader;

public class AuthorizationHeaderTest {

    /**
     * Tests scenario where <code>null</code> parameter passed into constructor.
     */
    @Test
    public void testNullParameter() {
	try {
	    new AuthorizationHeader(null);
	    fail("Should throw NullPointerException");
	} catch (final NullPointerException expected) {

	}
    }

    /**
     * Tests scenario where invalid header value is passed into constructor.
     */
    @Test
    public void testTooManyParts() {
	try {
	    new AuthorizationHeader("ONE TWO THREE");
	    fail("Should throw BadRequestException");
	} catch (final BadRequestException expected) {

	}
    }

    /**
     * Tests scenario where valid header value is passed into constructor.
     */
    @Test
    public void testValid() {
	final AuthorizationHeader header = new AuthorizationHeader("ONE TWO");
	assertThat(header.getScheme(), is("ONE"));
	assertThat(header.getToken(), is("TWO"));
    }
}
