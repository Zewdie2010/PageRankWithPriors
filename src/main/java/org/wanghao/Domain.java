package org.wanghao;

import java.util.HashSet;

/**
 * 
 * @author wanghao
 * 
 * @ date 3.11 @ date 3.12
 * 
 * The domain class
 */
public class Domain {

	String filePath;
	HashSet<String> userSet;

	public Domain(String filePath) {

		this.filePath = filePath;
		userSet = new HashSet<String>();
	}

	// DM domain
	public static boolean IsDMConfOrJour(String line) {

		// Conf
		if (line.contains("conf/kdd/") || line.contains("conf/icdm/") || line.contains("conf/sdm/"))
			return true;

		// Journal
		if (line.contains("journals/datamine/") || line.contains("journals／tkde/"))
			return true;

		return false;
	}

	// DB domain
	public static boolean IsDBConfOrJour(String line) {

		if (line.contains("conf/sigmod/") || line.contains("conf/vldb/") || line.contains("conf/icde"))
			return true;

		if (line.contains("journals/vldb/") || line.contains("journals/tods"))
			return true;

		return false;
	}

	// ML domain
	public static boolean IsMLConfOrJour(String line) {

		if (line.contains("conf/icml") || line.contains("conf/nips") || line.contains("conf/uai"))
			return true;

		if (line.contains("journals/ml") || line.contains("journals/jmlr"))
			return true;

		return false;
	}

	// AI domain
	public static boolean IsAIConfOrJour(String line) {

		if (line.contains("conf/aaai") || line.contains("ijcai"))
			return true;

		if (line.contains("journals/ai/") || line.contains("journals/jair/"))
			return true;

		return false;
	}

	// CV domain
	public static boolean IsVisionConfOrJour(String line) {

		if (line.contains("conf/cvpr/") || line.contains("conf/iccv"))
			return true;

		if (line.contains("journals/pami") || line.contains("journals/ijcv"))
			return true;

		return false;
	}

	// IR domain
	public static boolean IsIRConfOrJour(String line) {

		if (line.contains("conf/sigir/") || line.contains("conf/wsdm/") || line.contains("conf/www/"))
			return true;

		if (line.contains("journals/tois"))
			return true;

		return false;
	}

	// judge the aimed the conf or jour
	public static boolean IsAimedConfOrJour(String line) {

		if (IsAIConfOrJour(line) || IsDBConfOrJour(line) || IsDMConfOrJour(line) || IsIRConfOrJour(line)
				|| IsMLConfOrJour(line) || IsVisionConfOrJour(line))
			return true;

		return false;
	}

	// get the domain of the conf or journal
	public static String GetDomainOfConfOrJour(String conf) {

		if (IsAIConfOrJour(conf))
			return "AI";
		if (IsDBConfOrJour(conf))
			return "DB";
		if (IsDMConfOrJour(conf))
			return "DM";
		if (IsIRConfOrJour(conf))
			return "IR";
		if (IsMLConfOrJour(conf))
			return "ML";
		if (IsVisionConfOrJour(conf))
			return "CV";

		return "Other";
	}

	public static String GetTypeOfConfOrJour(boolean IsAimedConfOrJour, String typeLine) {

		if (IsAimedConfOrJour) {
			if (typeLine.startsWith("<inproceedings")) {
				return "conf/";
			}
			if (typeLine.startsWith("<article")) {
				return "journals/";
			}
		}

		return "";

	}

	public static void main(String[] args) {
		System.out.println("This is the Domain");
	}
}
