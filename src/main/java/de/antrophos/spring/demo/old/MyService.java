package de.antrophos.spring.demo.old;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.messaging.Message;

public class MyService {

    private final String watchedDirectory;

    public MyService(String watchedDirectory) {
        this.watchedDirectory = watchedDirectory;
        System.out.println("Watched Directory from XML: " + watchedDirectory);
        Path path = Paths.get(watchedDirectory);
        System.out.println("This would be " + path.toAbsolutePath());
        // Add your directory monitoring logic here.
    }


    public void receiveMessage(Message<String> message) {
        String fileContent = message.getPayload();
        System.out.println("Received file content: " + fileContent);
        // Add your processing logic here
    }
}
