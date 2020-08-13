package company;

import java.util.List;

public class CompaniesService {
	public List<Company> getCompanies(){
		return CompaniesRepository.getCompanies();
	}

	public boolean deleteCompany(String name) {
		return CompaniesRepository.deleteCompany(name);
		
	}

	public boolean editCompany(String oldName, String newname) {
		return CompaniesRepository.editCompany(oldName,newname);
	}

	public boolean addCompany(String name) {
		return CompaniesRepository.addCompany(name);
	}
}
