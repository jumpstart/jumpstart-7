package jumpstart.web.pages.examples.tables;

import java.util.List;

import jumpstart.business.domain.person.Person;

import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;

@Import(stylesheet = "css/examples/plain.css")
public class EditableGridForUpdate2 {

	// Screen fields

	@Property
	@Persist(PersistenceConstants.FLASH)
	private List<Person> persons;

	// The code

	public void set(List<Person> persons) {
		this.persons = persons;
	}

}
