package org.cishell.reference.gui.log;

import java.io.File;

import org.osgi.service.log.LogEntry;
import org.osgi.service.log.LogListener;

import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;
import java.io.IOException;

/**
 * This is a basic implementation. It writes log records to files
 * @author Weixia(Bonnie) Huang (huangb@indiana.edu)
 */
public class LogToFile implements LogListener {
	//default log directory
	private static String default_log_dir = 
		System.getProperty("user.dir") 
		+ File.separator + "logs";
	private static String prefix_file_name = "user";
	
	private File currentDir;
	private Logger logger;
	
	//Specify the default size of each log file
	private int limit = 100000; // 100 kb
	
	//Specify the default numbers of log files
	private int max_Number_Of_LogFiles  = 10;

    /**
     * Constructor
     */
    public LogToFile() {
    	
    	try {
            // Create an appending file handler
    		currentDir = getLogDirectory();
    		if (currentDir != null){
    			boolean append = true;
    			String logFileName = currentDir+File.separator+
								generateUniqueFile(prefix_file_name)+
								".%g.log";
    			
    			FileHandler handler = new FileHandler(logFileName, 
            			limit, max_Number_Of_LogFiles, append);
    			
    			handler.setFormatter(new SimpleFormatter());
        
    			// Add to the desired logger
    			logger = Logger.getLogger("edu.iu.iv.logger");
    			logger.addHandler(handler);
    		}
        } catch (IOException e) {
        }

    }
	
    public void logged(final LogEntry entry) {
    	String message = entry.getMessage();
    	
    	if (goodMessage(message)){
    		logger.log(Level.INFO, message+"\n");
    	}
    }
    
    private boolean goodMessage(String msg) {
        if (msg == null || 
                msg.startsWith("ServiceEvent ") || 
                msg.startsWith("BundleEvent ") || 
                msg.startsWith("FrameworkEvent ")) {
            return false;
        } else {
            return true;   
        }
    }

    private static File getLogDirectory(){    	
    	//later, we should get the log directory from preference service
    	File logDir = new File(default_log_dir);
    	if (!logDir.exists() || !logDir.isDirectory()){  
    		try{
    			if (logDir.mkdir()){
    				return logDir;
    			} else {
    				return new File (default_log_dir);
    			}
    		}catch (Exception e){
    			e.printStackTrace();
    			return new File (default_log_dir);
    		}
    		
    	}else
    		return logDir;		
            
    }
    
    /*
     * create log file with given name plus unique timestamp
     */
    private String generateUniqueFile(String prefixFN) {
 
        Calendar now = Calendar.getInstance();
        String month = (now.get(Calendar.MONTH) + 1) + ""; //zero based

        if (month.length() == 1) {
            month = "0" + month;
        }

        String day = now.get(Calendar.DAY_OF_MONTH) + "";

        if (day.length() == 1) {
            day = "0" + day;
        }

        String year = now.get(Calendar.YEAR) + "";
        
        String timestamp = "-" + month + "-" + day + "-" + year + "-"+
        		System.currentTimeMillis();
        return prefixFN+timestamp;        
        
    }

}