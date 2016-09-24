package com.horizon.component.sender.impl.qq;

import com.horizon.component.sender.MimeMessage;
import com.horizon.component.sender.Sender;

/**
 * interface defined by
 *
 * @author ZhenZhong
 * @date 2016/9/7
 */
public class QQSender implements Sender<MimeMessage> {
    /**
     * send the send method
     *
     * @param mimeMessage
     *
     * @throws Exception
     */
    @Override
    public void send(MimeMessage mimeMessage) throws Exception {
        System.out.println("sender qq successful.");
    }

    /**
     * check the type is supported.
     *
     * @param sendType
     *
     * @return
     */
    @Override
    public boolean isSupported(String sendType) {
        if (sendType.equalsIgnoreCase("QQ"))
            return true;
        return false;
    }
}
