import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class sendPost {
    public static String sendPost(String url,String param)
    {
        String result="";
        try{
            URL httpurl = new URL(url);
            HttpURLConnection httpConn = (HttpURLConnection)httpurl.openConnection();
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            PrintWriter out = new PrintWriter(httpConn.getOutputStream());
            out.print(param);
            out.flush();
            out.close();
            BufferedReader in = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
            String line;
            while ((line = in.readLine())!= null)
            {
                result += line;
            }
            in.close();
        }catch(Exception e){
            System.out.println("Helloword！"+e);
        }
        return result;
    }
}
