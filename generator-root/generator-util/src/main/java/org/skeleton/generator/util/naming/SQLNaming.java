package org.skeleton.generator.util.naming;

import org.skeleton.generator.util.metadata.DatabaseEngine;

public class SQLNaming {
	
	private static final String separator = "_";

	public static String rename(String name, DatabaseEngine databaseEngine)
    {
        if (databaseEngine.equals(DatabaseEngine.ORACLE) && name.length() > 20)
        {
            
            String[] elements = name.toLowerCase().split(separator);
            String result = "";
            boolean start = true;
            for (String element : elements)
            {
                String firstCar = element.substring(0, 1);
                String lastCar = element.substring(element.length() - 1,element.length());
                String coreBody = element.substring(1, element.length() - 1);
                coreBody = coreBody.replace("a", "").replace("e", "").replace("i", "").replace("o", "").replace("u", "").replace("y", "");
                if (start == false)
                {
                    result += separator;
                }
                else
                {
                    start = false;
                }
                result += firstCar + coreBody + lastCar;
            }
            
            if (result.length() > 20) {
            	result = "";
                start = true;
            	for (String element : elements)
                {
                    if (start == false)
                    {
                        result += separator;
                    }
                    else
                    {
                        start = false;
                    }
                    result += element.substring(0,Math.min(element.length(), 3));
                }
            }

            return result.toUpperCase();
        }
        else
        {
            return name;
        }
    }
	
	public static String getInsertProcedureName(String tableName, DatabaseEngine databaseEngine) {
		if (databaseEngine.equals(DatabaseEngine.ORACLE)) {
			return "ins_" + tableName.toLowerCase() + "_bc";
		} else {
			return "ins_" + tableName.toLowerCase() + "_by_code";
		}
	}
	
}
