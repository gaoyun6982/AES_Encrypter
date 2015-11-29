package com.android.gaoyun.aesencrypter;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class EncryptActivity extends ActionBarActivity {

    Typeface typeface;
    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encrypt);

        typeface = Typeface.createFromAsset(getAssets(), "fonts/consola.ttf");

        TextView decryptedText = (TextView)findViewById(R.id.Encryptedtext);
        EditText encrypto = (EditText)findViewById(R.id.encryptText);
        encrypto.setTypeface(typeface);
        decryptedText.setTypeface(typeface);
        EditText keyText = (EditText)findViewById(R.id.keyText);
        keyText.setTypeface(typeface);
        Button encryptButton = (Button)findViewById(R.id.encryptButton);
        encryptButton.setTypeface(typeface);
        Button decryptButton = (Button)findViewById(R.id.decryptButton);
        decryptButton.setTypeface(typeface);
        Button save2file = (Button)findViewById(R.id.save2filebutton);
        save2file.setTypeface(typeface);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_encrypt, menu);
       /* MenuItem shareItem = menu.findItem(R.id.menu_item_share);
        ShareActionProvider mShareActionProvider = (ShareActionProvider) shareItem.getActionProvider();*/

        return true;
    }

    /*private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void encrypt(View view) throws Exception {

        EditText encrypto = (EditText)findViewById(R.id.encryptText);

        String text = encrypto.getText().toString();

        EditText keyText = (EditText)findViewById(R.id.keyText);
        String key = keyText.getText().toString();

        EditText encryptedText = (EditText)findViewById(R.id.Encryptedtext);

        /*Toast toast = Toast.makeText(getApplicationContext(),
                "Пора покормить кота!",
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();*/

        AesProceed encrypting = new AesProceed(key);
        encryptedText.setText(encrypting.encrypt(text));

    }

    public void decypt(View view){

        EditText keyText = (EditText)findViewById(R.id.keyText);
        String key = keyText.getText().toString();

        EditText encrypto = (EditText)findViewById(R.id.encryptText);
        String text = encrypto.getText().toString();

        TextView decryptedText = (TextView)findViewById(R.id.Encryptedtext);

        AesProceed decrypting = new AesProceed(key);
        try{
            decryptedText.setText(decrypting.decrypt(text).toString());
        }
        catch(java.lang.NullPointerException e){
            decryptedText.setText("Error!");
        }


    }


    public void toFileEncrypter(MenuItem item) {

        //Intent intent = new Intent(EncryptActivity.this, FileEncrypterActivity.class);
        //startActivity(intent);

        Toast toast = Toast.makeText(getApplicationContext(),
                "In work.",
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.show();

    }

    public void save2file(View view) {

        EditText encryptedFileName = (EditText)findViewById(R.id.encryptedFileName);
        String filename = encryptedFileName.getText().toString();

        if(filename.equals("")){
            filename = "Unnamed Encrypted File";
        }

        EditText encrypto = (EditText)findViewById(R.id.Encryptedtext);

        File sdPatch = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        sdPatch = new File(sdPatch.getPath());
        sdPatch.mkdirs();

        File sdFile = new File(sdPatch, filename);
        try{
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(sdFile));
            bufferedWriter.write(encrypto.getText().toString());
            bufferedWriter.close();

            Toast toast = Toast.makeText(getApplicationContext(),
                    "File "+filename+" saved completely.",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM, 0, 0);
            toast.show();

        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    public void toAbout(MenuItem item) {

        Intent intent = new Intent(EncryptActivity.this, AboutActivity.class);
        startActivity(intent);

    }
}
