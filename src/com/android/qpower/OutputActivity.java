package com.android.qpower;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import power.*;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.android.qpower.R;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OutputActivity extends Activity {
	Runtime runtime = Runtime.getRuntime();
    Process proc = null;
    TextView output;
    Button home,back;
    String errorString;
    String device;
    
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.output);
        output = (TextView)this.findViewById(R.id.output);
        output.setMovementMethod(new ScrollingMovementMethod());
        home = (Button)findViewById(R.id.home);
        back = (Button)findViewById(R.id.back);
        device = getIntent().getStringExtra("device");     
        
        switch (StartActivity.Device.valueOf(device)) {
	       	case POWER:  
	        	PowerActivity p1 = new PowerActivity();
	    		output.setTextSize(15);    	
				output.setText( "Output: \n\n"+ p1.powerScript());  
	        	break;
			case POWERCABL:  
	        	PowerActivity p2 = new PowerActivity();
	    		output.setTextSize(15);    	
				output.setText( "Output: \n\n"+ p2.powerScript());  
	        	break;
			case POWERBRIGHT:  
	        	PowerActivity p3 = new PowerActivity();
	    		output.setTextSize(15);    	
				output.setText( "Output: \n\n"+ p3.powerScript());  
	        	break;
			case POWERLOG:  
	        	PowerActivity p4 = new PowerActivity();
	    		output.setTextSize(15);    	
				output.setText( "Output Written to file:" + p4.powerLogScript());  
	        	break;
	        
	        default: errorString = "Invalid device"; break;
	    }
        
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	finish();
            }
        });
        
        home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(OutputActivity.this, StartActivity.class));
            }
        });
	}
	
	public static String displayOnScreen(String calledScript){
		try {
		    Process process = Runtime.getRuntime().exec(calledScript);
			BufferedReader reader = new BufferedReader(
		    new InputStreamReader(process.getInputStream()));
		    int read;
		    char[] buffer = new char[4096];
		    StringBuffer output = new StringBuffer();
		    while ((read = reader.read(buffer)) > 0) {
		        output.append(buffer, 0, read);
		    }
		    reader.close();
		    process.waitFor();
		    return output.toString();

		} catch (IOException e) {
		    throw new RuntimeException(e);
		} catch (InterruptedException e) {
		    throw new RuntimeException(e);
		}
    }
	
	public static void logtoFile(File fp, String calledScript){
		try {
		    Process process = Runtime.getRuntime().exec(calledScript);
		    BufferedWriter writer = null;
		    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		    int read;
		    char[] buffer = new char[4096];
		    StringBuilder output = new StringBuilder();
		    while ((read = reader.read(buffer)) > 0) {
		    	output.append(buffer, 0, read);
		    }
		    try{
		    writer = new BufferedWriter(new FileWriter(fp));
		    writer.write(output.toString());
		    }
		    catch(Exception e)
		    { e.printStackTrace();}
		    finally {
	            //Close the BufferedWriter
	            try {
	                if (writer != null) {
	                    writer.flush();
	                    writer.close();
	                }
	            } catch (IOException ex) {
	                ex.printStackTrace();
	            }
	        }
		    
		    reader.close();		    
		    process.waitFor();		   

		} catch (IOException e) {
		    throw new RuntimeException(e);
		} catch (InterruptedException e) {
		    throw new RuntimeException(e);
		}
    }
	
}
