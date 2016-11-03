package com.horizon.component.sender.basic;

import com.horizon.component.sender.MimeMessage;
import com.horizon.component.sender.validation.AbstractValidator;

/**
 * interface defined by
 *
 * @author ZhenZhong
 * @date 2016/7/3
 */
public class DefaultValidator extends AbstractValidator<MimeMessage> {

    public DefaultValidator() {
        requiredParams.add("send_type");
        requiredParams.add("subject");
        requiredParams.add("to");
    }

    @Override
    public boolean isSupported(String type) {
        return true;
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

    }

}
