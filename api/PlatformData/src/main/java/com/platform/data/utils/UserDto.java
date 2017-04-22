package com.platform.data.utils;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 12/20/16.
 */
public class UserDto implements UserDetails {
    private Long id;

    private String username;

    private String token;

    private boolean enabled;

    private boolean expired;

    private boolean locked;

    private ZonedDateTime credentialsExpirationDate;

    private Role role;

    private List<GrantedAuthority> authorities;


    public UserDto(Long id, String username, String token, boolean enabled, boolean expired,
            boolean locked, ZonedDateTime credentialsExpirationDate, Role role)
    {
        this.id = id;
        this.username = username;
        this.token = token;
        this.enabled = enabled;
        this.expired = expired;
        this.locked = locked;
        this.credentialsExpirationDate = credentialsExpirationDate;
        this.setRole(role);
    }


    public UserDto(Trader trader, String token) {
        this(trader.getId(), trader.getEmail(), token, trader.isEnabled(), false, trader.isLocked(),
                trader.getCredentialsExpirationDate(), trader.getRole());
    }


    public UserDto(Trader trader) {
        this(trader, null);
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getToken() {
        return token;
    }


    public void setToken(String token) {
        this.token = token;
    }


    public boolean isExpired() {
        return expired;
    }


    public void setExpired(boolean expired) {
        this.expired = expired;
    }


    public boolean isLocked() {
        return locked;
    }


    public void setLocked(boolean locked) {
        this.locked = locked;
    }


    public ZonedDateTime getCredentialsExpirationDate() {
        return credentialsExpirationDate;
    }


    public void setCredentialsExpirationDate(ZonedDateTime credentialsExpirationDate) {
        this.credentialsExpirationDate = credentialsExpirationDate;
    }


    public Role getRole() {
        return role;
    }


    public void setRole(Role role) {
        this.authorities = new ArrayList<>(1);
        this.authorities.add(role);
        this.role = role;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }


    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }


    @Override
    public String getPassword() {
        return null;
    }


    @Override
    public String getUsername() {
        return username;
    }


    @Override
    public boolean isAccountNonExpired() {
        return !expired;
    }


    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }


    @Override
    public boolean isCredentialsNonExpired() {
        if (Role.ROLE_USER != role && Role.ROLE_ADMIN != role) {
            return credentialsExpirationDate.isAfter(ZonedDateTime.now());
        }
        return true;
    }


    @Override
    public boolean isEnabled() {
        return enabled;
    }


    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", token='" + token + '\'' +
                ", enabled=" + enabled +
                ", expired=" + expired +
                ", locked=" + locked +
                ", credentialsExpirationDate=" + credentialsExpirationDate +
                ", role=" + role +
                ", authorities=" + authorities +
                '}';
    }
}

