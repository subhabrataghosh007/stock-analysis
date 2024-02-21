package org.mutualfund;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mutualfund.enums.MutualFundType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;

import static org.mutualfund.constant.MutualFundConstant.ALL_EQUITY_DESTINATION;
import static org.mutualfund.constant.MutualFundConstant.MID_CAP_DESTINATION;
import static org.mutualfund.constant.MutualFundConstant.SMALL_CAP_DESTINATION;

public class FileNameParser {

    public static void main(String... args) throws IOException {

        FileNameParser fileNameParser = new FileNameParser();

        String fileName = "mid_cap_equity_mutual_fund.html";
        MutualFundType type = MutualFundType.MID_CAP;
        fileNameParser.downloadBaseOnFile(fileName,  type);


//        String fileName = "small_cap_equity_mutual_fund.html";
//        MutualFundType type = MutualFundType.SMALL_CAP;
//        fileNameParser.downloadBaseOnFile(fileName,  type);


//        String fileName = "equity_mutual_fund.html";
//        MutualFundType type = MutualFundType.ALL_EQUITY;
//        fileNameParser.downloadBaseOnFile(fileName,  type);

    }

    private void downloadBaseOnFile(String fileName, MutualFundType type) throws IOException {
        FileReader fr = new FileReader(fileName);
        BufferedReader br = new BufferedReader(fr);

        StringBuilder stringBuilder = new StringBuilder();
        br.lines().forEach(stringBuilder::append);

        Document document = Jsoup.parse(stringBuilder.toString());
        Elements links = document.select("a");

        for (Element link : links) {
            String toBeSaveFileName = link.children().select("span").get(0).text();
            System.out.println("Mutual Fund Name: " + toBeSaveFileName);

            String toBeDownloadFileName = link.attr("href").replace("/mf/fund/", "") + ".json";
            System.out.println("Download File name: " + toBeDownloadFileName);

            if (MutualFundType.MID_CAP == type) {
                String sourceFilePath = ALL_EQUITY_DESTINATION + toBeSaveFileName +".json";
                String destFilePath = MID_CAP_DESTINATION + toBeSaveFileName +".json";
                callingDownloader(sourceFilePath, destFilePath, toBeSaveFileName, toBeDownloadFileName, MID_CAP_DESTINATION);
            } else if (MutualFundType.SMALL_CAP == type) {
                String sourceFilePath = ALL_EQUITY_DESTINATION + toBeSaveFileName +".json";
                String destFilePath = SMALL_CAP_DESTINATION + toBeSaveFileName +".json";
                callingDownloader(sourceFilePath, destFilePath, toBeSaveFileName, toBeDownloadFileName, SMALL_CAP_DESTINATION);
            } else if (MutualFundType.ALL_EQUITY == type) {
                String sourceFilePath = ALL_EQUITY_DESTINATION + toBeSaveFileName +".json";
                String destFilePath = ALL_EQUITY_DESTINATION + toBeSaveFileName +".json";
                callingDownloader(sourceFilePath, destFilePath, toBeSaveFileName, toBeDownloadFileName, ALL_EQUITY_DESTINATION);
            }
        }

        br.close();
        fr.close();
    }

    private void callingDownloader(String sourceFilePath, String destFilePath, String toBeSaveFileName, String toBeDownloadFileName, String toBeSavePath) throws IOException {
        Downloader downloader = new Downloader();

        File sourceFile = new File(sourceFilePath);
        File destFile = new File(destFilePath);

        if (destFile.exists()) {
            return;
        } else if (sourceFile.exists()) {
            copyFile(sourceFile, destFile);
        } else {
            downloader.downloading(toBeSavePath, toBeDownloadFileName, toBeSaveFileName);
        }
    }

    private static void copyFile(File source, File dest) throws IOException {
        Files.copy(source.toPath(), dest.toPath());
    }

}
