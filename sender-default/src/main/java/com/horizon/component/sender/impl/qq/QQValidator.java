package com.horizon.component.sender.impl.qq;

import com.horizon.component.sender.MimeMessage;
import com.horizon.component.sender.validation.AbstractValidator;

/**
 * interface defined by
 *
 * @author ZhenZhong
 */
public class QQValidator extends AbstractValidator<MimeMessage> {
    @Override
    public boolean isSupported(String type) {
        return type.equalsIgnoreCase("QQ");
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
        System.out.println("validate qq successful.");
    }
}
