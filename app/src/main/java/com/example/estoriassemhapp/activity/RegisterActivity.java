package com.example.estoriassemhapp.activity;

import static com.google.protobuf.CodedOutputStream.DEFAULT_BUFFER_SIZE;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.estoriassemhapp.R;
import com.example.estoriassemhapp.model.RegisterViewModel;
import com.example.estoriassemhapp.util.Config;
import com.example.estoriassemhapp.util.HttpRequest;
import com.example.estoriassemhapp.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegisterActivity extends AppCompatActivity {

    static int PHOTO_PICKER_REQUEST = 1;


    ////////////////////////////////////// TEMPORÀRIO
    // PS: NÃO MEXE!!!!!!!!!!
    public static File getFile(Context context, Uri uri) throws IOException {
        File destinationFilename = new File(context.getFilesDir().getPath() + File.separatorChar + queryName(context, uri));
        try (InputStream ins = context.getContentResolver().openInputStream(uri)) {
            createFileFromStream(ins, destinationFilename);
        } catch (Exception ex) {
            Log.e("Save File", ex.getMessage());
            ex.printStackTrace();
        }
        return destinationFilename;
    }

    public static void createFileFromStream(InputStream ins, File destination) {
        try (OutputStream os = new FileOutputStream(destination)) {
            byte[] buffer = new byte[4096];
            int length;
            while ((length = ins.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            os.flush();
        } catch (Exception ex) {
            Log.e("Save File", ex.getMessage());
            ex.printStackTrace();
        }
    }

    private static String queryName(Context context, Uri uri) {
        Cursor returnCursor =
                context.getContentResolver().query(uri, null, null, null, null);
        assert returnCursor != null;
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        String name = returnCursor.getString(nameIndex);
        returnCursor.close();
        return name;
    }
    ////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        RegisterViewModel rvm = new ViewModelProvider(this).get(RegisterViewModel.class);

        Uri selectPhotoLocation = rvm.getSelectPhotoLocation();
        if (selectPhotoLocation != null) {
            ImageView imvPhotoPreview = findViewById(R.id.imvPreview);
            imvPhotoPreview.setImageURI(selectPhotoLocation);
        }

        Button btnAddPhoto = findViewById(R.id.btnAddPhoto);
        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, PHOTO_PICKER_REQUEST);
            }
        });


        Button btnRegister =  findViewById(R.id.btnSingIn);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterViewModel rvm = new ViewModelProvider(RegisterActivity.this).get(RegisterViewModel.class);
                Uri photoFile = rvm.getSelectPhotoLocation();

                if(photoFile == null) {
                    Toast.makeText(RegisterActivity.this, "Você precisa selecionar uma imagem", Toast.LENGTH_LONG).show();
                    return;
                }

                EditText etNewUsername =  findViewById(R.id.etNewUsername);
                final String newUsername = etNewUsername.getText().toString();
                if(newUsername.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Campo de usuário não preenchido", Toast.LENGTH_LONG).show();
                    return;
                }

                EditText etNewLogin =  findViewById(R.id.etNewEmail);
                final String newLogin = etNewLogin.getText().toString();
                if(newLogin.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Campo de email não preenchido", Toast.LENGTH_LONG).show();
                    return;
                }

                EditText etNewPassword =  findViewById(R.id.etNewPassword);
                final String newPassword = etNewPassword.getText().toString();
                if(newPassword.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Campo de senha não preenchido", Toast.LENGTH_LONG).show();
                    return;
                }

                EditText etNewPasswordCheck =  findViewById(R.id.etRpNewPassword);
                String newPasswordCheck = etNewPasswordCheck.getText().toString();
                if(newPasswordCheck.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Campo de checagem de senha não preenchido", Toast.LENGTH_LONG).show();
                    return;
                }

                if(!newPassword.equals(newPasswordCheck)) {
                    Toast.makeText(RegisterActivity.this, "Senha não confere", Toast.LENGTH_LONG).show();
                    return;
                }

                Intent i = new Intent();
                i.setData(rvm.getSelectPhotoLocation());
                setResult(Activity.RESULT_OK, i);

                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        HttpRequest httpRequest = new HttpRequest(Config.BD_APP_URl + "register.php", "POST", "UTF-8");
                        httpRequest.addParam("newName", newUsername);
                        httpRequest.addParam("newLogin", newLogin);
                        httpRequest.addParam("newPassword", newPassword);

                        RegisterViewModel rvm = new ViewModelProvider(RegisterActivity.this).get(RegisterViewModel.class);

                        httpRequest.addFile("newPhoto", rvm.getPhotoFile());

                        try {
                            InputStream is = httpRequest.execute();
                            String result = Util.inputStream2String(is, "UTF-8");
                            httpRequest.finish();

                            JSONObject jsonObject = new JSONObject(result);
                            final int success = jsonObject.getInt("success");
                            if(success == 1) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(RegisterActivity.this, "Novo usuario registrado com sucesso", Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                });
                            }
                            else {
                                final String error = jsonObject.getString("error");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(RegisterActivity.this, error, Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Verifica se a foto foi selecionada
        if(requestCode == PHOTO_PICKER_REQUEST) {
            if(resultCode == Activity.RESULT_OK) {
                Uri selectPhotoLocation = data.getData(); //Pega o caminho da imagem

                File f = null;

                try {
                    f = getFile(RegisterActivity.this, selectPhotoLocation);
                    System.out.println(f.getPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //Insere a foto no preview
                ImageView imvPhotoPreview = findViewById(R.id.imvPreview);
                imvPhotoPreview.setImageURI(selectPhotoLocation);

                RegisterViewModel rvm = new ViewModelProvider(RegisterActivity.this).get(RegisterViewModel.class);
                rvm.setPhotoFile(f);

                System.out.println(rvm.getPhotoFile());
                rvm.setSelectPhotoLocation(selectPhotoLocation);
            }
        }
    }
}