package com.rschir.prac.services;

import com.rschir.prac.model.Jewelry;
import com.rschir.prac.model.JewelryBlob;
import com.rschir.prac.repositories.JewelryBlobRepository;
import com.rschir.prac.repositories.JewelryRepository;
import com.rschir.prac.util.ImageUtil;
import com.rschir.prac.util.exceptions.NotFoundException;
import jakarta.transaction.Transactional;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.sql.Blob;
import java.sql.Clob;
import java.util.Arrays;
import java.util.List;


@Service
public class JewelryBlobService {
    @Autowired
    private JewelryBlobRepository fileRepository;

    @Autowired
    private JewelryRepository jewelryRepository;
    @Transactional
    public JewelryBlob saveAttachment(MultipartFile file, Long id) throws Exception {

        try {
            Jewelry jewelry = jewelryRepository.findById(id).orElseThrow(NotFoundException::new);

            if (file.getBytes().length > (1024 * 1024)) {
                throw new Exception("File size exceeds maximum limit");
            }

            System.out.println(jewelry.getJewelryId());
            JewelryBlob attachment = new JewelryBlob();
            attachment.setJewelry(jewelry);
            attachment.setJewelryId(id);
            attachment.setPic(ImageUtil.compressImage(file.getBytes()));
            return fileRepository.save(attachment);
        } catch (MaxUploadSizeExceededException e) {
            throw new MaxUploadSizeExceededException(file.getSize());
        } catch (Exception e) {
            throw new Exception("Could not save File ");
        }
    }



    @Transactional
    public byte[] getAttachment(long id) {
        JewelryBlob jewelryBlob = fileRepository.findById(id).orElseThrow(NotFoundException::new);
        byte[] image = ImageUtil.decompressImage(jewelryBlob.getPic());
        return image;
    }


}
