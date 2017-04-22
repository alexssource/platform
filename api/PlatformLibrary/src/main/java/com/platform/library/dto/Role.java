package com.platform.library.dto;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 17.12.16.
 */
public enum Role implements GrantedAuthority {
    ROLE_USER {
        @Override
        public String getAuthority() {
            return Get.ROLE_USER;
        }
    },
    ROLE_ADMIN {
        @Override
        public String getAuthority() {
            return Get.ROLE_ADMIN;
        }
    },
    ROLE_STARTER {
        @Override
        public String getAuthority() {
            return Get.ROLE_STARTER;
        }
    };
    public static class Get {
        public final static String ROLE_USER = "ROLE_USER";

        public final static String ROLE_ADMIN = "ROLE_ADMIN";
    }
}
