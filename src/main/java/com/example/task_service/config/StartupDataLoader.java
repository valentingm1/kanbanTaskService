package com.example.task_service.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.example.task_service.entity.Swimlane;
import com.example.task_service.repository.SwimlaneRepository;
import java.util.Arrays;

@Component
public class StartupDataLoader implements CommandLineRunner {

    private final SwimlaneRepository swimlaneRepository;

    public StartupDataLoader(SwimlaneRepository swimlaneRepository) {
        this.swimlaneRepository = swimlaneRepository;
    }

    @Override
    public void run(String... args) {
        if (swimlaneRepository.count() == 0) { // Verifica si ya existen swimlanes
            Swimlane defaultSwimlane = new Swimlane(null, "Default Swimlane", null);
            Swimlane inProgress = new Swimlane(null, "In Progress", null);
            Swimlane done = new Swimlane(null, "Done", null);

            swimlaneRepository.saveAll(Arrays.asList(defaultSwimlane, inProgress, done));
            System.out.println("✅ Swimlanes iniciales creadas.");
        }
    }
}