package com.android.gaoyun.aesencrypter;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileEncrypterActivity extends ActionBarActivity {

    Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_encrypter);

        typeface = Typeface.createFromAsset(getAssets(), "fonts/consola.ttf");

        Button loadButton = (Button) findViewById(R.id.loadButton);
        loadButton.setTypeface(typeface);
        Button encryptFileButton = (Button) findViewById(R.id.encryptFileButton);
        encryptFileButton.setTypeface(typeface);
        Button decryptFileButton = (Button) findViewById(R.id.decryptFileButton);
        decryptFileButton.setTypeface(typeface);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_file_encrypter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public File getFilesDirectory(String fileName) {

        File xFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), fileName);

        return xFile;
    }

    public void decryptFile(View view) {

        EditText fileName = (EditText) findViewById(R.id.fileName);
        String filename;

        if (fileName.getText().toString() != null) {
            filename = fileName.getText().toString();
        } else {
            filename = "Encrypted file";
        }

        String fileText = "Text";
        String LOG = "";

        TextView fileTestText = (TextView) findViewById(R.id.fileTestText);
        //fileTestText.setText(getFilesDirectory("testFile").toString());

        File sdPatch = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        sdPatch = new File(sdPatch.getPath());
        sdPatch.mkdirs();
        //fileTestText.setText(sdPatch.toString());

        File encryptedFile = new File(sdPatch, filename);
        File resultDecrypt = new File(sdPatch, filename + "Decrypt");
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(encryptedFile));
            String str = "";
            while ((str = bufferedReader.readLine()) != null) {
                Log.d(LOG, fileText);
            }
            bufferedReader.close();

            //byte[] encryptBytes = new byte[(int)encryptedFile.length()];
            FileInputStream fis = new FileInputStream(encryptedFile);
            InputStreamReader isr = new InputStreamReader(fis);
            char[] inputBuffer = new char[(int) encryptedFile.length()];
            isr.read(inputBuffer);
            String read = new String(inputBuffer);
            //fileTestText.setText(read);
            EditText filePassword = (EditText) findViewById(R.id.filePassword);
            String fileKey = filePassword.getText().toString();
            System.out.println(read);
            AesProceed fileAES = new AesProceed(fileKey);

            //BufferedWriter saverEncryptFile = new BufferedWriter(new FileWriter(resultDecrypt));
            //saverEncryptFile.write(fileAES.decrypt(read.toString()));
            FileOutputStream fos = new FileOutputStream(resultDecrypt);
            //OutputStreamWriter osw = new OutputStreamWriter(fos);
            fos.write(fileAES.decrypt(read).getBytes());
            //fos.flush();
            fos.close();
            //fos.close();
            //saverEncryptFile.close();

            fileTestText.setText("File " + filename + " was decrypt succesful.");
            fileTestText.setText(fileAES.decrypt(read).toString());
        } catch (IOException e) {
            e.printStackTrace();
            fileTestText.setText("Decryption Error!.");
        }

    }


    public void encryptFile(View view) {

        EditText fileName = (EditText) findViewById(R.id.fileName);
        String filename;

        if (fileName.getText().toString() != null) {
            filename = fileName.getText().toString();
        } else {
            filename = "Encrypted file";
        }

        String fileText = "";
        String LOG = "";

        TextView fileTestText = (TextView) findViewById(R.id.fileTestText);

        File sdPatch = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        sdPatch = new File(sdPatch.toString());
        fileTestText.setText(sdPatch.toString());

        File encryptedFile = new File(sdPatch, filename);
        File resultEncrypt = new File(sdPatch, filename + "Result");

        try {

            BufferedReader bufferedReader = new BufferedReader(new FileReader(encryptedFile));
            String str = "";
            while ((str = bufferedReader.readLine()) != null) {
                Log.d(LOG, fileText);
            }

            byte[] encryptBytes = new byte[(int) encryptedFile.length()];
            FileInputStream fis = new FileInputStream(encryptedFile);
            fis.read(encryptBytes);

            EditText filePassword = (EditText) findViewById(R.id.filePassword);
            String fileKey = filePassword.getText().toString();

            AesProceed fileAES = new AesProceed(fileKey);

            BufferedWriter saverEncryptFile = new BufferedWriter(new FileWriter(resultEncrypt));
            saverEncryptFile.write(fileAES.encrypt(encryptBytes.toString()));
            saverEncryptFile.close();

            fileTestText.setText("File " + filename + " was Encrypt succesfull.");

        } catch (IOException e) {
            e.printStackTrace();
        }

        //catch (FileNotFoundException e){
        //    e.printStackTrace();
        //}

    }
}
