package com.platform.data.job.impl;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 3/29/17.
 */
public class MailingBatchJob implements BatchJob {

    private final int size;

    private final String fromEmailAddress;

    private final String mailingSubject;

    private int page;

    private String jobName;

    private MailingHolder mailingHolder;

    @Autowired
    private TraderService traderService;

    @Autowired
    private MailService mailService;


    public MailingBatchJob(int size, String fromEmailAddress, String mailingSubject) {
        this.size = size;
        this.fromEmailAddress = fromEmailAddress;
        this.mailingSubject = mailingSubject;
        this.jobName = "MailingJob-" + UUID.randomUUID().toString();
    }


    private void init(String templateMessage) {
        this.page = 0;
        this.mailingHolder = new MailingHolder(new MailingPreprocessor(templateMessage));
    }


    @Override
    public int getSize() {
        return size;
    }


    @Override
    public int getCurrentPage() {
        return page;
    }


    @Override
    public String getJobName() {
        return jobName;
    }


    @Override
    public boolean execute(Object... args) throws PlatformException {
        if (this.mailingHolder == null) {
            if (args == null || args.length != 1) {
                throw new PlatformException("Неверно переданы параметры!");
            }

            this.init(args[0].toString());
        }

        Pageable pageable = new PageRequest(page++, size, new Sort(Sort.Direction.ASC, "traderId"));
        Slice<Profile> profiles = traderService.findTraderProfilesForMailing(pageable);

        for (Profile profile : profiles) {
            this.mailingHolder.add(new MailingKeyHolder(profile.isCanReceiveGeneralMailing(),
                    profile.isCanReceiveBugFixesMailing(), profile.isCanReceiveNewTasksMailing()), profile.getTrader().getEmail());
        }

        mailingHolder.getMailingContext().values().forEach(mailingValueHolder -> {
            mailingValueHolder.getSubscribers().forEach(subscriber -> {
                mailService.sendEmail(subscriber, fromEmailAddress, mailingSubject, mailingValueHolder.getMessage(), null);
            });
        });

        mailingHolder.clean();
        return profiles.hasNext();
    }

}
