package awt.security;

public @interface Authorization {
    String key();

    String[] values();
}
