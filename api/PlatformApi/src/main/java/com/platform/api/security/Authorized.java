package com.platform.api.security;


/**
 * Метод, помеченный этой аннотацией, может быть вызван любым АВТОРИЗОВАННЫМ пользователем,
 * либо администратором.
 *
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 4/2/17.
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Secured({ Role.Get.ROLE_USER, Role.Get.ROLE_STARTER, Role.Get.ROLE_ADMIN })
public @interface Authorized {
}
