package com.example.estoriassemhapp.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.util.DisplayMetrics;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Util {
    /**
     * Calcula o numero de colunas que cabem na tela ao usar o tipo de
     * visualizacao GRID no RecycleView
     *
     * @param context contexto utilizado, geralmente instancia da activity que
     *                contem o RecycleView
     * @param columnWidthDp largura do item do grid em dp
     * @return numero de colunas a serem usadas no GridLayoutManager
     */
    public static int calculateNoOfColumns(Context context, float columnWidthDp) { // For example columnWidthdp=180
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float screenWidthDp = displayMetrics.widthPixels;
        int noOfColumns = (int) (screenWidthDp / columnWidthDp + 0.5); // +0.5 for correct rounding to int.
        return noOfColumns;
    }

    /**
     * Carrega um arquivo de imagem em um bitmap sem realizar qualquer tipo de escala.
     * @param imagePath caminho local de onde esta localizado o arquivo de imagem.
     * @return o bitmap.
     */
    public static Bitmap getBitmap(String imagePath) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(imagePath, bmOptions);
    }

    /**
     * Carrega um arquivo de imagem e aplica um fator de escala.
     * @param imagePath caminho local de onde esta localizado o arquivo de imagem
     * @param scaleFactor fator de escala a ser aplicado na imagem. Se
     *                    quiser uma imagem com metade do tamanho, deve
     *                    usar o valor 2 como entrada. Uma imagem com um
     *                    quarto do tamanho deve usar 4 como entrada.
     * @return o bitmap
     */
    public static Bitmap getBitmap(String imagePath, int scaleFactor) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        return BitmapFactory.decodeFile(imagePath, bmOptions);
    }

    /**
     * Carrega um arquivo de imagem em um bitmap de tamanho definido.
     * @param imagePath caminho local de onde esta localizado o arquivo de imagem.
     * @param w largura que a imagem deve ter.
     * @param h altura que a imagem deve ter.
     * @return o bitmap
     */
    public static Bitmap getBitmap(String imagePath, int w, int h) {

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, bmOptions);

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.max(photoW/w, photoH/h);

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        return BitmapFactory.decodeFile(imagePath, bmOptions);
    }

    /**
     * Carrega uma imagem em um bitmap a partir de um URI.
     * @param context contexto utilizado, geralmente instancia da activity que
     *                chama a função
     * @param imageLocation endereço URI da imagem
     * @return o bitmap
     * @throws FileNotFoundException caso a imagem não seja achada
     */
    public static Bitmap getBitmap(Context context, Uri imageLocation) throws FileNotFoundException {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = 1;
        bmOptions.inPurgeable = true;
        InputStream is = context.getContentResolver().openInputStream(imageLocation);
        return BitmapFactory.decodeStream(is, null, bmOptions);
    }

    /**
     * Carrega uma imagem em um bitmap a partir de um URI.
     * A imagem sofrerá escala de acordo com as dimensões escohidas.
     * @param context contexto utilizado, geralmente instancia da activity que
     *                chama a função
     * @param imageLocation endereço URI da imagem
     * @param scaleFactor fator de escala a ser aplicado na imagem. Se
     *                    quiser uma imagem com metade do tamanho, deve
     *                    usar o valor 2 como entrada. Uma imagem com um
     *                    quarto do tamanho deve usar 4 como entrada.
     * @return o bitmap
     * @throws FileNotFoundException caso a imagem não seja achada
     */
    public static Bitmap getBitmap(Context context, Uri imageLocation, int scaleFactor) throws FileNotFoundException {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;
        InputStream is = context.getContentResolver().openInputStream(imageLocation);
        return BitmapFactory.decodeStream(is, null, bmOptions);
    }

    /**
     * Carrega uma imagem em um bitmap a partir de um URI.
     * A imagem sofrerá escala de acordo com as dimensões escohidas.
     * @param context contexto utilizado, geralmente instancia da activity que
     *                chama a função
     * @param imageLocation endereço URI da imagem
     * @param w largura que a imagem deve ter
     * @param h altura que a imagem deve ter
     * @return o bitmap
     * @throws FileNotFoundException caso a imagem não seja achada
     */
    public static Bitmap getBitmap(Context context, Uri imageLocation, int w, int h) throws FileNotFoundException {

        InputStream is = context.getContentResolver().openInputStream(imageLocation);

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, bmOptions);

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.max(photoW/w, photoH/h);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        is = context.getContentResolver().openInputStream(imageLocation);
        return BitmapFactory.decodeStream(is, null, bmOptions);
    }

    /**
     * Aplica uma escala em um arquivo de imagem já existente.
     * @param imageLocation caminho absoluto onde esta salva a imagem
     * @param scaleFactor fator de escala a ser aplicado na imagem. Se
     *                    quiser uma imagem com metade do tamanho, deve
     *                    usar o valor 2 como entrada. Uma imagem com um
     *                    quarto do tamanho deve usar 4 como entrada.
     * @throws FileNotFoundException se o arquivo nao existe
     */
    public static void scaleImage(String imageLocation, int scaleFactor) throws FileNotFoundException {
        // Decode the image file into a Bitmap sized to fill the View
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bmp =  BitmapFactory.decodeFile(imageLocation, bmOptions);

        FileOutputStream out = new FileOutputStream(imageLocation);
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
    }

    /**
     * Aplica uma escala em um arquivo de imagem já existente.
     * @param imageLocation caminho absoluto onde esta salva a imagem
     * @param w largura que a imagem deve ter
     * @param h altura que a imagem deve ter
     * @throws FileNotFoundException se o arquivo nao existe
     */
    public static void scaleImage(String imageLocation, int w, int h) throws FileNotFoundException {

        InputStream is = new FileInputStream(imageLocation);

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, bmOptions);

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.max(photoW/w, photoH/h);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bmp =  BitmapFactory.decodeFile(imageLocation, bmOptions);

        FileOutputStream out = new FileOutputStream(imageLocation);
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
    }

    /**
     * Salva um Bitmap em um arquivo.
     * @param bmp bitmap a ser salvo
     * @param imageLocation caminho absoluto onde esta salva a imagem
     * @throws FileNotFoundException se o arquivo nao existe
     */
    public static void saveImage(Bitmap bmp, String imageLocation) throws FileNotFoundException {
        FileOutputStream out = new FileOutputStream(imageLocation);
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
    }

    /**
     * Converte um InputStream para uma String.
     * @param is InputStream a ser convertido.
     * @param charset o charset a ser utilizado na conversão.
     * @return a string.
     * @throws FileNotFoundException se o arquivo nao existe
     */
    public static String inputStream2String(InputStream is, String charset) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                is, charset), 8);
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
        }
        is.close();
        return sb.toString();
    }

    /**
     * Converte um String em formato Base64 para Bitmap
     * @param myImageData string Base64
     * @return o Bitmap.
     * @throws FileNotFoundException se o arquivo nao existe
     */
    public static Bitmap base642Bitmap(String myImageData)
    {
        byte[] imageAsBytes = Base64.decode(myImageData.getBytes(),Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }
}
