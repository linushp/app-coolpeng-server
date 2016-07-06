package com.coolpeng.common.reply.service;

import com.coolpeng.common.reply.service.model.AvatarAlbumModel;

/**
 * Created by luanhaipeng on 16/7/6.
 */
public class CommonAvatarService {

    private static AvatarAlbumModel[] avatarModels = new AvatarAlbumModel[]{
            new AvatarAlbumModel("aa-0001-0504", "aa", 1, 504),       //1     504
            new AvatarAlbumModel("mv-0001-1957", "mv", 1, 1957)      //505    504+1957
    };


    private static void calculateIndex() {

        AvatarAlbumModel mm = avatarModels[0];
        mm.setIndexBegin(1);
        mm.setIndexEnd(mm.getCount());

        int length = avatarModels.length;
        for (int i = 1; i < length; i++) {
            AvatarAlbumModel m0 = avatarModels[i - 1];
            AvatarAlbumModel m1 = avatarModels[i];
            m1.setIndexBegin(m0.getIndexEnd() + 1);
            m1.setIndexEnd(m0.getIndexEnd() + m1.getCount());
        }
    }

    static {
        calculateIndex();
    }


    public static void main(String[] args) {
        String url = getRandomAvatarURL();
        System.out.println(url);

        for (int i = 0; i < 100; i++) {
            url = getRandomAvatarURL();
            System.out.println(url);
        }
    }

    public static String getRandomAvatarURL() {
        AvatarAlbumModel m = getRandomAvatarAlbum();
        int randomNumber = getRandomNumber(m.getBegin(), m.getEnd());
        String fileName = m.getPrefix() + "-" + formatNumber(randomNumber) + ".jpg";
        //http://coolpeng.bj.bcebos.com/avatar/aa-0001-0504/aa-0001.jpg
        return "http://coolpeng.bj.bcebos.com/avatar/" + m.getAlbumName() + "/" + fileName;
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

    private static int getRandomNumber(int min, int max) {
        int randomNumber = (int) (Math.random() * max) + min;
        return randomNumber;
    }


    private static AvatarAlbumModel getRandomAvatarAlbum() {

        AvatarAlbumModel lastAlbum = avatarModels[avatarModels.length - 1];

        int randomNumber = getRandomNumber(1, lastAlbum.getIndexEnd());

        int length = avatarModels.length;
        for (int i = 0; i < length; i++) {
            AvatarAlbumModel m = avatarModels[i];
            if (m.getIndexBegin() <= randomNumber &&  randomNumber <= m.getIndexEnd()) {
                return m;
            }
        }

        System.out.println("default:" + randomNumber);
        return avatarModels[0];
    }


}
