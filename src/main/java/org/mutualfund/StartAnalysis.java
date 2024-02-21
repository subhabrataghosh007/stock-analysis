package org.mutualfund;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.mutualfund.beans.CompanyAllocation;
import org.mutualfund.beans.Root;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StartAnalysis {

    public static void main(String[] args) throws IOException, ParseException {

        String folder = "Download/AllEquity/";
//        String folder = "Download/midcap/";
//        String folder = "Download/smallcap/";

        File file = new File(folder);

        List<CompanyAllocation> companyAllocations = new ArrayList<>();
        if (file.exists()) {
            for (String fileName : file.list()) {
                StringBuilder builder = new StringBuilder(folder);
                builder.append(fileName);

                companyAllocations.addAll(getCompanyAllocation(builder.toString()));
            }
        }
//        System.out.println("size:"+companyAllocations.size());

        GetAnalysis getAnalysis = new GetAnalysis();

        getAnalysis.getAnalysis(companyAllocations);

    }

    private static List<CompanyAllocation> getCompanyAllocation(String fileName) throws IOException, ParseException {

        FileReader fr = new FileReader(fileName);
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(fr);

        ObjectMapper objectMapper = new ObjectMapper();
        Root root = objectMapper.readValue(jsonObject.toJSONString(), Root.class);

        List<CompanyAllocation> companyAllocations = new ArrayList<>();

        for (List<Object> data : root.getData()) {
            CompanyAllocation companyAllocation = new CompanyAllocation();
            String name = (String) data.get(1);
            name = name.toUpperCase();
            name = name.replaceAll("[^a-zA-Z ]", "");
            name = name.replace("LIMITED", "LTD");
            name = name.replace("COMPANY", "CO");
            name = name.replace("LTD", "");
            name = name.trim();

            String type = (String) data.get(3);
            double allocation = (double) data.get(5);

            companyAllocation.setName(name);
            companyAllocation.setType(type);
            companyAllocation.setAllocation(allocation);
            companyAllocations.add(companyAllocation);
        }
        return companyAllocations;
    }
}
