package com.example.legendpeng.weather_application;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;




import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnClick(View view) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH:mm");
        SimpleDateFormat formatweek = new SimpleDateFormat("EEEE", Locale.ENGLISH);
        String t=format.format(new Date());
        String week=formatweek.format(new Date());
        ((TextView) findViewById(R.id.tv_date)).setText(t);

        ((TextView) findViewById(R.id.week)).setText(week);
        new DownloadUpdate().execute();
        Toast.makeText(this, "click the button", Toast.LENGTH_SHORT).show();
    }

    public void btncirClick(View view) {
        new DownloadUpdate().execute();
        Toast.makeText(this, "click the reflesh button", Toast.LENGTH_SHORT).show();
    }



    private android.util.Log log;
    private class DownloadUpdate extends AsyncTask<String, Void, String> {




        @Override
        protected String doInBackground(String... strings) {
            String stringUrl = "http://wthrcdn.etouch.cn/WeatherApi?citykey=101040100";

            HttpURLConnection urlConnection = null;
           BufferedReader reader;



            try {
                URL url = new URL(stringUrl);

                // Create the request to get the information from the server, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;

                //log.d("line is",);
                while ((line = reader.readLine()) != null) {

                    // Mainly needed for debugging
                    buffer.append(line + "\n");
                    log.i("buffer append is:",line);
                   //log.i("count:");

                }


                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                //The temperature

                log.d("buff are:",buffer.toString());

                return buffer.toString();
                //return location;




            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

//            try {
//                URL url = new URL(stringUrl);
//                urlConnection = (HttpURLConnection) url.openConnection();
//                urlConnection.setRequestMethod("GET");
//                urlConnection.setConnectTimeout(8000);
//                urlConnection.setReadTimeout(8000);
//                InputStream in = urlConnection.getInputStream();
//                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//                StringBuffer sb = new StringBuffer();
//                String str;
//                while((str=reader.readLine())!=null)
//                {
//                    sb.append(str);
//                    Log.d("date from url",str);
//                }
//                String response = sb.toString();
//                Log.d("response",response);
//            }catch (Exception e)
//            {
//                e.printStackTrace();
//            }
//            return null;
//        }
        @Override
        protected void onPostExecute(String buffer) {
            log.i("333","w1111");



            try {
                org.dom4j.Document document= DocumentHelper.parseText(buffer);
                log.i("333","2222");
                org.dom4j.Element root;
                root = document.getRootElement();

                //List<org.dom4j.Element> list=root.elements();
                //log.i("list",root.getName());

                    log.i("333","333");
                    String temperature=root.element("wendu").getText();
                    String location=root.element("city").getText();
                    String date=root.element("forecast").element("weather").element("date").getText();
                    log.i("name",temperature);
                    ((TextView) findViewById(R.id.temperature_of_the_day)).setText(temperature);
                    ((TextView) findViewById(R.id.tv_location)).setText(location);

                    //String week=date.substring(date.length()-3,date.length());
                    //((TextView) findViewById(R.id.week)).setText(week);


            } catch (DocumentException e) {
                e.printStackTrace();
            }

        }



//            String location=new String();
//            String date=new String();
//            String temperaturehigh=new String();
//            String temperaturelow=new String();
//
//            String location_s="<city>(.*)</city>";
//            String date_s="<date>(.*)</date>";
//            String temperature_high="<high>(.*)</high>";
//            String temperature_low="<low>(.*)</low>";
//            //location
//            Pattern pattern = Pattern.compile(location_s);
//            Matcher matcher=pattern.matcher(buffer);
//            while (matcher.find())
//            {
//                location=matcher.group(1);
//                log.i("location is:",location);
//            }
//            //date
//            Pattern pattern_date = Pattern.compile(date_s);
//            Matcher matcher_date=pattern_date.matcher(buffer);
//            while (matcher_date.find())
//            {
//                date=matcher_date.group(1);
//                log.i("date is:",date);
//            }
//            //temperature high
//            Pattern pattern_high = Pattern.compile(temperature_high);
//            Matcher matcher_high=pattern_high.matcher(buffer);
//            while (matcher_high.find())
//            {
//                temperaturehigh=matcher_high.group(1);
//                log.i("temperaturehigh is:",temperaturehigh);
//            }
//            //temperature low
//            Pattern pattern_low = Pattern.compile(temperature_low);
//            Matcher matcher_low=pattern_low.matcher(buffer);
//            while (matcher_low.find())
//            {
//                temperaturelow=matcher_low.group(1);
//                log.i("temperaturelow is:",temperaturelow);
//            }





            //Update the temperature displayed
//            ((TextView) findViewById(R.id.temperature_of_the_day)).setText(temperaturehigh);
//            ((TextView) findViewById(R.id.tv_location)).setText(location);
//            ((TextView) findViewById(R.id.tv_date)).setText(date);


    }
}