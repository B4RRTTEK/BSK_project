package bsk.demo;

import java.io.File;

public interface ChatUser{
    void sendMessage(String msg);
    void sendFile(File file);
}
