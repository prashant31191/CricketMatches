package com.demo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.crickbit.App;
import com.crickbit.R;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by prashant.patel on 9/9/2017.
 */

public class ActPdf extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_splash);


       /* for(int i=1; i<4; i++)
        {
            saveFile(i);
        }
*/

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                final InputStream streamToPdf1 = getFileToStream(Environment.getExternalStorageDirectory() +"/1.pdf");
                final InputStream streamToPdf2 = getFileToStream(Environment.getExternalStorageDirectory() +"/2.pdf");
                final InputStream streamToPdf3 = getFileToStream(Environment.getExternalStorageDirectory() +"/3.pdf");

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if(streamToPdf1 !=null && streamToPdf2 !=null&& streamToPdf3 !=null)
                            {

                            }
                            else
                            {
                                App.showLog("====00000====Some null data===");
                            }

                            downloadAndCombinePDFs(streamToPdf1,streamToPdf2,streamToPdf3);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },5000);
            }
        },5000);
    }


    private void saveFile(int i) {

        String string = "asdasd \n Count = " +i+
                "<html> \n<body> \n<h1>this is test</h1> \n </body> \n </html>";

        String filename = i+".pdf";
        File myFile = new File(Environment.getExternalStorageDirectory()+"/", filename);
        if (!myFile.exists())
            try {
                myFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        FileOutputStream fos;
        byte[] data = string.getBytes();
        try {
            fos = new FileOutputStream(myFile);
            fos.write(data);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private InputStream getFileToStream(String strFilePath) {
        InputStream is = null;
        try
        {
            is = new FileInputStream(strFilePath);
            App.showLog(strFilePath+"<<======is="+is.toString());
            is.close();
        }
        catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            App.showLog("====err===strFilePath="+strFilePath);
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            App.showLog("====err===strFilePath="+strFilePath);
        }

        return is;
    }

    private File downloadAndCombinePDFs(InputStream streamToPdf1, InputStream streamToPdf2, InputStream streamToPdf3) throws IOException {

        if(streamToPdf1 !=null && streamToPdf2 !=null&& streamToPdf3!=null)
        {

        }
        else
        {
            App.showLog("=====1111===Some null data===");
        }

        String filename = "final.pdf";
      //  File file = new File(Environment.getExternalStorageDirectory()+"/", filename);

/*

        MemoryUsageSetting settings = MemoryUsageSetting.setupTempFileOnly().setTempDir(file);

        PDFMergerUtility ut = new PDFMergerUtility();
        ut.addSource(streamToPdf1);
        ut.addSource(streamToPdf2);
        ut.addSource(streamToPdf3);


    //    final File file = new File(getExternalCacheDir(), System.currentTimeMillis() + ".pdf");


        final FileOutputStream fileOutputStream = new FileOutputStream(file);
        try {
            ut.setDestinationStream(fileOutputStream);
            ut.mergeDocuments(MemoryUsageSetting.setupTempFileOnly());

        } finally {
            fileOutputStream.close();
        }

*/









        File result = new File(Environment.getExternalStorageDirectory(), "/result.pdf");
        {
            /*OutputStream fos = null;
            PDFMergerUtility utility = new PDFMergerUtility();
            utility.addSource(streamToPdf1);
            utility.addSource(streamToPdf1);
            utility.setDestinationStream(fos);
            utility.mergeDocuments();

            if(fos !=null){
                App.showLog("======111==fos===="+fos);
            }
            else {
                App.showLog("====000==null==fos====");
            }

            */

            MemoryUsageSetting settings = MemoryUsageSetting.setupTempFileOnly().setTempDir(Environment.getExternalStorageDirectory());
            PDFMergerUtility ut = new PDFMergerUtility();
            ut.addSource(streamToPdf1);
            ut.addSource(streamToPdf2);
            ut.addSource(streamToPdf3);
            ut.setDestinationFileName(result.getCanonicalPath());
            ut.mergeDocuments(settings);

          //  assertThat(result.length(), is( greaterThan(document.length())));
        }












        return result;
    }
}
