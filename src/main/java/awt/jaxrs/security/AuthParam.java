package awt.jaxrs.security;

public @interface AuthParam {
    String key();

    String[] values();
}
