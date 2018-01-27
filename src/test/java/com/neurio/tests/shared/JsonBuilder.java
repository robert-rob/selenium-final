package com.neurio.tests.shared;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

/**
 * Created by Robert Tan on 6/3/2016.
 * JsonBuilder Class
 */
public class JsonBuilder {
    /**
     * Creates a Json Object from a map of Strings and Objects
     * Used Objects because vales could String, Int or JsonArrays
     *
     * @param array - Entries going into the Json object
     */
    public static JSONObject getRequest(List<String> array) {
        return getRequest(array, Common.getPropertyValue("billing", "flat"));
    }

    public static JSONObject getRequest(List<String> array, String billingType) {
        Map<String, Object> map = parseRequest(array, billingType);
        JSONObject obj = new JSONObject();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            try {
                obj.put(entry.getKey(), entry.getValue());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return obj;
    }

    /**
     * Creates a Json Object from a map of Strings and Objects
     * Used Objects because vales could String, Int or JsonArrays
     * It ignore Budget Settings and Billing Days settings for now
     * due to issues that causes the web app to crash
     *
     * @param array - Entries going into the Json object
     */
    private static Map<String, Object> parseRequest(List<String> array, String billingsType) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> entry;
        if (billingsType.contains("flat")) {
            map.put("billingType", "flat");
        } else if (billingsType.contains("tiered")) {
            map.put("billingType", "tiered");
        } else {
            map.put("billingType", "timeOfUse");
        }

        for (String element : array) {
            Calendar cal = Calendar.getInstance();
            int today = cal.get(Calendar.DAY_OF_MONTH);
            int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

            switch (element) {
                case "Billing cycle day null":
                    map.put("billingCycleDay", 1);
                    break;
                case "Billing cycle day day != billing cycle day":
                    map.put("billingCycleDay", 1);
                    break;
                case "Billing cycle day day = billing cycle day":
                    map.put("billingCycleDay", today);
                    break;
                case "Billing cycle day day = first day":
                    map.put("billingCycleDay", 1);
                    break;
                case "Billing cycle day day = last day":
                    map.put("billingCycleDay", cal.getActualMaximum(Calendar.DAY_OF_MONTH));
                    break;
                case "Billing cycle day day < billing cycle day":
                    if ((today + 1) <= lastDay) map.put("billingCycleDay", today + 1);
                    break;
                case "Billing cycle day day > billing cycle day":
                    if (today > 1) map.put("billingCycleDay", today - 1);
                    break;
                case "Billing cycle day day << billing cycle day":
                    if ((today + 2) <= lastDay)
                        map.put("billingCycleDay", today + 2);
                    else
                        map.put("billingCycleDay", 2);
                    break;
                case "Billing cycle day day >> billing cycle day":
                    if (today > 2)
                        map.put("billingCycleDay", today - 2);
                    else
                        map.put("billingCycleDay", lastDay - 2);

                    break;

                case "Fixed Charges decimal":
                    map.put("fixedCharge", 8.6);
                    break;
                case "Fixed Charges max":
                    map.put("fixedCharge", 10);
                    break;
                case "Fixed Charges min":
                    map.put("fixedCharge", 0);
                    break;
                case "Fixed Charges text":
                    map.put("fixedCharge", "Money!");
                    break;
                case "Fixed Charges valid":
                    map.put("fixedCharge", 1);
                    break;
                case "Fixed Charges 0.0":
                    map.put("fixedCharge", 0);
                    break;
                case "Fixed Charges zero":
                    map.put("fixedCharge", 0);
                    break;

                case "Electricity Price decimal":
                    map.put("energyRate", 0.86);
                    break;
                case "Electricity Price max":
                    map.put("energyRate", 10);
                    break;
                case "Electricity Price min":
                    map.put("energyRate", 0.1);
                    break;
                case "Electricity Price valid":
                    map.put("energyRate", 0.125);
                    break;
                case "Electricity Price text":
                    map.put("energyRate", "money!");
                    break;
                case "Electricity Price negative":
                    map.put("energyRate", -1);
                    break;
                case "Electricity Price zero":
                    map.put("energyRate", 0);
                    break;

                case "Solar Incentive Rate max":
                    map.put("solarIncentive", 10);
                    break;
                case "Solar Incentive Rate min":
                    map.put("solarIncentive", 1);
                    break;
                case "Solar Incentive Rate valid":
                    map.put("solarIncentive", 5);
                    break;
                case "Solar Incentive Rate zero":
                    map.put("solarIncentive", 0);
                    break;

                case "Solar Services Provider Equipment Owned by Me":
                    entry = new HashMap<>();
                    entry.put("name", "ownership");
                    entry.put("score", 0);
                    map.put("solarServiceCost", entry);
                    break;

                case "Solar Services Provider LeaseRentorLoan":
                    entry = new HashMap<>();
                    entry.put("name", "lease");
                    if (billingsType.contains("flat")) {
                        if (array.get(5).contains("max")) {
                            entry.put("score", 19.98);
                        } else if (array.get(5).contains("min")) {
                            entry.put("score", 0);
                        } else {
                            entry.put("score", 2.1);
                        }
                    } else if (billingsType.contains("tiered")) {
                        if (array.get(7).contains("max")) {
                            entry.put("score", 19.98);
                        } else if (array.get(7).contains("min")) {
                            entry.put("score", 1);
                        } else if (array.get(7).contains("zero")) {
                            entry.put("score", 0);
                        } else {
                            entry.put("score", 2.1);
                        }
                    } else {
                        if (array.get(6).contains("max")) {
                            entry.put("score", 19.98);
                        } else if (array.get(6).contains("min")) {
                            entry.put("score", 1);
                        } else if (array.get(6).contains("zero")) {
                            entry.put("score", 0);
                        } else {
                            entry.put("score", 2.1);
                        }
                    }
                    map.put("solarServiceCost", entry);
                    break;

                case "Solar Services Provider Power Purchase Agreement":
                    entry = new HashMap<>();
                    entry.put("name", "powerPurchase");
                    if (billingsType.contains("flat")) {
                        if (array.get(5).contains("max")) {
                            entry.put("score", 19.98);
                        } else if (array.get(5).contains("min")) {
                            entry.put("score", 0);
                        } else {
                            entry.put("score", 2.1);
                        }
                    } else if (billingsType.contains("tiered")) {
                        if (array.get(7).contains("max")) {
                            entry.put("score", 19.98);
                        } else if (array.get(7).contains("min")) {
                            entry.put("score", 1);
                        } else if (array.get(7).contains("zero")) {
                            entry.put("score", 0);
                        } else {
                            entry.put("score", 2.1);
                        }
                    } else {
                        if (array.get(6).contains("max")) {
                            entry.put("score", 19.98);
                        } else if (array.get(6).contains("min")) {
                            entry.put("score", 1);
                        } else if (array.get(6).contains("zero")) {
                            entry.put("score", 0);
                        } else {
                            entry.put("score", 2.1);
                        }
                    }
                    map.put("solarServiceCost", entry);
                    break;

                case "Taxes yes":
                    map.put("taxRate", 1.5);
                    break;
                case "Taxes no":
                    map.put("taxRate", 0);
                    break;
                case "Taxes Rent":
                    map.put("taxRate", 0);
                    break;
                default:
                    if (element.contains("Tiered")) {
                        int num = Character.getNumericValue(element.charAt(element.length() - 1));
                        double minStartingValue = 0;
                        double minStaringCost = 0.1;
                        double maxStaringCost = 0.8;
                        List list = new ArrayList();
                        for (int i = 0; i < num; i++) {
                            entry = new HashMap<>();
                            if (array.get(1).contains("To (n) > To(n-1)")) {
                                entry.put("minConsumption", minStartingValue);
                                minStartingValue += 0.1;
                                if (i < num - 1) {
                                    entry.put("maxConsumption", minStartingValue);
                                }
                                entry.put("price", minStaringCost);
                                minStaringCost += 0.1;
                            } else if (array.get(1).contains("To(n) < To (n-1)")) {
                                entry.put("minConsumption", minStartingValue);
                                minStartingValue += 0.1;
                                if (i < num - 1) {
                                    entry.put("maxConsumption", minStartingValue);
                                }
                                entry.put("price", maxStaringCost);
                                maxStaringCost -= 0.1;
                            } else if (array.get(1).contains("To(n) == -ve")) {
                                entry.put("minConsumption", minStartingValue);
                                minStartingValue += 0.1;
                                if (i < num - 1) {
                                    entry.put("maxConsumption", minStartingValue);
                                }
                                entry.put("price", 0.2);
                            } else if (array.get(1).contains("To(n) == 0")) {
                                entry.put("minConsumption", minStartingValue);
                                minStartingValue += 0.1;
                                if (i < num - 1) {
                                    entry.put("maxConsumption", minStartingValue);
                                }
                                entry.put("price", 0.1);
                            } else if (array.get(1).contains("To(n) == max")) {
                                entry.put("minConsumption", minStartingValue);
                                minStartingValue += 0.1;
                                if (i < num - 1) {
                                    entry.put("maxConsumption", minStartingValue);
                                }
                                entry.put("price", 1.1);
                            } else if (array.get(1).contains("To(n) == To (n-1)")) {
                                entry.put("minConsumption", minStartingValue);
                                minStartingValue += 0.1;
                                if (i < num - 1) {
                                    entry.put("maxConsumption", minStartingValue);
                                }
                                entry.put("price", 0.5);
                            } else if (array.get(1).contains("To(n) == valid")) {
                                entry.put("minConsumption", minStartingValue);
                                minStartingValue += 0.1;
                                if (i < num - 1) {
                                    entry.put("maxConsumption", minStartingValue);
                                }
                                entry.put("price", 0.2);
                            }
                            list.add(entry);
                        }
                        map.put("pricingTiers", list);
                    } else if (element.contains("Billing Type")) {
                        int num = Integer.parseInt(element.split(" ")[2]);
                        List list = new ArrayList();
                        map.put("billingType", "timeOfUse");
                        entry = new HashMap<>();
                        if (element.contains("Weekends Included")) {
                            entry.put("weekends", "included");
                        } else {
                            entry.put("weekends", "excluded");
                        }
                        int minStaringInterval = 0;
                        double minStaringCost = 0.1;
                        double maxStaringCost = 1.2;
                        Map<String, Object> subEntry;
                        for (int i = 0; i <= num; i++) {
                            subEntry = new HashMap<>();
                            if (array.get(3).contains("Peak-Period Time From(n +1) < To(n)")) {
                                if (i < num) {
                                    subEntry.put("start", minStaringInterval);
                                    minStaringInterval += 1;
                                    subEntry.put("end", minStaringInterval);
                                    subEntry.put("price", maxStaringCost);
                                    maxStaringCost -= 0.1;
                                    subEntry.put("type", "peak");
                                } else {
                                    subEntry.put("start", minStaringInterval);
                                    subEntry.put("end", 24);
                                    subEntry.put("price", maxStaringCost);
                                    subEntry.put("type", "offPeak");
                                }
                            } else if (array.get(3).contains("Peak-Period Time From(n +1) == To(n)")) {
                                if (i < num) {
                                    subEntry.put("start", minStaringInterval);
                                    minStaringInterval += 1;
                                    subEntry.put("end", minStaringInterval);
                                    subEntry.put("price", 0.6);
                                    subEntry.put("type", "peak");
                                } else {
                                    subEntry.put("start", minStaringInterval);
                                    subEntry.put("end", 24);
                                    subEntry.put("price", 0.6);
                                    subEntry.put("type", "offPeak");
                                }
                            } else if (array.get(3).contains("Peak-Period Time From(n +1) > To(n)")) {
                                if (i < num) {
                                    subEntry.put("start", minStaringInterval);
                                    minStaringInterval += 1;
                                    subEntry.put("end", minStaringInterval);
                                    subEntry.put("price", minStaringCost);
                                    minStaringCost += 0.1;
                                    subEntry.put("type", "peak");
                                } else {
                                    subEntry.put("start", minStaringInterval);
                                    subEntry.put("end", 24);
                                    subEntry.put("price", minStaringCost);
                                    subEntry.put("type", "offPeak");
                                }
                            } else if (array.get(3).contains("Peak-Period Time To(n) < From(n)")) {
                                if (i < num) {
                                    subEntry.put("start", minStaringInterval);
                                    minStaringInterval += 1;
                                    subEntry.put("end", minStaringInterval);
                                    subEntry.put("price", maxStaringCost);
                                    minStaringCost -= 0.2;
                                    subEntry.put("type", "peak");
                                } else {
                                    subEntry.put("start", minStaringInterval);
                                    subEntry.put("end", 24);
                                    subEntry.put("price", maxStaringCost);
                                    subEntry.put("type", "offPeak");
                                }
                            } else if (array.get(3).contains("Peak-Period Time To(n) > From(n)")) {
                                if (i < num) {
                                    subEntry.put("start", minStaringInterval);
                                    minStaringInterval += 1;
                                    subEntry.put("end", minStaringInterval);
                                    subEntry.put("price", minStaringCost);
                                    minStaringCost += 0.2;
                                    subEntry.put("type", "peak");
                                } else {
                                    subEntry.put("start", i);
                                    subEntry.put("end", 24);
                                    subEntry.put("price", minStaringCost);
                                    subEntry.put("type", "offPeak");
                                }
                            }
                            list.add(subEntry);
                        }
                        entry.put("pricingPeriods", list);
                        map.put("timeOfUsePricing", entry);
                    } else {
                        System.out.println("!!!!!!!!! Didn't have rules for parsing element: " + element);
                    }
            }
        }
        return map;
    }

    /**
     * Extracts data from an Excel file and turns it to a String Array
     *
     * @param filePath - Path to the excel file
     */
    public static List<List<String>> extractExcelFile(String filePath, int bookNum) {

        List<List<String>> fileContents = new ArrayList<>();
        try {
            InputStream ExcelFileToRead = new FileInputStream(filePath);
            XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);

            XSSFSheet sheet = wb.getSheetAt(bookNum);
            XSSFRow row;
            XSSFCell cell;

            Iterator rows = sheet.rowIterator();

            row = (XSSFRow) rows.next();
            Iterator cells = row.cellIterator();

            List<String> columnNames = new ArrayList<>();
            cells.next();
            while (cells.hasNext()) {
                cell = (XSSFCell) cells.next();

                if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
                    columnNames.add(cell.getStringCellValue());
                } else {
                    System.out.print("Cell type isn't string but is " + cell.getCellType() + "... throwing IO Error");
                    throw new Exception("IO Error");
                }
            }

            while (rows.hasNext()) {
                int count = 0;
                List<String> currentRow = new ArrayList<>();

                row = (XSSFRow) rows.next();
                cells = row.cellIterator();
                cells.next();
                while (cells.hasNext()) {
                    cell = (XSSFCell) cells.next();

                    if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
                        currentRow.add(columnNames.get(count) + " " + cell.getStringCellValue().replace("~", ""));
                        count++;
                    } else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
                        currentRow.add(columnNames.get(count) + " " + cell.getNumericCellValue());
                        count++;
                    } else if (cell.getCellType() == XSSFCell.CELL_TYPE_BLANK) {
                        return fileContents;
                    } else {
                        System.out.println("cellType is " + cell.getCellType() + "... throwing IO Error");
                        throw new Exception("IO Error");
                    }
                }
                fileContents.add(currentRow);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return fileContents;
    }
}
