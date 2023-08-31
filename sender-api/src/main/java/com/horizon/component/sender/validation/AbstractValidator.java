package com.horizon.component.sender.validation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.horizon.component.sender.MimeMessage;
import com.horizon.component.sender.Validator;
import com.horizon.component.utilities.SenderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.IOException;
import java.rmi.ServerException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * interface defined by
 *
 * @param <T> the type parameter
 * @author ZhenZhong
 * @date 2016 /6/16
 */
public abstract class AbstractValidator<T extends MimeMessage> implements Validator<T> {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractValidator.class);
    /**
     * The constant PATTERN_EMAIL.
     */
    public static final Pattern PATTERN_EMAIL = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    /**
     * The constant PATTERN_PHONE.
     */
    public static final Pattern PATTERN_PHONE = Pattern.compile("\\+?[0-9]{1,5}\\-?[0-9]{1,14}\\-?[0-9]{1,13}");

    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * The Required params.
     */
    protected List<String> requiredParams = new ArrayList<String>();
    /**
     * The Template params.
     */
    protected List<String> templateParams = new ArrayList<String>();

    /**
     * validate the handler
     *
     * @param mimeMessage
     * @throws ServerException
     */
    @Override
    public void validate(T mimeMessage) throws Exception {
        this.validateRequiredParameters(mimeMessage);
        this.validateTemplateParameters(mimeMessage);
        this.validateInvalidParameters(mimeMessage);
    }

    /**
     * validate the requirement parameters
     *
     * @param mimeMessage
     * @throws Exception
     */
    @Override
    public void validateRequiredParameters(T mimeMessage) {
        LOG.info("Validate required parameters {}", requiredParams);
        final Set<String> missingParameters = new HashSet<String>();
        Map<String, String> parameters = new HashMap<String, String>();
        try {
            parameters = mapper.readValue(mapper.writeValueAsString(mimeMessage), Map.class);
        } catch (JsonProcessingException e) {
            LOG.error("Can not parse the mime message. The case {}", e.toString());
        } catch (IOException e) {
            LOG.error("Can not parse the mime message. The case {}", e.toString());
        }
        for (String requiredParam : requiredParams) {
            String val = parameters.get(requiredParam);
            if (StringUtils.isEmpty(val)) {
                missingParameters.add(requiredParam);
            }
        }

        if (!missingParameters.isEmpty()) {
            throw SenderUtil.handleMissingParameters(missingParameters);
        }

    }

    /**
     * validate the template parameters
     *
     * @param mimeMessage
     * @throws Exception
     */
    @Override
    public void validateTemplateParameters(T mimeMessage) {
        LOG.info("Validate template parameters {}", templateParams);
        final Set<String> missingParameters = new HashSet<String>();
        if (mimeMessage.getContent() == null || mimeMessage.getContent().length() == 0) {
            if (mimeMessage.getTemplate() == null || mimeMessage.getTemplate().getModel() == null) {
                throw new NullPointerException("Undefined template.");
            }
            Map<String, Object> parameters = mimeMessage.getTemplate().getModel();
            for (String templateParam : templateParams) {
                Object val = parameters.get(templateParam);
                if (val == null) {
                    missingParameters.add(templateParam);
                }
            }

            if (!missingParameters.isEmpty()) {
                throw SenderUtil.handleMissingParameters(missingParameters);
            }
        }
    }

    /**
     * Is valid email boolean.
     *
     * @param email the email
     * @return the boolean
     */
    protected boolean isValidEmail(String email) {
        try {
            InternetAddress[] addresses = InternetAddress.parse(email);
            for (InternetAddress address : addresses) {
                if (!PATTERN_EMAIL
                        .matcher(address.getAddress()).matches()) {
                    return false;
                }
            }
        } catch (AddressException e) {
            return false;
        }

        return true;
    }

    /**
     * Is valid mobile no boolean.
     *
     * @param mobileNo the mobile no
     * @return the boolean
     */
    protected boolean isValidMobileNo(String mobileNo) {
        int numberCount = 0;
        char[] ch = mobileNo.toCharArray();
        for (int i = 0; i < mobileNo.length(); i++) {
            if (Character.isDigit(ch[i])) {
                numberCount++;
            }
        }

        if (numberCount < 8 || numberCount > 15) {
            return false;
        }

        return PATTERN_PHONE
                .matcher(mobileNo).matches();
    }
}
