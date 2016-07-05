package com.horizon.component.container;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.horizon.component.sender.Dispatcher;
import com.horizon.component.sender.MimeMessage;
import com.horizon.component.sender.basic.BasicMineMessage;
import com.horizon.component.sender.dispatch.SenderDispatcher;
import org.apache.commons.lang.NullArgumentException;

import java.io.IOException;

/**
 * interface defined by
 *
 * @author ZhenZhong
 * @date 2016/7/5
 */
public class Main {

    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            throw new NullArgumentException(MimeMessage.class.getSimpleName());
        }
        String msg = args[0];
        ObjectMapper mapper = new ObjectMapper();
        BasicMineMessage mimeMessage;
        try {
            mimeMessage = mapper.readValue(msg, BasicMineMessage.class);
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        Dispatcher dispatcher = null;
        try {
            dispatcher = new SenderDispatcher(mimeMessage);
            dispatcher.dispatch(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
