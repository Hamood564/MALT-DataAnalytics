package tqc.malt.api;

import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/api/malt")
public class MALTController {
	
	private final MaltService maltService;
	public MALTController(MaltService maltService) {
        this.maltService = maltService;
    }

	@GetMapping("/test")
	public String testConnection() {
		return "API works";
	}
	
	@GetMapping("/test-settings")
	public ResponseEntity<String> getTestSettings(@RequestParam String ipAddress,@RequestParam int testIndex ){
		String response = maltService.getTestSettings(ipAddress,testIndex);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/calibration-settings")
	public ResponseEntity<String> getCalibSettings(@RequestParam String ipAddress, @RequestParam String channel){
		String response = maltService.getCalibSettings(ipAddress,channel);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/option-settings")
	public ResponseEntity<String> getOptSettings(@RequestParam String ipAddress){
		String response = maltService.getOptSettings(ipAddress);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/set-calibration")
	public ResponseEntity<List<String>> setCalibSettings(@RequestParam String ipAddress,@RequestBody String calibrationJson ){
		String[] response = maltService.setCalibrationSettings(ipAddress, calibrationJson);
		return ResponseEntity.ok(Arrays.asList(response));
	}
	
	@PostMapping("/set-option")
	public ResponseEntity<List<String>> setOptSettings(@RequestParam String ipAddress,@RequestBody String optionJson ){
		String response[] = maltService.setOptionSettings(ipAddress, optionJson);
		return ResponseEntity.ok(Arrays.asList(response));
	}
	
	@PostMapping("/set-test")
	public ResponseEntity<List<String>> setTestSettings(@RequestParam String ipAddress,@RequestBody String testJson ){
		String response[] = maltService.setTestSettings(ipAddress, testJson);
		return ResponseEntity.ok(Arrays.asList(response));
	}
	
	
	@GetMapping("/is-test-running")
    public ResponseEntity<Boolean> isTestRunning(@RequestParam String ipAddress) {
        return ResponseEntity.ok(maltService.isTestRunning(ipAddress));
    }

    @GetMapping("/status-lights")
    public ResponseEntity<String> getStatusLights(@RequestParam String ipAddress) {
        return ResponseEntity.ok(maltService.getStatusLights(ipAddress));
    }

    @GetMapping("/result-code")
    public ResponseEntity<String> getResultCode(@RequestParam String ipAddress) {
        return ResponseEntity.ok(maltService.getResultCode(ipAddress));
    }

    @GetMapping("/last-data")
    public ResponseEntity<String> getLastData(@RequestParam String ipAddress) {
        return ResponseEntity.ok(maltService.getLastData(ipAddress));
    }

    @PostMapping("/configure")
    public ResponseEntity<String> configureMalt(@RequestParam String ipAddress, @RequestBody String configJson) {
        return ResponseEntity.ok(maltService.configureMalt(ipAddress, configJson));
    }
		
	
	@PostMapping("/start-test")
    public ResponseEntity<String> startTest(@RequestParam String ipAddress, @RequestParam int testIndex) {
        String response = maltService.startTest(ipAddress, testIndex);
        return ResponseEntity.ok(response);
    }
	
	@PostMapping("/reset")
    public ResponseEntity<String> reset(@RequestParam String ipAddress) {
        String response = maltService.reset(ipAddress);
        return ResponseEntity.ok(response);
    }
}
