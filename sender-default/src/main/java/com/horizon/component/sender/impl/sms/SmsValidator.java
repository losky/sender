package com.horizon.component.sender.impl.sms;


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
public class SmsValidator extends AbstractValidator<MimeMessage> {

    private static final Logger LOG = LoggerFactory.getLogger(SmsValidator.class);

    public SmsValidator() {
        requiredParams.add("send_type");
        requiredParams.add("to");
    }

    @Override
    public boolean isSupported(String type) {
        return "sms".equalsIgnoreCase(type);
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
        if (!isValidMobileNo(mimeMessage.getTo()))
            throw new IllegalArgumentException("Illegal mobile phone!");
    }
}
