package com.platform.data.job;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 3/29/17.
 */
public class BatchExecutor {
    private final static Logger logger = LoggerFactory.getLogger(BatchExecutor.class);


    public void execute(BatchJob job, Object... args) throws PlatformException {
        do {
            logger.info("Job {} beginning to process {} page", job.getJobName(), job.getCurrentPage());
        } while (job.execute(args));
    }

}
