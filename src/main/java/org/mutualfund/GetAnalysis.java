package org.mutualfund;

import org.mutualfund.beans.CompanyAllocation;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GetAnalysis {

    public void getAnalysis(List<CompanyAllocation> companyAllocations) {
        System.out.println("---------------Analysis---------------");
        Set<String> distinctType = new HashSet<>();
        Set<String> distinctCompany = new HashSet<>();
        HashMap<String, Double> allocationMap = new HashMap<>();
        HashMap<String, Integer> allocationFrequency = new HashMap<>();
        for (CompanyAllocation allocation : companyAllocations) {
            if (!"Equity".equalsIgnoreCase(allocation.getType())) {
                continue;
            }
            distinctType.add(allocation.getType());
            distinctCompany.add(allocation.getName());
            allocationMap.put(allocation.getName(), allocationMap.getOrDefault(allocation.getName(), 0.0) + allocation.getAllocation());
            allocationFrequency.put(allocation.getName(), allocationFrequency.getOrDefault(allocation.getName(), 0) + 1);
        }

        int count = 0;
        allocationFrequency = sortByValueInt(allocationFrequency);
        System.out.println("---allocationFrequency----");
        for (Map.Entry<String, Integer> m : allocationFrequency.entrySet()) {
            System.out.println(m.getKey() +" -> "+m.getValue());
            count++;
            if (count == 1000) {
                break;
            }
        }

//        allocationMap = sortByValueDouble(allocationMap);
//        System.out.println("---allocationMap----");
//        count = 0;
//        for (Map.Entry<String, Double> m : allocationMap.entrySet()) {
//            System.out.println(m.getKey() +" -> "+m.getValue());
//            count++;
//            if (count == 100) {
//                break;
//            }
//        }


    }

    private static HashMap<String, Integer> sortByValueInt(HashMap<String, Integer> hm) {
        List<Map.Entry<String, Integer>> list = new LinkedList<>(hm.entrySet());

        Collections.sort(list, (i1, i2) -> i2.getValue().compareTo(i1.getValue()));

        HashMap<String, Integer> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    private static HashMap<String, Double> sortByValueDouble(HashMap<String, Double> hm) {
        List<Map.Entry<String, Double>> list = new LinkedList<>(hm.entrySet());

        Collections.sort(list, (i1, i2) -> i2.getValue().compareTo(i1.getValue()));

        HashMap<String, Double> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Double> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
}
