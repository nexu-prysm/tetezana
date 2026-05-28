package mg.tetezana.learn.endpoint.rest.controller;

import java.net.URL;
import lombok.RequiredArgsConstructor;
import mg.tetezana.learn.repository.InternshipTrackRepository;
import mg.tetezana.learn.repository.model.AppUser;
import mg.tetezana.learn.repository.model.InternshipTrack;
import mg.tetezana.learn.service.certificate.CertificateService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/certificates")
@RequiredArgsConstructor
public class CertificateController {

  private final CertificateService certificateService;
  private final InternshipTrackRepository trackRepository;

  @PostMapping("/generate/{trackId}")
  @PreAuthorize("hasRole('STUDENT')")
  public String generateCertificate(@PathVariable String trackId, @AuthenticationPrincipal AppUser user) {
    InternshipTrack track = trackRepository.findById(trackId).orElseThrow(() -> new RuntimeException("Track not found"));
    return certificateService.generateAndUploadCertificate(user, track);
  }

  @GetMapping("/url/{bucketKey}")
  @PreAuthorize("hasRole('STUDENT')")
  public URL getCertificateUrl(@PathVariable String bucketKey) {
    return certificateService.getCertificateUrl(bucketKey);
  }
}
