package com.back.tasks.api.configuration.seed;

import com.back.tasks.domain.entity.task.TaskEntity;
import com.back.tasks.domain.entity.user.UserEntity;
import com.back.tasks.domain.io.enums.TaskStatus;
import com.back.tasks.domain.repository.task.TaskRepository;
import com.back.tasks.domain.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            UserEntity user = new UserEntity();
            user.setName("Ryan Victor");
            user.setEmail("vryan8294@gmail.com");
            user.setPassword("12345678");
            userRepository.save(user);
        }

        if (taskRepository.count() == 0) {
            TaskEntity task = new TaskEntity();
            task.setTitle("Example task");
            task.setDescription("This is the example task.");
            task.setStatus(TaskStatus.WAITING);
            taskRepository.save(task);
        }
    }
}
