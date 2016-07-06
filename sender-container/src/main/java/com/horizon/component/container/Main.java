package com.horizon.component.container;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.horizon.component.sender.Dispatcher;
import com.horizon.component.sender.basic.BasicMineMessage;
import com.horizon.component.sender.dispatch.SenderDispatcher;

import java.io.*;
import java.net.URL;

/**
 * interface defined by
 *
 * @author ZhenZhong
 * @date 2016/7/5
 */
public class Main {

    public static void main(String[] args) throws IOException {

        System.out.println(Main.class.getResource("/"));
        System.out.println(Main.class.getClassLoader().getResource(""));
        if (args == null || args.length == 0) {
            args = new String[]{"demo/message.txt"};
        }
        URL url = Main.class.getClassLoader().getResource(args[0]);
        if (url == null) throw new FileNotFoundException("Can not find the message \'" + args[0] + "\' file.");
        String message;
        BufferedReader reader = new BufferedReader(new FileReader(new File(url.getFile())));
        StringBuffer buffer = new StringBuffer();
        try {
            while ((message = reader.readLine()) != null) {
                buffer.append(message);
            }
        } finally {
            if (reader != null)
                reader.close();
        }

        ObjectMapper mapper = new ObjectMapper();
        BasicMineMessage mimeMessage;
        try {
            mimeMessage = mapper.readValue(buffer.toString(), BasicMineMessage.class);
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
