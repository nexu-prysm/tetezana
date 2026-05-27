package mg.tetezana.learn.service.certificate;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import mg.tetezana.learn.file.bucket.BucketComponent;
import mg.tetezana.learn.repository.model.AppUser;
import mg.tetezana.learn.repository.model.InternshipTrack;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CertificateService {

  private final BucketComponent bucketComponent;

  public String generateAndUploadCertificate(AppUser user, InternshipTrack track) {
    try {
      File tempFile = File.createTempFile("certificate-" + user.getId() + "-", ".pdf");

      Document document = new Document();
      PdfWriter.getInstance(document, new FileOutputStream(tempFile));

      document.open();
      Font titleFont = new Font(Font.HELVETICA, 24, Font.BOLD);
      Font bodyFont = new Font(Font.HELVETICA, 16, Font.NORMAL);

      Paragraph title = new Paragraph("Certificate of Completion", titleFont);
      title.setAlignment(Paragraph.ALIGN_CENTER);
      document.add(title);

      document.add(new Paragraph("\n\n"));

      Paragraph body =
          new Paragraph(
              "This is to certify that "
                  + user.getFirstName()
                  + " "
                  + user.getLastName()
                  + "\nhas successfully completed the Virtual Internship Track:\n\n"
                  + track.getTitle(),
              bodyFont);
      body.setAlignment(Paragraph.ALIGN_CENTER);
      document.add(body);

      document.close();

      String bucketKey = "certificates/" + user.getId() + "/" + track.getId() + ".pdf";
      bucketComponent.upload(tempFile, bucketKey);

      // Cleanup temp file
      tempFile.delete();

      return bucketKey;

    } catch (Exception e) {
      throw new RuntimeException("Failed to generate certificate", e);
    }
  }

  public URL getCertificateUrl(String bucketKey) {
    return bucketComponent.presign(bucketKey, Duration.ofDays(7));
  }
}
