package org.putegg.app.PhotoEgg.photo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.putegg.app.PhotoEgg.file.fileman;
import org.putegg.app.PhotoEgg.view.login;
/**
 * ImageUrlConfig
 * <p/>
 * Created by woxingxiao on 2016-10-24.
 */

public final class ImageUrlConfig {

    private static List<String> sUrls = new ArrayList<>();
    public static List<String> getUrls() {
        sUrls.clear();

        File userRoot = new File(new fileman().getUserHomeString(login.nowUser));
        if (userRoot.exists()) {
            for (File file : userRoot.listFiles()) {
                String path = file.getAbsolutePath();
                if (path.endsWith(".jpg") || path.endsWith(".jpeg") || path.endsWith(".png")) {
                    System.out.println(path);
                    sUrls.add(path);
                }
            }
        }
        else if (!userRoot.exists()){
            System.out.println("用户根目录文件夹丢失");
        }
if (sUrls.isEmpty()){System.out.println("urls is empty ");}
        return sUrls;
    }
}
