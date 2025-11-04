package org.delivery.common.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
@Profile("local")
public class LocalFileStorageService implements FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private static final String FILE_URL_PREFIX = "/uploads/products/";

    @Override
    public String upload(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("파일이 비어 있습니다.");
        }

        try {
            String originalFilename = file.getOriginalFilename();
            String extension = getExtension(originalFilename);
            String uniqueName = UUID.randomUUID().toString() + extension;

            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File dest = new File(dir, uniqueName);
            file.transferTo(dest);

            return FILE_URL_PREFIX + uniqueName;

        } catch (IOException e) {
            throw new RuntimeException("파일 업로드 실패: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(String fileUrl) {
        if (fileUrl == null || fileUrl.isEmpty() || !fileUrl.startsWith(FILE_URL_PREFIX)) {
            log.warn("삭제할 수 없는 파일 URL입니다: {}", fileUrl);
            return;
        }
        try{
            String uniqueName = fileUrl.substring(FILE_URL_PREFIX.length());

            File fileToDelete = new File(uploadDir, uniqueName);

             if (fileToDelete.exists()) {
                 if (fileToDelete.delete()) {
                     log.info("로컬 파일 삭제 성공: {}", fileUrl);
                 } else {
                     log.error("로컬 파일 삭제 실패 (권한 문제 등) {}", fileUrl);
                 }
             }else  {
                 log.warn("삭제하려는 로컬 파일이 존재하지 않습니다. {}", fileUrl);
             }
        }catch (Exception e) {
            log.error("로컬 파일 삭제 중 예외 발생 : {}", fileUrl, e);
        }
    }

    private String getExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf("."));
    }
}
