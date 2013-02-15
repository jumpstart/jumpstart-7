package jumpstart.web.pages.examples.tables;

import java.util.List;

import javax.ejb.EJB;

import jumpstart.business.domain.person.Person;
import jumpstart.business.domain.person.iface.IPersonFinderServiceLocal;

import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Grid;

public class SortedGrid {
	static private final int MAX_RESULTS = 30;

	// Screen fields

	@Property
	private List<Person> persons;

	// Generally useful bits and pieces

	@EJB
	private IPersonFinderServiceLocal personFinderService;

	@InjectComponent
	private Grid grid;

	// The code
	
	void setupRender() {
		// Get all persons - ask business service to find them (from the database)
		persons = personFinderService.findPersons(MAX_RESULTS);
		 
		if (grid.getSortModel().getSortConstraints().isEmpty()) {
			grid.getSortModel().updateSort("startDate");
		}
	}
}
