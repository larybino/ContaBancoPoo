package util;

import java.util.Calendar;
import java.util.Date;

public class ValidarDatas {
    public static Date getInicioMesAnterior() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);       
        cal.set(Calendar.DAY_OF_MONTH, 1);      
        cal.set(Calendar.HOUR_OF_DAY, 0);      
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

	public static Date getFimMesAnterior() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);               
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 23);         
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }
}
