package idoelad.finalproject.core.userparams;

import idoelad.finalproject.core.bigtouch.UserParamsBigTouch;
import idoelad.finalproject.core.deviationtouch.UserParamsDeviationTouch;
import idoelad.finalproject.core.multitouch.UserParamsMultiTouch;
import idoelad.finalproject.core.userparams.UserParamsHolder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;


public class UserParamsHandler {
	public static final String USER_PARAMS_DIR = "/storage/emulated/0/TremorTouchLauncher/userParams";

	/////////////// LOAD ///////////////
	public static UserParamsBigTouch loadUserParamsBig() throws FileNotFoundException, IOException{
		return mapToParamsBig(loadUserParams());
	}
	
	public static UserParamsMultiTouch loadUserParamsMulti() throws FileNotFoundException, IOException{
		return mapToParamsMulti(loadUserParams());
	}
	
	public static UserParamsDeviationTouch loadUserParamsDev() throws FileNotFoundException, IOException{
		return mapToParamsDev(loadUserParams());
	}
	
	private static HashMap<String, HashMap<String, Double>> loadUserParams(String dirPath) throws FileNotFoundException, IOException{
		HashMap<String, HashMap<String, Double>> params = new HashMap<String, HashMap<String,Double>>();
		File folder = new File(dirPath);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				String type = getType(listOfFiles[i].getName());
				HashMap<String, Double> weights = getWeights(listOfFiles[i]);
				params.put(type, weights);
			}
		}
		return params;
	}
	
	private static HashMap<String, HashMap<String, Double>> loadUserParams() throws FileNotFoundException, IOException{
		return loadUserParams(USER_PARAMS_DIR);
	}
	
	
	private static String getType(String name) {
		return name.substring(name.lastIndexOf("_")+1,name.indexOf("."));
	}

	private static HashMap<String, Double> getWeights(File file) throws FileNotFoundException, IOException {
		HashMap<String, Double> weights = new HashMap<>();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		    	if (line.contains("=")){
		    		String[] s = line.split("=");
		    		weights.put(s[0], Double.parseDouble(s[1]));
		    	}
		    }
		}
		return weights;
		
	}
	
	
	/////////////// SAVE ///////////////
	public static void saveUserParams(UserParamsBigTouch upBig, UserParamsMultiTouch upMulti, UserParamsDeviationTouch upDev) throws IOException {
		HashMap<String, HashMap<String, Double>> params = paramsToMap(upBig, upMulti, upDev);
		saveUserParams(params);
		UserParamsHolder.upBig = upBig;
		UserParamsHolder.upMulti = upMulti;
		UserParamsHolder.upDev = upDev;
		
	}
	
	public static void updateUserParamsFromTraining(UserParamsBigTouch upBig, UserParamsMultiTouch upMulti, UserParamsDeviationTouch upDev, double factor) throws FileNotFoundException, IOException{
		HashMap<String, HashMap<String, Double>> currParams = loadUserParams();
		HashMap<String, HashMap<String, Double>> newParams = paramsToMap(upBig, upMulti, upDev);
		
		for (String mapType : currParams.keySet()){
			HashMap<String, Double> currParamsValues = currParams.get(mapType);
			HashMap<String, Double> newParamsValues =  newParams.get(mapType);
				
			for (String key : currParamsValues.keySet()){
				double currValue = currParamsValues.get(key);
				double newValue = newParamsValues.get(key);
				currParamsValues.put(key, newValue*factor + currValue*(1-factor));
			}
		}
		
		saveUserParams(currParams);
		UserParamsHolder.upBig = loadUserParamsBig();
		UserParamsHolder.upMulti = loadUserParamsMulti();
		UserParamsHolder.upDev = loadUserParamsDev();
		
	}
	
	private static void saveUserParams(HashMap<String, HashMap<String, Double>> params) throws IOException {
		for (Entry<String, HashMap<String, Double>> entry : params.entrySet()){
			File fout = new File(USER_PARAMS_DIR,"user_params_"+entry.getKey()+".txt");
			FileOutputStream fos = new FileOutputStream(fout);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			
			bw.write("LastUpdate:"+new Date().toString());
			bw.newLine();
			
			for (Entry<String, Double> wEntry : entry.getValue().entrySet()){
				bw.write(wEntry.getKey()+"="+wEntry.getValue());
				bw.newLine();
			}
		 
			bw.close();
		}
	}
	
	///////////////INIT FILES///////////
	public static void initUserParamsFiles() throws IOException{;
		File userParamsDir = new File(USER_PARAMS_DIR);
		if (!userParamsDir.exists()){
			userParamsDir.mkdir();
			HashMap<String, HashMap<String, Double>> params = new HashMap<String, HashMap<String,Double>>();
			
			HashMap<String, Double> big = new HashMap<String, Double>();
			big.put("wAvg", 1.0);
			big.put("wDown", 0.0);
			big.put("wPressure", 0.0);
			big.put("wRadius", 0.0);
			big.put("wTime", 0.0);
			params.put("big", big);
			
			HashMap<String, Double> multi = new HashMap<String, Double>();
			multi.put("wFirst", 1.0);
			multi.put("wMaxPress", 0.0);
			multi.put("wMaxTime", 0.0);
			multi.put("wStructure", 0.0);
			params.put("multi", multi);
			
			HashMap<String, Double> dev = new HashMap<String, Double>();
			dev.put("xDev", 0.0);
			dev.put("yDev", 0.0);
			dev.put("xWeight", 0.0);
			dev.put("yWeight", 0.0);
			params.put("dev", dev);
			
			saveUserParams(params);
		}
		
	}
	
	
	//////////////////////////
	private static HashMap<String, HashMap<String, Double>> paramsToMap(UserParamsBigTouch upBig, UserParamsMultiTouch upMulti, UserParamsDeviationTouch upDev){
		HashMap<String, HashMap<String, Double>> map = new HashMap<>();
		
		HashMap<String, Double> big = new HashMap<String, Double>();
		big.put("wAvg", upBig.getwAvg());
		big.put("wDown", upBig.getwDown());
		big.put("wPressure", upBig.getwPressure());
		big.put("wRadius",upBig.getwRadius());
		big.put("wTime", upBig.getwTime());
		map.put("big", big);
		
		HashMap<String, Double> multi = new HashMap<String, Double>();
		multi.put("wFirst", upMulti.getwFirst());
		multi.put("wMaxPress", upMulti.getwMaxPressure());
		multi.put("wMaxTime", upMulti.getwMaxTime());
		multi.put("wStructure", upMulti.getwStructure());
		map.put("multi", multi);
		
		HashMap<String, Double> dev = new HashMap<String, Double>();
		dev.put("xDev", upDev.getxDev());
		dev.put("yDev", upDev.getyDev());
		dev.put("xWeight", upDev.getxWeight());
		dev.put("yWeight", upDev.getyWeight());
		map.put("dev", dev);
		
		return map;
	}
	
	private static UserParamsBigTouch mapToParamsBig(HashMap<String, HashMap<String, Double>> map){
		HashMap<String, Double> bigMap = map.get("big");
		UserParamsBigTouch upBig = new UserParamsBigTouch(bigMap.get("wAvg"), bigMap.get("wDown"), bigMap.get("wTime"),
				bigMap.get("wPressure"), bigMap.get("wRadius"), 0, 0, 0, 0);
		return upBig;	
	}
	
	private static UserParamsMultiTouch mapToParamsMulti(HashMap<String, HashMap<String, Double>> map){
		HashMap<String, Double> multiMap = map.get("multi");
		UserParamsMultiTouch upMulti = new UserParamsMultiTouch(multiMap.get("wFirst"), multiMap.get("wMaxPress"), multiMap.get("wMaxTime"), multiMap.get("wStructure"));
		return upMulti;	
	}
	
	private static UserParamsDeviationTouch mapToParamsDev(HashMap<String, HashMap<String, Double>> map){
		HashMap<String, Double> devMap = map.get("dev");
		UserParamsDeviationTouch upDev = new UserParamsDeviationTouch(devMap.get("xDev"), devMap.get("yDev"), devMap.get("xWeight"), devMap.get("yWeight"));
		return upDev;
	}

	
}