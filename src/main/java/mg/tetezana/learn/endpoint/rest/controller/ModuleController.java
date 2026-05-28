package mg.tetezana.learn.endpoint.rest.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mg.tetezana.learn.repository.ModuleRepository;
import mg.tetezana.learn.repository.model.Module;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/modules")
@RequiredArgsConstructor
public class ModuleController {

  private final ModuleRepository moduleRepository;

  @GetMapping
  public List<Module> getAllModules() {
    return moduleRepository.findAll();
  }

  @GetMapping("/{id}")
  public Module getModuleById(@PathVariable String id) {
    return moduleRepository.findById(id).orElseThrow();
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public Module createModule(@RequestBody Module module) {
    return moduleRepository.save(module);
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public Module updateModule(@PathVariable String id, @RequestBody Module moduleDetails) {
    Module module = moduleRepository.findById(id).orElseThrow();
    module.setTitle(moduleDetails.getTitle());
    module.setDescription(moduleDetails.getDescription());
    module.setOrderIndex(moduleDetails.getOrderIndex());
    module.setInternshipTrack(moduleDetails.getInternshipTrack());
    return moduleRepository.save(module);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public void deleteModule(@PathVariable String id) {
    moduleRepository.deleteById(id);
  }
}
