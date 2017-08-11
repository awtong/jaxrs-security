package awt.jaxrs.security;

public @interface Authorization {
    String key();

    String[] values();
}
