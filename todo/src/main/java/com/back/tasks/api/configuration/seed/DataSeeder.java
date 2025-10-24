package com.back.tasks.api.configuration.seed;

import com.back.tasks.domain.entity.project.ProjectEntity;
import com.back.tasks.domain.entity.task.TaskEntity;
import com.back.tasks.domain.entity.user.UserEntity;
import com.back.tasks.domain.io.enums.TaskStatus;
import com.back.tasks.domain.io.enums.UserRole;
import com.back.tasks.domain.repository.project.ProjectRepository;
import com.back.tasks.domain.repository.task.TaskRepository;
import com.back.tasks.domain.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode("12345678");

            UserEntity user = new UserEntity();
            user.setName("Admin master");
            user.setEmail("admin@admin.com");
            user.setPassword(encodedPassword);
            user.setRole(UserRole.ADMIN);
            user.setDeleted(false);
            userRepository.save(user);
        }

        if (projectRepository.count() == 0) {
            ProjectEntity project = new ProjectEntity();
            project.setName("Project admin");
            project.setDescription("Project admin");
            project.setManager(userRepository.findById(1L));
            project.setDeleted(false);
            projectRepository.save(project);
        }

        if (taskRepository.count() == 0) {
            TaskEntity task = new TaskEntity();
            task.setTitle("Example task");
            task.setDescription("This is the example task.");
            task.setStatus(TaskStatus.WAITING);
            task.setResponsible(userRepository.findById(1L));
            task.setProject(projectRepository.findById(1L).get());
            task.setDeleted(false);
            taskRepository.save(task);
        }
    }
}
