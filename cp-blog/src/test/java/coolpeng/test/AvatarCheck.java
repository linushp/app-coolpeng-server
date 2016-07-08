package coolpeng.test;

import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.BosClientConfiguration;
import com.baidubce.services.bos.model.BosObjectSummary;
import com.baidubce.services.bos.model.ListObjectsRequest;
import com.baidubce.services.bos.model.ListObjectsResponse;
import com.coolpeng.framework.utils.ServiceUtils;

import java.util.*;

/**
 * Created by Administrator on 2016/7/8.
 */
public class AvatarCheck {


    public static void main(String ars[]){


        String ACCESS_KEY_ID ="f0131c5559d3415e956706caf01d1051";
        ;                   // 用户的Access Key ID
        String SECRET_ACCESS_KEY ="ba90fcb9ee2441faa32f49a909192cc9";     // 用户的Secret Access Key

        // 初始化一个BosClient
        BosClientConfiguration config = new BosClientConfiguration();
        config.setCredentials(new DefaultBceCredentials(ACCESS_KEY_ID, SECRET_ACCESS_KEY));
        BosClient client = new BosClient(config);

//        ListObjectsResponse listObject = client.listObjects("coolpeng","avatar");

        ListObjectsRequest listObjectsRequest = new ListObjectsRequest("coolpeng");




        Map<String,Boolean> map = new HashMap<>();

        String maker = null;
        int count = 0;
        while (true){
//            listObjectsRequest.setDelimiter("/");
            listObjectsRequest.setPrefix("avatar");
            if (maker!=null){
                listObjectsRequest.setMarker(maker);
            }

            ListObjectsResponse listing = client.listObjects(listObjectsRequest);
            List<BosObjectSummary> contents = listing.getContents();
            maker = listing.getNextMarker();
            for (BosObjectSummary summary : contents){
//                System.out.println(summary.getKey());
                map.put(summary.getKey(),false);
                count ++ ;
            }
            if (maker==null){
                break;
            }
        }

        System.out.println(count);


        Album [] avatarModels = {
                Album.parse("aa-0001-0504"),
                Album.parse("dw-0001-0500"),
                Album.parse("dw-0501-1000"),
                Album.parse("dw-1001-1311"),
                Album.parse("fj-0001-0425"),
                Album.parse("kt-0001-1040"),
                Album.parse("lb-0001-0460"),
                Album.parse("LL-0001-0320"),
                Album.parse("lz-0001-1000"),
                Album.parse("mm-0001-0600"),
                Album.parse("mv-0001-1957"),
                Album.parse("mx-0001-0188"),
                Album.parse("ns-0001-0269"),
                Album.parse("xf-0001-0101")
        };

        Set<String> error = new HashSet<>();

        for (int i = 0 ;i<avatarModels.length;i++){
            Album album = avatarModels[i];
            for (int m=album.getBegin();m<=album.getEnd();m++){
                String p = "avatar/"+album.getName()+"/" + album.getPrefix() + "-" + formatNumber(m) + ".jpg";
                if (!map.containsKey(p)){
                  error.add(p);
                }
                map.put(p,true);
            }
        }


        System.out.println(error.size());
        for (String e:error){
            System.out.println(e);
        }

        for (String x :map.keySet()){
            if (!map.get(x)){
                System.out.println(x);
            }
        }
    }


    private static String formatNumber(int num) {
        if (num < 10) {
            return "000" + num;
        }

        if (num < 100) {
            return "00" + num;
        }

        if (num < 1000) {
            return "0" + num;
        }

        return "" + num;
    }


}
