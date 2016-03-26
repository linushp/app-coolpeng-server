package com.coolpeng.blog.utils;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ResourceBundle;

import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.BosClientConfiguration;
import com.baidubce.services.bos.model.PutObjectResponse;
//import com.coolpeng.framework.utils.ImageUtil;
import com.coolpeng.framework.utils.ServiceUtils;
import org.springframework.util.StreamUtils;


public class BAEUtils {


    public static void PutObject(InputStream inputStream, String fileName) throws IOException {

        //资源包
        ResourceBundle rb = ServiceUtils.getResourceBundle();

        String ACCESS_KEY_ID = rb.getString("application.bae.ACCESS_KEY_ID");
        ;                   // 用户的Access Key ID
        String SECRET_ACCESS_KEY = rb.getString("application.bae.SECRET_ACCESS_KEY");           // 用户的Secret Access Key

        // 初始化一个BosClient
        BosClientConfiguration config = new BosClientConfiguration();
        config.setCredentials(new DefaultBceCredentials(ACCESS_KEY_ID, SECRET_ACCESS_KEY));
        BosClient client = new BosClient(config);


        byte[] byteArray = StreamUtils.copyToByteArray(inputStream);

        InputStream inputStream1 = new ByteArrayInputStream(byteArray);

        PutObject(client, "coolpeng", "dragon_ball/upload/" + fileName, inputStream1);


//        try {
//            InputStream inputStream2 = new ByteArrayInputStream(byteArray);
//            inputStream2 = ImageUtil.resize(inputStream2, 100, 0.7f);
//            PutObject(client, "coolpeng", "dragon_ball/upload_thumb/" + fileName, inputStream2);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            InputStream inputStream3 = new ByteArrayInputStream(byteArray);
//            inputStream3 = ImageUtil.resize(inputStream3, 600, 0.7f);
//            PutObject(client, "coolpeng", "dragon_ball/upload_middle/" + fileName, inputStream3);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


    public static void PutObject(BosClient client, String bucketName, String objectKey, InputStream inputStream) throws FileNotFoundException {

        // 以数据流形式上传Object
        PutObjectResponse putObjectResponseFromInputStream = client.putObject(bucketName, objectKey, inputStream);

        // 打印ETag
        System.out.println(putObjectResponseFromInputStream.getETag());
    }

}
