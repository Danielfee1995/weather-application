# weather-application
the tips of the first achieve assigment
----------------------------------------
* add the new icon to the application
* add the different color backbround 
* add the refresh button
* the information change when the refresh button is pressed
Here is the design we need to achieve.
![](https://github.com/Danielfee1995/weather-application/blob/master/photo/design.jpg)

add the different color backbrounds.and then change the layout_width and layout_height the same to circle. the backgroud use 
different circle XML files.in those files the color and the size are different.

```
            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:background="@drawable/circlebuttonred"
                android:gravity="center"
                android:text="thu"
                android:textAllCaps="true"
                android:textColor="#909090" />
```
add the refresh button.using the ImageButton and the circle XML file.
```
<ImageButton
                android:id="@+id/imageButton8"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_alignTop="@+id/linearLayout"
                android:layout_marginTop="22dp"
                android:layout_toLeftOf="@+id/tv_temperature"
                android:layout_toStartOf="@+id/tv_temperature"
                android:background="@android:color/transparent"
                android:onClick="btncirClick"
                app:srcCompat="@drawable/circlebutton" />
```

the information change when the refresh button is pressed.in this step, I made a mistake.I understand all information should 
select from internet.but the teacher provide the inf just only has temperature.So I chiosed another URL website to load.but 
inf organized by XML file.I almoast crash. I found method how to make stream to XML file.later,i was talled can use the local
inf load on UI.then i changed.the first code is using internet API.the second code is easy!
```
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
```
```
 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH:mm");
        SimpleDateFormat formatweek = new SimpleDateFormat("EEEE", Locale.ENGLISH);
        String t=format.format(new Date());
        String week=formatweek.format(new Date());
        ((TextView) findViewById(R.id.tv_date)).setText(t);
        ((TextView) findViewById(R.id.week)).setText(week);
        new DownloadUpdate().execute();
        Toast.makeText(this, "click the button", Toast.LENGTH_SHORT).show();
```
