import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Requests {
    // Java 6
    static String makePostUrlEncodedRequest(String strUrl, String[] params) throws IOException {
        String userName = params[0];
        String password = params[1];
        URL url = null;
        InputStream stream = null;
        OutputStreamWriter wr = null;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(strUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);

            String data = URLEncoder.encode("userName", "UTF-8")
                    + "=" + URLEncoder.encode(userName, "UTF-8");

            data += "&" + URLEncoder.encode("password", "UTF-8") + "="
                    + URLEncoder.encode(password, "UTF-8");

            urlConnection.connect();

            wr = new OutputStreamWriter(urlConnection.getOutputStream());
            wr.write(data);
            wr.flush();
            wr.close(); // check if this works
            stream = urlConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"), 8);
            String result = reader.readLine();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (stream != null) {
                stream.close();
            }
        }

        return null;
    }

    // Java 6
    static void makePostJsonRequest(String strUrl, String json) throws IOException {
        URL url = new URL (strUrl);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);

        try(OutputStream os = con.getOutputStream()) {
            byte[] input = json.getBytes("utf-8");
            os.write(input, 0, input.length);			
        }
        // Check if this works since os will be closed


        try(BufferedReader br = new BufferedReader(
            new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
        }
    }

    // Java 8
    static void downloadImage(String url, String filename) throws IOException {
        try(InputStream in = new URL(url).openStream()){
            Files.copy(in, Paths.get(filename));
        }
    }

    // Java 6
    static void downloadImage2(String strUrl, String filename) throws IOException {
        URL url = new URL(strUrl);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
          InputStream in = new BufferedInputStream(urlConnection.getInputStream());
          try {
            byte[] buf = new byte[9128];
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(filename)));
            try {
              int count = 0;
              while ((count  = in.read(buf)) != -1) {
                out.write(buf, 0, count);
              }
            } finally {
                out.close();
            }
          } finally {
              in.close();
          }
        } finally {
          urlConnection.disconnect();
        }
    }

    // Java 8
    static String downloadPage(String url) throws MalformedURLException, IOException {
        try(InputStream in = new URL(url).openStream()) {
            try (ByteArrayOutputStream targetStream = new ByteArrayOutputStream()) {
                in.transferTo(targetStream);
                return targetStream.toString(StandardCharsets.UTF_8);
            }
        }
    }

    // Java 6
    static String downloadPage2(String strUrl) throws MalformedURLException, IOException {
        URL url = new URL(strUrl);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
          var in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
          StringBuilder sb = new StringBuilder();
          String line = null;
          while ((line = in.readLine()) != null) {
            sb.append(line);
          }
          return sb.toString();
        } finally {
          urlConnection.disconnect();
        }
    }

    public static void main(String[] args) throws IOException {
        //downloadImage("https://images.unsplash.com/photo-1579353977828-2a4eab540b9a?crop=entropy&cs=tinysrgb&fm=jpg&ixlib=rb-1.2.1&q=80&raw_url=true&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1374", "test.jpeg");
        //System.out.print(downloadPage("https://example.org"));
        //downloadImage2("https://images.unsplash.com/photo-1579353977828-2a4eab540b9a?crop=entropy&cs=tinysrgb&fm=jpg&ixlib=rb-1.2.1&q=80&raw_url=true&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1374", "test2.jpg");
        //makePostJsonRequest("http://127.0.0.1:8888/jj", "{\"name\": \"Upendra\", \"job\": \"Programmer\"}");
        System.out.println(makePostUrlEncodedRequest("http://127.0.0.1:8888/ff", new String[]{"JohnDoe", "123456"}));
    }
}