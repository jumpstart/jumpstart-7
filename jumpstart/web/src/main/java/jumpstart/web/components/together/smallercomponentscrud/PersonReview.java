package jumpstart.web.components.together.smallercomponentscrud;

import javax.ejb.EJB;

import jumpstart.business.domain.person.Person;
import jumpstart.business.domain.person.iface.IPersonFinderServiceLocal;
import jumpstart.business.domain.person.iface.IPersonManagerServiceLocal;
import jumpstart.util.ExceptionUtil;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Events;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * This component will trigger the following events on its container (which in
 * this example is the page): {@link PersonReview#TO_UPDATE}(Long personId),
 * {@link PersonReview#DELETED}(Long personId).
 */
// @Events is applied to a component solely to document what events it may
// trigger. It is not checked at runtime.
@Events({ PersonReview.TO_UPDATE, PersonReview.DELETED })
public class PersonReview {
	public static final String TO_UPDATE = "toUpdate";
	public static final String DELETED = "deleted";

	private final String demoModeStr = System
			.getProperty("jumpstart.demo-mode");

	// Parameters

	@Parameter(required = true)
	@Property
	private Long personId;

	// Screen fields

	@Property
	private Person person;

	@Property
	@Persist(PersistenceConstants.FLASH)
	private String deleteMessage;

	// Generally useful bits and pieces

	@EJB
	private IPersonFinderServiceLocal personFinderService;

	@EJB
	private IPersonManagerServiceLocal personManagerService;

	@Inject
	private ComponentResources componentResources;

	// The code

	void setupRender() {

		if (personId == null) {
			person = null;
			// Handle null person in the template.
		} else {
			if (person == null) {
				person = personFinderService.findPerson(personId);
				// Handle null person in the template.
			}
		}

	}

	boolean onToUpdate(Long personId) {
		// Return false, which means we haven't handled the event so bubble it
		// up.
		// This method is here solely as documentation, because without this
		// method the event would bubble up anyway.
		return false;
	}

	boolean onDelete(Long personId, Integer personVersion) {
		this.personId = personId;

		if (demoModeStr != null && demoModeStr.equals("true")) {
			deleteMessage = "Sorry, but Delete is not allowed in Demo mode.";

			// We don't want the event to bubble up, so we return true to say
			// we've handled it.
			return true;
		}

		try {
			personManagerService.deletePerson(personId, personVersion);
		} catch (Exception e) {
			// Display the cause. In a real system we would try harder to get a
			// user-friendly message.
			deleteMessage = ExceptionUtil.getRootCauseMessage(e);

			// We don't want the event to bubble up, so we return true to say
			// we've handled it.
			return true;
		}

		// Trigger new event which will bubble up.
		componentResources.triggerEvent(DELETED, new Object[] { personId },
				null);

		// We don't want the original event to bubble up, so we return true to
		// say we've handled it.
		return true;
	}

}
