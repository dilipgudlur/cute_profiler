package power;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.android.qpower.R;
import com.android.qpower.OutputActivity;
import com.android.qpower.StartActivity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Calendar;
import java.io.IOException;

public class PowerActivity extends Activity {
	Button onScreen,log,cabl,bright,home,exit,back;
    int device;
    static String testOptions=" ";
           
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.power);
        onScreen = (Button)findViewById(R.id.onScreen);
        log = (Button)findViewById(R.id.log);
		cabl = (Button)findViewById(R.id.cabl);
		bright = (Button)findViewById(R.id.bright);
        home = (Button)findViewById(R.id.home);
        back = (Button)findViewById(R.id.back);
        exit = (Button)findViewById(R.id.exit);
		
        exit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				android.os.Process.killProcess(android.os.Process.myPid());
			}
		});
        
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        
        home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(PowerActivity.this, StartActivity.class));
            }
        });
        
       /*rwtest.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				generateTestOptions();				
				String nominal = " -n";
				setTestOptions(getTestOptions().concat(nominal));
				//setTestOptions();
				Intent i = new Intent(SDActivity.this, OutputActivity.class);
			        i.putExtra("device", "SD");
				startActivity(i);						        
			}
		});
        
        trace.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				generateTestOptions();				
				//String trace = " -t";
				//setTestOptions(getTestOptions().concat(trace));
				Intent i = new Intent(SDActivity.this, OutputActivity.class);
			        i.putExtra("device", "SDTRACE");	
				startActivity(i);
			}
		});
       
	   preference.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(SDActivity.this, SDPreferencesActivity.class);	        
				startActivity(i);
			}
		});
        
		help.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				generateTestOptions();				
				String help = " -h";
				setTestOptions(getTestOptions().concat(help));
				Intent i = new Intent(PowerActivity.this, OutputActivity.class);
			    i.putExtra("device", "SD");	
				startActivity(i);				     
			}
		});*/
        
		onScreen.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//generateTestOptions();				
				String runscript = "r";
				setTestOptions(getTestOptions().concat(runscript));
				//setTestOptions("");
				Intent i = new Intent(PowerActivity.this, OutputActivity.class);
			        i.putExtra("device", "POWER");
				startActivity(i);						        
			}
		});
		cabl.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//generateTestOptions();				
				String cabl = "c";
				setTestOptions(getTestOptions().concat(cabl));
				//setTestOptions("");
				Intent i = new Intent(PowerActivity.this, OutputActivity.class);
			        i.putExtra("device", "POWERCABL");
				startActivity(i);						        
			}
		});
		bright.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//generateTestOptions();				
				String bright = "b";
				setTestOptions(getTestOptions().concat(bright));
				//setTestOptions("");
				Intent i = new Intent(PowerActivity.this, OutputActivity.class);
			        i.putExtra("device", "POWERBRIGHT");
				startActivity(i);						        
			}
		});
		log.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//generateTestOptions();				
				String log = " -n"; //will run nominal test only for now
				setTestOptions(getTestOptions().concat(log));
				Intent i = new Intent(PowerActivity.this, OutputActivity.class);
			        i.putExtra("device", "POWERLOG");	
				startActivity(i);
			}
		});
    }
            
    public String powerScript()
    {
    	String powerStr = "/data/local/qpower.sh";
    	String tempOptions = getTestOptions();
    	setTestOptions(" ");
    	powerStr = powerStr.concat(tempOptions);
    	return OutputActivity.displayOnScreen(powerStr);
    }
	public String powerLogScript()
    {
    	
		try{
		Process process = Runtime.getRuntime().exec("mkdir /data/local/qpower-logs 755");
		} catch (IOException e) {
		    throw new RuntimeException(e);
		}
		File fp = new File("/data/local/qp-"+getDateTime()+".txt");
    	if(!fp.exists()){
    		try {
				fp.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
		String powerStr = "/data/local/qpower.sh";
    	//String tempOptions = getTestOptions();
    	//setTestOptions(" ");
    	//sdStr = sdStr.concat(tempOptions);
    	OutputActivity.logtoFile(fp,powerStr);
    	return fp.getName();
    }
	
    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }
	
    /*public void generateTestOptions()
    {
    	if(!editTextKB.getText().toString().equals(""))
			testOptions = testOptions.concat(editTextKB.getText().toString());
	}*/
    
    public String getTestOptions()
    {	
    	return testOptions;
    }
    
    public void setTestOptions(String str)
    {
    	testOptions = str;
    }
}
