package com.platform.data.job.utils;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 3/29/17.
 */
public class MailingPreprocessor {
    private final String BUG_FIXES_DIRECTIVE = "BUGTRACKER_BUG_FIXES";
    private final String NEW_TASKS_DIRECTIVE = "BUGTRACKER_NEW_TASKS";

    private final String template;


    public MailingPreprocessor(String template) {
        this.template = template;
    }


    public String createMessage(MailingKeyHolder key) {
        String message = this.template;

        message = (key.isCanReceiveNewTasksMailing()) ? MailingPreprocessor.removeDirective(message,
                NEW_TASKS_DIRECTIVE)
                : MailingPreprocessor.removeDirectiveContent(message, NEW_TASKS_DIRECTIVE);

        message = (key.isCanReceiveBugFixesMailing()) ? MailingPreprocessor.removeDirective(message,
                BUG_FIXES_DIRECTIVE)
                : MailingPreprocessor.removeDirectiveContent(message, BUG_FIXES_DIRECTIVE);

        return message;
    }


    private static String removeDirectiveContent(String message, String directive) {
        return message.replaceAll("\\#\\[" + directive + "\\].*\\#\\[\\/" + directive + "\\]", "");
    }


    private static String removeDirective(String message, String directive) {
        return message.replaceAll("\\#\\[" + directive + "\\]", "").replaceAll("\\#\\[\\/" + directive + "\\]", "");
    }
}
