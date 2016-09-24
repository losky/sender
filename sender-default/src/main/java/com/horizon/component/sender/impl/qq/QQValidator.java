package com.horizon.component.sender.impl.qq;

import com.horizon.component.sender.MimeMessage;
import com.horizon.component.sender.validation.AbstractValidator;

/**
 * interface defined by
 *
 * @author ZhenZhong
 * @date 2016/9/7
 */
public class QQValidator extends AbstractValidator<MimeMessage> {
    @Override
    public boolean isSupported(String type) throws Exception {
        if (type.equalsIgnoreCase("QQ"))
            return true;
        return false;
    }

    /**
     * validate the invalid parameters
     *
     * @param mimeMessage
     *
     * @throws Exception
     */
    @Override
    public void validateInvalidParameters(MimeMessage mimeMessage) throws Exception {
        System.out.println("validate qq successful.");
    }
}
