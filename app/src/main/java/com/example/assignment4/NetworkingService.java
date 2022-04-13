package com.example.assignment4;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Looper;
import android.os.Handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetworkingService {

    private String api = "https://imdb-api.com/en/API/";
    private String SearchMovie = "SearchMovie/k_o43qbfm7/";
    private String SearchTitle = "SearchTitle/k_o43qbfm7/";

    public static ExecutorService networkExecutorService = Executors.newFixedThreadPool(4);
    public static Handler networkingHandler = new Handler(Looper.getMainLooper());

    interface NetworkingListener {
        void dataListener(String jsonString);
        void imageListener(Bitmap image);
    }

    public NetworkingListener listener;

    public void searchMovie(String movie){
        String url = api + SearchMovie + movie;
        connect(url);
    }

    public void movieDetail(String id)
    {
        String url = api + SearchTitle + id;
        connect(url);
    }

    public void getImageData(String icon){
        String iconurl = icon;
        networkExecutorService.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    URL url = new URL(iconurl);
                    Bitmap bitmap = BitmapFactory.decodeStream((InputStream)url.getContent()) ;
                    networkingHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.imageListener(bitmap);
                        }
                    });

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void connect(String url)
    {
        networkExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection httpURLConnection = null;

                try {
                    String jsonString = "";
                    URL urlObj = new URL(url);
                    httpURLConnection= (HttpURLConnection)urlObj.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setRequestProperty("Content-Type","application/json");

                    InputStream in = httpURLConnection.getInputStream();
                    InputStreamReader reader = new InputStreamReader(in);
                    int inputStreamData = 0;
                    while ( (inputStreamData = reader.read()) != -1){
                        char current = (char)inputStreamData;
                        jsonString+= current;
                    } // json is ready
                    // I can send it to somewhere else to decode it

                    final String finalJsonString = jsonString;
                    networkingHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.dataListener(finalJsonString);
                        }
                    });


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    httpURLConnection.disconnect();
                }
            }
        });
    }

}
