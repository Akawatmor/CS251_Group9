package cs251.group9.backend.controller;

import cs251.group9.backend.entity.*;
import cs251.group9.backend.repository.*;
import cs251.group9.backend.service.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mods")
public class ModDLCController {
    @Autowired private ModDLCRepository repo;

    @GetMapping("/game/{gameID}")
    public List<ModDLC> getModsByGame(@PathVariable String gameID) {
        return repo.findByGameGameID(gameID);
    }
}