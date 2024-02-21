package org.mutualfund;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class Downloader {

    public void download(Map<String, String> fileMapping) throws IOException {

        File file = new File("Download");

        if (!file.exists()) {
            file.mkdir();
        }

        for (Map.Entry<String, String> m : fileMapping.entrySet()) {
            String fileName = m.getValue();
            String downloadFileName = m.getKey();

            downloading("Download", fileName, downloadFileName);
        }

    }

    public void downloading(String path, String toBeDownloadFileName, String toBeSaveFileName) throws IOException {
        String urlPrefix = "https://staticassets.zerodha.com/coin/scheme-portfolio/";
        String savedFileFullName = path + "/" + toBeSaveFileName + ".json";

        System.out.println(savedFileFullName);

        File file = new File(savedFileFullName);
        if (file.exists()) {
            System.out.println("File already Downloaded");
            return;
        } else {
            System.out.println("Downloading......");
        }

        String urlStr = urlPrefix + toBeDownloadFileName;
        System.out.println("URL : " + urlStr);

        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

        int status = con.getResponseCode();
        System.out.println(status);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        FileWriter myWriter = new FileWriter(savedFileFullName);
        myWriter.write(content.toString());
        myWriter.close();
    }


}
