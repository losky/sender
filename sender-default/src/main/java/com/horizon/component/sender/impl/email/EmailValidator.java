package com.horizon.component.sender.impl.email;

import com.horizon.component.sender.MimeMessage;
import com.horizon.component.sender.validation.AbstractValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * interface defined by
 *
 * @author ZhenZhong
 * @date 2016/6/16
 */
public class EmailValidator extends AbstractValidator<MimeMessage> {

    private static final Logger LOG = LoggerFactory.getLogger(EmailValidator.class);

    public EmailValidator() {
        requiredParams.add("send_type");
        requiredParams.add("subject");
        requiredParams.add("to");
    }

    @Override
    public boolean isSupported(String type) {
        return "email".equalsIgnoreCase(type) ? true : false;
    }

    /**
     * validate the invalid parameters
     *
     * @param mimeMessage
     *
     * @throws Exception
     */
    @Override
    public void validateInvalidParameters(MimeMessage mimeMessage) {
        if (!isValidEmail(mimeMessage.getTo()))
            throw new IllegalArgumentException("Illegal email!");
    }


}
