package com.horizon.component.sender.voice;


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
public class VoiceValidator extends AbstractValidator<MimeMessage> {

    private static final Logger LOG = LoggerFactory.getLogger(VoiceValidator.class);

    public VoiceValidator() {
        requiredParams.add("send_type");
        requiredParams.add("to");
    }

    @Override
    public boolean isSupported(String type) throws Exception {
        return "voice".equalsIgnoreCase(type) ? true : false;
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
        if (!isValidMobileNo(mimeMessage.getTo()))
            throw new IllegalArgumentException("Illegal mobile phone!");
    }


}
