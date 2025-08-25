package com.NovaMind.Project.NovaMind.controller;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ClassPathResource;

@RestController
@RequestMapping("/images")
public class ImageController {

    @GetMapping("/cloud-computing.jpg")
    public Resource getCloudComputingImage() {
        return new ClassPathResource("com/NovaMind/Project/NovaMind/uploads/images/cloud-computing.jpg");
    }


}
