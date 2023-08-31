package com.horizon.component.sender.impl.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * interface defined by
 *
 * @author ZhenZhong
 * @date 2016 /7/3
 */
public class EmailAuthenticator extends Authenticator {
    private final String userName;
    private final String password;

    /**
     * Instantiates a new Email authenticator.
     *
     * @param username the username
     * @param password the password
     */
    public EmailAuthenticator(String username, String password) {
        this.userName = username;
        this.password = password;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(userName, password);
    }
}
