package com.example.estoriassemhapp.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageCache {

    public static void loadToImageView(Activity activity, String id, ImageView imageView, ProgressBar progressBar) {
        //Verificar se a imagem já está salva localmente
        String imageLocation = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + id;
        File f = new File(imageLocation);
        if (f.exists() && !f.isDirectory()) {
            imageView.setImageBitmap(Util.getBitmap(imageLocation));
        }
        else {
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.VISIBLE);
                        }
                    });


                    HttpRequest httpRequest = new HttpRequest(Config.BD_APP_URl + "users/get_photo_user.php", "GET", "UTF-8");
                    httpRequest.addParam("id", id);

                    try {
                        InputStream is = httpRequest.execute();
                        String imgBase64 = Util.inputStream2String(is, "UTF-8");
                        httpRequest.finish();

                        String pureBase64Encoded = imgBase64.substring(imgBase64.indexOf(",") + 1);
                        Bitmap img = Util.base642Bitmap(pureBase64Encoded);

                        Util.saveImage(img, imageLocation);

                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImageBitmap(img);
                            }
                        });

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }
            });
        }
    }

}