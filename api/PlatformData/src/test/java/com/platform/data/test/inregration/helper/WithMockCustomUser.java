package com.platform.data.test.inregration.helper;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 27.12.16.
 */
@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {
    String username() default "testuser@localhost.loc";
}
