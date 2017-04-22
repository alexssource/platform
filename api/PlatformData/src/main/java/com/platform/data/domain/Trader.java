package com.platform.data.domain;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 12/20/16.
 */
@Entity
@Table(name = "TRADER")
@SequenceGenerator(name = "SQ_USERS_GENERATOR", sequenceName = "SQ_USERS", allocationSize = 1)
public class Trader {

    @Id
    @GeneratedValue(generator = "SQ_USERS_GENERATOR")
    @Column(name = "ID")
    private Long id;

    @Email
    @NotNull
    @Size(min = 4, max = 32)
    @Column(name = "EMAIL")
    private String email;

    @NotEmpty
    @Length(min = 3, max = 128)
    @Column(name = "PASSWORD", nullable = false, length = 128)
    private String password;

    @Column(name = "IS_ENABLED")
    private boolean enabled;

    @Column(name = "CREATED_AT", nullable = false)
    private ZonedDateTime createdAt;

    @Column(name = "ROLE", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "IS_LOCKED", nullable = false)
    private boolean locked;

    @Column(name = "CREDENTIALS_EXPIRATION_DATE", nullable = false)
    private ZonedDateTime credentialsExpirationDate;

    @Column(name = "TOKEN_EXPIRATION_DATE", nullable = false)
    private ZonedDateTime tokenExpirationDate;

    @OneToOne(mappedBy = "trader", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Profile profile;


    public Trader() {
    }


    public Trader(String email, String password, boolean enabled, ZonedDateTime createdAt, Role role, boolean locked,
            ZonedDateTime credentialsExpirationDate, ZonedDateTime tokenExpirationDate)
    {
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.createdAt = createdAt;
        this.role = role;
        this.locked = locked;
        this.credentialsExpirationDate = credentialsExpirationDate;
        this.tokenExpirationDate = tokenExpirationDate;
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public boolean isEnabled() {
        return enabled;
    }


    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }


    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }


    public Role getRole() {
        return role;
    }


    public void setRole(Role role) {
        this.role = role;
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


    public ZonedDateTime getTokenExpirationDate() {
        return tokenExpirationDate;
    }


    public void setTokenExpirationDate(ZonedDateTime tokenExpirationDate) {
        this.tokenExpirationDate = tokenExpirationDate;
    }


    public Profile getProfile() {
        return profile;
    }


    public void setProfile(Profile profile) {
        this.profile = profile;
    }


    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, enabled, createdAt, role, locked, credentialsExpirationDate,
                tokenExpirationDate);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Trader trader = (Trader) o;
        return enabled == trader.enabled &&
                locked == trader.locked &&
                Objects.equals(id, trader.id) &&
                Objects.equals(email, trader.email) &&
                Objects.equals(password, trader.password) &&
                Objects.equals(createdAt, trader.createdAt) &&
                role == trader.role &&
                Objects.equals(credentialsExpirationDate, trader.credentialsExpirationDate) &&
                Objects.equals(tokenExpirationDate, trader.tokenExpirationDate);
    }
}
