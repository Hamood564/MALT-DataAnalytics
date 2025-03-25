package tqc.malt.api;

import org.springframework.stereotype.Service;

import application.StatusLights;
import maltdriver.Malt;
import maltdriver.Response;
import settings.CalibrationSettings.Channel;
import settings.Option;
import settings.OptionSettings;
import settings.Param;
import settings.TestSettings;

@Service
public class MaltService {
	public String startTest(String ipAddress, int testIndex) {
		  try {
	            Malt malt = new Malt(ipAddress, 5000);
	            if (!malt.isReady()) {
	                return "MALT unit at " + ipAddress + " is not ready for testing.";
	            }
	            malt.selectTest(testIndex);
	            Response response = malt.start();
	            return response.getMessage();
	        } catch (Exception e) {
	            return "Error starting test on " + ipAddress + ": " + e.getMessage();
	        }
	}
	
	
	
	public String reset(String ipAddress) {
		try {
            int port = 5000;  // Default MALT port
            Malt malt = new Malt(ipAddress, port); // Use the IP provided in the request
            
            if (!malt.isConnected()) {
                return "Connection failed to " + ipAddress;
            }
            
            Response res = malt.reset();
            Thread.sleep(500); // wait for reset to complete
            return res.getMessage(); // Return MALT's response message
            
		} catch (Exception e) {
            e.printStackTrace();
            return "Error resetting on " + ipAddress + ": " + e.getMessage();
        }

	}

	public String getTestSettings(String ipAddress, Integer testIndex) {
		try {
            Malt malt = new Malt(ipAddress, 5000);
            Response response = malt.requestTestSettings(testIndex);
            return response.asJSON();
        } catch (Exception e) {
            return "Error retrieving test settings: " + e.getMessage();
        }
	
	}

	public String getCalibSettings(String ipAddress, String channel) {
		try {
            Malt malt = new Malt(ipAddress, 5000);
            Response response = malt.requestCalibrationSettings(Channel.valueOf(channel));
            return response.asJSON();
        } catch (Exception e) {
            return "Error retrieving calibration settings: " + e.getMessage();
        }
	}

	public String getOptSettings(String ipAddress) {
		 try {
	            Malt malt = new Malt(ipAddress, 5000);
	            Response response = malt.requestOptionSettings();
	            return response.asJSON();
	        } catch (Exception e) {
	            return "Error retrieving option settings: " + e.getMessage();
	        }
	}
	
	

	public String[] setCalibrationSettings(String ipAddress, String calibrationJson) {
		 try {
	            Malt malt = new Malt(ipAddress, 5000);
	            Response[] response = malt.settings(calibrationJson);
	            
	         // Convert each Response object to JSON
	            String[] jsonResponses = new String[response.length];
	            for (int i = 0; i < response.length; i++) {
	                jsonResponses[i] = response[i].asJSON();
	            }

	            return jsonResponses;
				
	        } catch (Exception e) {
	        	 return new String[]{"Error setting calibration settings: " + e.getMessage()};
	        }
	}
	public String[] setOptionSettings(String ipAddress, String optionJson) {
		 try {
	            Malt malt = new Malt(ipAddress, 5000);
	            Response[] response = malt.settings(optionJson);
	         // Convert each Response object to JSON
	            String[] jsonResponses = new String[response.length];
	            for (int i = 0; i < response.length; i++) {
	                jsonResponses[i] = response[i].asJSON();
	            }

	            return jsonResponses;
	        } catch (Exception e) {
	        	 return new String[]{"Error setting calibration settings: " + e.getMessage()};
	        }
	}
	public String[] setTestSettings(String ipAddress, String testJson) {
		try {
            Malt malt = new Malt(ipAddress, 5000);
            Response[] response = malt.settings(testJson);
            // Convert each Response object to JSON
            String[] jsonResponses = new String[response.length];
            for (int i = 0; i < response.length; i++) {
                jsonResponses[i] = response[i].asJSON();
            }

            return jsonResponses;
        } catch (Exception e) {
        	 return new String[]{"Error setting calibration settings: " + e.getMessage()};
        }
	}

	public Boolean isTestRunning(String ipAddress) {
		try {
            Malt malt = new Malt(ipAddress, 5000);
            return malt.testRunning();
        } catch (Exception e) {
            return false;
        }
	}

	public String getStatusLights(String ipAddress) {
		try {
            Malt malt = new Malt(ipAddress, 5000);
            int val = Integer.parseInt(malt.requestStatus().getMessage());
            return "Status Lights: " + val;
        } catch (Exception e) {
            return "Error retrieving status lights: " + e.getMessage();
        }
	}

	public String getResultCode(String ipAddress) {
		 try {
	            Malt malt = new Malt(ipAddress, 5000);
	            Response response = malt.requestResultCode();
	            return response.asJSON();
	        } catch (Exception e) {
	            return "Error retrieving result code: " + e.getMessage();
	        }
	}

	public String getLastData(String ipAddress) {
		 try {
	            Malt malt = new Malt(ipAddress, 5000);
	            Response response = malt.requestLastData();
	            return response.asJSON();
	        } catch (Exception e) {
	            return "Error retrieving last data: " + e.getMessage();
	        }
	}

	public String configureMalt(String ipAddress, String configJson) {
		try {
            Malt malt = new Malt(ipAddress, 5000);
            Response[] responses = malt.settings(configJson);
            StringBuilder result = new StringBuilder();
            for (Response response : responses) {
                result.append(response.asJSON()).append("\n");
            }
            return result.toString();
        } catch (Exception e) {
            return "Error configuring MALT: " + e.getMessage();
        }
	}

	
}
