package com.scm.smart_contact_manager.services.implimentation;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.scm.smart_contact_manager.helper.AppConstants;
import com.scm.smart_contact_manager.services.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageServiceImp implements ImageService {

    private  Cloudinary cloudinary;

    public ImageServiceImp(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }


    @Override
    public String uploadImage(MultipartFile contactImage,String filename) {
        /*String filename = UUID.randomUUID().toString();*/

        try {
            byte[] data = new byte[contactImage.getInputStream().available()];
            contactImage.getInputStream().read(data);
            cloudinary.uploader().upload(
                    data, ObjectUtils.asMap("public_id", filename)
            );
            return this.getUrlFromPublicId(filename);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


    }



    @Override
    public String getUrlFromPublicId(String publicId) {


        return cloudinary.url().transformation(new Transformation<>().width(AppConstants.CONTACT_IMAGE_WIDTH).height(AppConstants.CONTACT_IMAGE_HEIGHT).crop(AppConstants.CONTACT_IMAGE_CROP)).generate(publicId);
    }


}
