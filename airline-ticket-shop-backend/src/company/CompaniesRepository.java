package company;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

import user.UserEntity;

public class CompaniesRepository {
	public static synchronized List<Company> getCompanies(){
		
		List<Company> cmp = null;
		Gson gson = new Gson();
		
		try {
			Reader reader = new FileReader("companies.json");
			cmp = Arrays.asList(gson.fromJson(reader, Company[].class));
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
System.out.println(cmp.toString());
		return cmp;
		
	}
}
