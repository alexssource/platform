package com.platform.data.domain;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 12/26/16.
 */
@Entity
@Table(name = "PAYMENT_TRANSACTION")
public class PaymentTransaction {

    /**
     * Id транзакции в ... Platform
     */
    @Id
    @Column(name = "TRANSACTION_ID", nullable = false)
    private UUID transactionId;

    /**
     * Валюта
     */
    @Column(name = "CURRENCY", nullable = false)
    private Currency currency;

    /**
     * Сумма платежа
     */
    @Column(name = "AMOUNT", nullable = false)
    private Double amount;

    /**
     * Описание платежа
     */
    @Column(name = "DESCRIPTION")
    private String description;

    /**
     * Статус платежа
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "TRANSACTION_STATUS", nullable = false)
    private TransactionStatus transactionStatus;

    /**
     * Плательщик
     */
    @ManyToOne
    @JoinColumn(name = "TRADER_ID", nullable = false)
    private Trader trader;

    /**
     * Дата создания запроса на платеж от клиента
     */
    @Column(name = "REQUEST_CREATION_DATE", nullable = false)
    private ZonedDateTime requestCreationDate;

    /**
     * IP адрес клиента, начавшего процесс оплаты
     */
    @Column(name = "REQUEST_CLIENT_IP_ADDRESS", nullable = false)
    private String requestIpAddress;

    /**
     * Приобретаемый тариф
     */
    @ManyToOne
    @JoinColumn(name = "TARIFF_ID", nullable = false)
    private Tariff tariff;

    /**
     * Id магазина
     */
    @Column(name = "MERCHANT_ID")
    private String merchantId;

    /**
     * Локаль
     */
    @Column(name = "LOCALE", nullable = false)
    private Locale locale;


    /**
     * Сумма, которую покупатель оплатил
     */
    @Column(name = "WALLET_AMOUNT")
    private Double walletAmount;

    /**
     * Размер комиссии, взысканной с покупателя
     */
    @Column(name = "WALLET_COMMISSION_AMOUNT")
    private Double walletCommissionAmount;

    /**
     * Идентификатор валюты, ISO 4217
     */
    @Column(name = "WALLET_CURRENCY_ID")
    private Integer walletCurrencyId;

    /**
     * Идентификатор покупателя в системе Wallet One
     */
    @Column(name = "WALLET_CUSTOMER_ID")
    private String walletCustomerId;

    /**
     * Идентификатор заказа в системе Wallet One
     */
    @Column(name = "WALLET_ORDER_ID")
    private String walletOrderId;

    /**
     * Дата истечения заказа в системе Wallet One
     */
    @Column(name = "WALLET_EXPIRATION_DATE")
    private ZonedDateTime walletExpirationDate;

    /**
     * Дата создания заказа в системе Wallet One
     */
    @Column(name = "WALLET_CREATED_DATE")
    private ZonedDateTime walletCreatedDate;

    /**
     * Дата обновления заказа в системе Wallet One
     */
    @Column(name = "WALLET_UPDATED_DATE")
    private ZonedDateTime walletUpdatedDate;

    /**
     * Состояние заказа:
     * - Accepted - заказ оплачен
     */
    @Column(name = "WALLET_ORDER_STATE")
    private String walletOrderState;


    public PaymentTransaction() {

    }


    public PaymentTransaction(UUID transactionId, String merchantId, Currency currency, double amount, String description,
            Locale locale, TransactionStatus transactionStatus, Trader trader, ZonedDateTime requestCreationDate,
            String requestIpAddress, Tariff tariff)
    {
        this.transactionId = transactionId;
        this.merchantId = merchantId;
        this.currency = currency;
        this.amount = amount;
        this.description = description;
        this.locale = locale;
        this.transactionStatus = transactionStatus;
        this.trader = trader;
        this.requestCreationDate = requestCreationDate;
        this.requestIpAddress = requestIpAddress;
        this.tariff = tariff;
    }


    public UUID getTransactionId() {
        return transactionId;
    }


    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }


    public Currency getCurrency() {
        return currency;
    }


    public void setCurrency(Currency currency) {
        this.currency = currency;
    }


    public Double getAmount() {
        return amount;
    }


    public void setAmount(Double amount) {
        this.amount = amount;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }


    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }


    public Trader getTrader() {
        return trader;
    }


    public void setTrader(Trader trader) {
        this.trader = trader;
    }


    public ZonedDateTime getRequestCreationDate() {
        return requestCreationDate;
    }


    public void setRequestCreationDate(ZonedDateTime requestCreationDate) {
        this.requestCreationDate = requestCreationDate;
    }


    public String getRequestIpAddress() {
        return requestIpAddress;
    }


    public void setRequestIpAddress(String requestIpAddress) {
        this.requestIpAddress = requestIpAddress;
    }


    public Tariff getTariff() {
        return tariff;
    }


    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }


    public String getMerchantId() {
        return merchantId;
    }


    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }


    public Locale getLocale() {
        return locale;
    }


    public void setLocale(Locale locale) {
        this.locale = locale;
    }


    public Double getWalletAmount() {
        return walletAmount;
    }


    public void setWalletAmount(Double walletAmount) {
        this.walletAmount = walletAmount;
    }


    public Double getWalletCommissionAmount() {
        return walletCommissionAmount;
    }


    public void setWalletCommissionAmount(Double walletCommissionAmount) {
        this.walletCommissionAmount = walletCommissionAmount;
    }


    public Integer getWalletCurrencyId() {
        return walletCurrencyId;
    }


    public void setWalletCurrencyId(Integer walletCurrencyId) {
        this.walletCurrencyId = walletCurrencyId;
    }


    public String getWalletCustomerId() {
        return walletCustomerId;
    }


    public void setWalletCustomerId(String walletCustomerId) {
        this.walletCustomerId = walletCustomerId;
    }


    public String getWalletOrderId() {
        return walletOrderId;
    }


    public void setWalletOrderId(String walletOrderId) {
        this.walletOrderId = walletOrderId;
    }


    public ZonedDateTime getWalletExpirationDate() {
        return walletExpirationDate;
    }


    public void setWalletExpirationDate(ZonedDateTime walletExpirationDate) {
        this.walletExpirationDate = walletExpirationDate;
    }


    public ZonedDateTime getWalletCreatedDate() {
        return walletCreatedDate;
    }


    public void setWalletCreatedDate(ZonedDateTime walletCreatedDate) {
        this.walletCreatedDate = walletCreatedDate;
    }


    public ZonedDateTime getWalletUpdatedDate() {
        return walletUpdatedDate;
    }


    public void setWalletUpdatedDate(ZonedDateTime walletUpdatedDate) {
        this.walletUpdatedDate = walletUpdatedDate;
    }


    public String getWalletOrderState() {
        return walletOrderState;
    }


    public void setWalletOrderState(String walletOrderState) {
        this.walletOrderState = walletOrderState;
    }
}
