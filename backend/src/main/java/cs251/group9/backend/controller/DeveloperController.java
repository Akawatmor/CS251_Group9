package cs251.group9.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import cs251.group9.backend.entity.Developer;

import cs251.group9.backend.repository.DeveloperRepository;

@RestController
@RequestMapping("/api/developers")
public class DeveloperController {

    @Autowired
    private DeveloperRepository developerRepo;

    @PostMapping
    public ResponseEntity<Developer> create(@RequestBody Developer dev) {
        return ResponseEntity.ok(developerRepo.save(dev));
    }

    @GetMapping
    public List<Developer> getAll() {
        return developerRepo.findAll();
    }

    @GetMapping("/{devId}")
    public ResponseEntity<Developer> getById(@PathVariable String devId) {
        return developerRepo.findById(devId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{devId}")
    public ResponseEntity<Developer> update(@PathVariable String devId, @RequestBody Developer updatedDev) {
        return developerRepo.findById(devId)
            .map(dev -> {
                dev.setDevName(updatedDev.getDevName());
                dev.setEmail(updatedDev.getEmail());
                dev.setDevDesc(updatedDev.getDevDesc());
                dev.setSocialMedia(updatedDev.getSocialMedia());
                dev.setTeamName(updatedDev.getTeamName());
                return ResponseEntity.ok(developerRepo.save(dev));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{devId}")
    public ResponseEntity<Void> delete(@PathVariable String devId) {
        if (developerRepo.existsById(devId)) {
            developerRepo.deleteById(devId);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
