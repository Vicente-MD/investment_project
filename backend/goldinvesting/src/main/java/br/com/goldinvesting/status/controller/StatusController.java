package br.com.goldinvesting.status.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.goldinvesting.status.model.Status;
import br.com.goldinvesting.status.repository.StatusRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/status")
@RequiredArgsConstructor
public class StatusController {
    private final StatusRepository statusRepository;

    @PostMapping
    public ResponseEntity<?> createStatus(@RequestBody Status status){
        return ResponseEntity.ok(statusRepository.save(status));
    }

    @GetMapping
    public ResponseEntity<?> findStatus(){
        return ResponseEntity.ok(statusRepository.findAll());
    }

    @DeleteMapping
    public ResponseEntity<?> deleteStatus(){
        statusRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
