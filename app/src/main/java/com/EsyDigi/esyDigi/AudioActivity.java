package com.EsyDigi.esyDigi;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jean.jcplayer.general.JcStatus;
import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.service.JcPlayerManagerListener;
import com.example.jean.jcplayer.view.JcPlayerView;
import com.EsyDigi.esyDigi.Interface.ApiResponse;
import com.EsyDigi.esyDigi.adpters.AudioAdapter;
import com.EsyDigi.esyDigi.api.API;
import com.EsyDigi.esyDigi.api.GetAudioFiles;
import com.EsyDigi.esyDigi.api.Item;
import com.EsyDigi.esyDigi.api.ParentActivity;
import com.EsyDigi.esyDigi.api.SongsModel;
import com.EsyDigi.esyDigi.utility.ConnectionDetector;
import com.EsyDigi.esyDigi.utility.PathUtils;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

import static com.EsyDigi.esyDigi.api.ApiRequestCode.REQUEST_GET_AUDIOFILES;

public class AudioActivity extends ParentActivity implements ApiResponse {

    ListView mainList;
    MediaPlayer mp;
    //Add a resource of music files in Array
    final int[] resID = {R.raw.test1, R.raw.test2};
    ArrayList<Item> animalList = new ArrayList<>();
    private AudioAdapter audioAdapter;
    private static final int REQUEST_ID_MULTIPLE_PERMISSION = 2;
    ProgressDialog progressBar;
    private List<SongsModel> songsList = new ArrayList<>();
    private static String dirPath;
    private File file;
    private static String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        getcallAudioApi();
        dirPath = PathUtils.getRootDirPath(getApplicationContext());
        SongsModel songsModel = new SongsModel();
        /*songsModel.setName("test1");
        songsModel.setUrl("https://file-examples.com/wp-content/uploads/2017/11/file_example_MP3_700KB.mp3");
        songsList.add(songsModel);

        SongsModel songsModel1 = new SongsModel();
        songsModel1.setName("test2");
        songsModel1.setUrl("https://ia902508.us.archive.org/5/items/testmp3testfile/mpthreetest.mp3");
        songsList.add(songsModel1);*/


        mainList = (ListView) findViewById(R.id.listView);


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }


    private void getcallAudioApi() {
        try {
            if (ConnectionDetector.isNetworkAvailable(this)) {
                showDialog();


                API.AudioFile(this,REQUEST_GET_AUDIOFILES);
            } else {
                Toast.makeText(this, "Connection TimeOut", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            System.out.println("Exception data:" + e);
        }
    }


    @Override
    public void onSuccess(Response res,int requestCode) {
        hideDialog();
        if (res != null) {
            Toast.makeText(this, res.message(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFailure(Throwable res,int requestCode) {
        hideDialog();
        if (res != null) {
            Toast.makeText(this, res.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSuccessJson(Object res,int requestCode) {
        hideDialog();
        if (requestCode == REQUEST_GET_AUDIOFILES) {
            final GetAudioFiles body = (GetAudioFiles) res;
            if (body != null) {
                Log.e("response", body.toString());
                hideDialog();
                if (body.getStatus().equalsIgnoreCase("ok")) {
                    if (body.getData().size() > 0) {
                        songsList = body.getData();
                        AudioAdapter myAdapter = new AudioAdapter(this, R.layout.audio_item_row, animalList, songsList);
                        mainList.setAdapter(myAdapter);
                        myAdapter.notifyDataSetChanged();
                    } else {

                    }
                }

//                MySharedPreferenceClass.setcookie(getApplicationContext(), body.get);

            }
        }
        }



    /**/
    /*https://ia902508.us.archive.org/5/items/testmp3testfile/mpthreetest.mp3*/
    public void openDialog(int pos) {
        file = new File(dirPath + "/" + "Audio Files");
        if (file.exists()) {
            path = file.getAbsolutePath();
            File file1 = new File(path + "/" + songsList.get(pos).getTitle() + ".mp3");
            if (file1.exists()) {
                //play audio file
                playMusic(file1.getAbsolutePath());
            } else {
                new AudioDownloader().execute(songsList.get(pos).getMp3Url(), path + "/" + songsList.get(pos).getTitle() + ".mp3", path, songsList.get(pos).getTitle());
            }

        } else {
            file.mkdirs();
            path = file.getAbsolutePath();
            new AudioDownloader().execute(songsList.get(pos).getMp3Url(), path + "/" + songsList.get(pos).getTitle() + ".mp3", path, songsList.get(pos).getTitle());

        }
    }

    public void playMusic(String path) {
        ArrayList<JcAudio> jcAudios = new ArrayList<>();
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.audio_player_view);

        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        Double width = metrics.widthPixels * .8;
        Double height = metrics.heightPixels * .18;
        Window win = dialog.getWindow();
        win.setLayout(width.intValue(), height.intValue());
//                dialog.getWindow().setLayout(80, 80);

        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        final JcPlayerView jcPlayerView = dialog.findViewById(R.id.jcplayer);
        jcAudios.add(JcAudio.createFromFilePath("", path));

        jcPlayerView.initPlaylist(jcAudios, null);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                jcPlayerView.kill();
                dialog.dismiss();

            }
        });
        jcPlayerView.setJcPlayerManagerListener(new JcPlayerManagerListener() {
            @Override
            public void onPreparedAudio(@NotNull JcStatus jcStatus) {

            }

            @Override
            public void onCompletedAudio() {
                jcPlayerView.kill();
                dialog.dismiss();
            }

            @Override
            public void onPaused(@NotNull JcStatus jcStatus) {

            }

            @Override
            public void onContinueAudio(@NotNull JcStatus jcStatus) {

            }

            @Override
            public void onPlaying(@NotNull JcStatus jcStatus) {

            }

            @Override
            public void onTimeChanged(@NotNull JcStatus jcStatus) {


            }

            @Override
            public void onJcpError(@NotNull Throwable throwable) {

            }
        });

    }

    // Async task for download audio files
    public class AudioDownloader extends AsyncTask<String, Integer, Void> {
        public static final int DATA_READY = 1;
        public static final int DATA_NOT_READY = 2;
        public static final int DATA_CONSUMED = 3;
        public static final int DATA_NOT_AVAILABLE = 4;
        public String path;
        public Context context;
        ProgressDialog mProgressDialog;


        public int dataStatus = -1;
        /**
         * Keeps track of read bytes while serving to video player client from server
         */
        public int consumedb = 0;
        /**
         * Length of file being downloaded.
         */
        int fileLength = -1;
        /**
         * Keeps track of all bytes read on each while iteration
         */
        private int readb = 0;

        public boolean isDataReady() {
            dataStatus = -1;
            boolean res = false;
            if (fileLength == readb) {
                dataStatus = DATA_CONSUMED;
                res = false;
            } else if (readb > consumedb) {
                dataStatus = DATA_READY;
                res = true;
            } else if (readb <= consumedb) {
                dataStatus = DATA_NOT_READY;
                res = false;
            } else if (fileLength == -1) {
                dataStatus = DATA_NOT_AVAILABLE;
                res = false;
            }
            return res;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(AudioActivity.this, ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);
            mProgressDialog.setMessage("please wait...");
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {


            BufferedInputStream input = null;
            try {
                final FileOutputStream out = new FileOutputStream(params[1]);

                try {
                    URL url = new URL(params[0]);
                    path = params[1];

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.connect();
                    if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                        throw new RuntimeException("response is not http_ok");
                    }
                    fileLength = connection.getContentLength();

                    input = new BufferedInputStream(connection.getInputStream());
                    byte data[] = new byte[1024 * 50];
                    long readBytes = 0;
                    int len;
                    boolean flag = true;

                    while ((len = input.read(data)) != -1) {
                        out.write(data, 0, len);
                        out.flush();
                        readBytes += len;
                        readb += len;
//                        progressBar.setProgress(readb);

                        Log.w("download", (readb / 1024) + "kb of " + (fileLength / 1024) + "kb");

                        publishProgress((int) ((readb * 100) / fileLength));
                        Log.e("Progress ", String.valueOf((readb / 1024) / (fileLength / 1024)));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    File file = new File(params[2], params[3]);
                    if (file.exists())
                        file.delete();
                } finally {
                    if (out != null) {
                        out.flush();
                        out.close();
                    }
                    if (input != null)
                        input.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mProgressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mProgressDialog.dismiss();
//            Toast.makeText(AudioActivity.this, "Downloading completed", Toast.LENGTH_SHORT).show();
            Log.w("download", "Done");
            playMusic(path);
            // Toast.makeText(, "done", Toast.LENGTH_SHORT).show();
        }

    }

}

