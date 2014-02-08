package jumpstart.web.components.together.smallercomponentscrud;

import javax.ejb.EJB;

import jumpstart.business.domain.person.Person;
import jumpstart.business.domain.person.iface.IPersonFinderServiceLocal;
import jumpstart.business.domain.person.iface.IPersonManagerServiceLocal;
import jumpstart.util.ExceptionUtil;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Events;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * This component will trigger the following events on its container (which in this example is the page):
 * {@link jumpstart.web.components.examples.component.PersonEditorForm.PersonEditor#CANCEL_CREATE},
 * {@link jumpstart.web.components.examples.component.PersonEditorForm.PersonEditor#SUCCESSFUL_CREATE}(Long personId),
 */
// @Events is applied to a component solely to document what events it may trigger. It is not checked at runtime.
@Events({ PersonCreate.CANCEL_CREATE, PersonCreate.SUCCESSFUL_CREATE })
@Import(stylesheet = "css/together/filtercrud.css")
public class PersonCreate {
	public static final String CANCEL_CREATE = "cancelCreate";
	public static final String SUCCESSFUL_CREATE = "successfulCreate";

	private final String demoModeStr = System.getProperty("jumpstart.demo-mode");

	// Parameters

	// Screen fields

	@Property
	private Person person;

	// Generally useful bits and pieces

	@EJB
	private IPersonFinderServiceLocal personFinderService;

	@EJB
	private IPersonManagerServiceLocal personManagerService;

	@Component
	private Form form;

	@Inject
	private ComponentResources componentResources;

	@Inject
	private Messages messages;

	// The code

	boolean onCancel() {
		// We want to tell our containing page explicitly what person we've created, so we trigger new event
		// "successfulCreate" with a parameter. It will bubble up because we don't have a handler method for it.
		componentResources.triggerEvent(CANCEL_CREATE, new Object[] {}, null);
		// We don't want "success" to bubble up, so we return true to say we've handled it.
		return true;
	}

	void onPrepareForRenderFromForm() throws Exception {

		// If fresh start, make sure there's a Person object available.

		if (form.isValid()) {
			person = new Person();
		}
	}

	void onPrepareForSubmitFromForm() throws Exception {
		// Instantiate a Person for the form data to overlay.
		person = new Person();
	}

	void onValidateFromForm() {

		if (demoModeStr != null && demoModeStr.equals("true")) {
			form.recordError("Sorry, but Create is not allowed in Demo mode.");
		}

		if (form.getHasErrors()) {
			return;
		}

		try {
			person = personManagerService.createPerson(person);
		}
		catch (Exception e) {
			// Display the cause. In a real system we would try harder to get a user-friendly message.
			form.recordError(ExceptionUtil.getRootCauseMessage(e));
		}
	}

	boolean onSuccessFromForm() {
		// We want to tell our containing page explicitly what person we've created, so we trigger new event
		// "successfulCreate" with a parameter. It will bubble up because we don't have a handler method for it.
		componentResources.triggerEvent(SUCCESSFUL_CREATE, new Object[] { person.getId() }, null);
		// We don't want "success" to bubble up, so we return true to say we've handled it.
		return true;
	}

}