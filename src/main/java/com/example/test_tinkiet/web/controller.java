package com.example.test_tinkiet.web;
import com.example.test_tinkiet.entity.Candidate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@RestController
public class controller {
    private static final String FILE_PATH = "candidates.json";

    @PostMapping("/candidates")
    public ResponseEntity<String> createCandidate(@RequestBody Candidate candidate) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode candidateNode = objectMapper.createObjectNode();
            candidateNode.put("firstName", candidate.getFirstname());
            candidateNode.put("lastName", candidate.getLastname());
            candidateNode.put("email", candidate.getEmail());
            candidateNode.put("description", candidate.getDescription());

            File file = new File(FILE_PATH);
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.write(objectMapper.writeValueAsString(candidateNode) + "\n");
            fileWriter.close();

            return ResponseEntity.ok("Candidate ajouter au fichier ");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error");
        }
    }

}
