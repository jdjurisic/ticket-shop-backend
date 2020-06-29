package company;

import java.util.List;

public class CompaniesService {
	public List<Company> getCompanies(){
		return CompaniesRepository.getCompanies();
	}
}
