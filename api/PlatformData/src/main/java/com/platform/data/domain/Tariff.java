package com.platform.data.domain;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 27.12.16.
 */
@Entity
@Table(name = "TARIFF")
@NamedQueries({
        @NamedQuery(name = Tariff.QUERY_FIND_BY_NAME, query = "select t from Tariff t where t.name = :name")
})
@SequenceGenerator(name = "SQ_TARIFF_GENERATOR", sequenceName = "SQ_TARIFF", allocationSize = 1)
public class Tariff {
    public static final String QUERY_FIND_BY_NAME = "Tariff.findByName";

    @Id
    @GeneratedValue(generator = "SQ_TARIFF_GENERATOR")
    @Column(name = "ID")
    private Integer id;

    @NotNull
    @Size(min = 2, max = 16)
    @Column(name = "NAME", nullable = false)
    private String name;

    @NotNull
    @Min(1)
    @Column(name = "PRICE", nullable = false)
    private Double price;

    @NotNull
    @Size(min = 2, max = 64)
    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "TARIFF_ROLE", nullable = false)
    private Role tariffRole;


    public Tariff() {
    }


    public Tariff(String name, Double price, String description, Role tariffRole) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.tariffRole = tariffRole;
    }


    public Tariff(Integer id, String name, Double price, String description, Role tariffRole) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.tariffRole = tariffRole;
    }


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public Double getPrice() {
        return price;
    }


    public void setPrice(Double price) {
        this.price = price;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public Role getTariffRole() {
        return tariffRole;
    }


    public void setTariffRole(Role tariffRole) {
        this.tariffRole = tariffRole;
    }
}
