package jumpstart.web.pages.together.smallercomponentscrud;

import jumpstart.business.domain.person.Person;

import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.annotations.ActivationRequestParameter;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Property;

@Import(stylesheet = "css/together/filtercrud.css")
public class Persons {

	public enum Mode {
		CREATE, REVIEW, UPDATE;
	}

	// The activation context

	@Property
	private Mode editorMode;

	@Property
	private Long editorPersonId;

	// Screen fields

	@Property
	@ActivationRequestParameter
	private String partialName;

	@Property
	private Long listPersonId;

	// The code

	void onActivate(EventContext ec) {

		if (ec.getCount() == 0) {
			editorMode = null;
			editorPersonId = null;
		}
		else if (ec.getCount() == 1) {
			editorMode = ec.get(Mode.class, 0);
			editorPersonId = null;
		}
		else {
			editorMode = ec.get(Mode.class, 0);
			editorPersonId = ec.get(Long.class, 1);
		}

	}

	Object[] onPassivate() {

		if (editorMode == null) {
			return null;
		}
		else if (editorMode == Mode.CREATE) {
			return new Object[] { editorMode };
		}
		else if (editorMode == Mode.REVIEW || editorMode == Mode.UPDATE) {
			return new Object[] { editorMode, editorPersonId };
		}
		else {
			throw new IllegalStateException(editorMode.toString());
		}

	}

	void setupRender() {
		listPersonId = editorPersonId;
	}

	void onToCreate() {
		editorMode = Mode.CREATE;
		editorPersonId = null;
	}

	void onPersonSelectedFromList(Long personId) {
		editorMode = Mode.REVIEW;
		editorPersonId = personId;
	}

	// /////////////////////////////////////////////////////////////////////
	// CREATE
	// /////////////////////////////////////////////////////////////////////

	void onCanceledFromPersonCreate() {
		editorMode = null;
		editorPersonId = null;
	}

	void onCreatedFromPersonCreate(Person person) {
		editorMode = Mode.REVIEW;
		editorPersonId = person.getId();
	}

	// /////////////////////////////////////////////////////////////////////
	// REVIEW
	// /////////////////////////////////////////////////////////////////////

	void onToUpdateFromPersonReview(Long personId) {
		editorMode = Mode.UPDATE;
		editorPersonId = personId;
	}

	void onDeletedFromPersonReview(Long personId) {
		editorMode = null;
		editorPersonId = null;
	}

	// /////////////////////////////////////////////////////////////////////
	// UPDATE
	// /////////////////////////////////////////////////////////////////////

	void onCanceledFromPersonUpdate(Long personId) {
		editorMode = Mode.REVIEW;
		editorPersonId = personId;
	}

	void onUpdatedFromPersonUpdate(Person person) {
		editorMode = Mode.REVIEW;
		editorPersonId = person.getId();
	}

	// /////////////////////////////////////////////////////////////////////
	// GETTERS ETC.
	// /////////////////////////////////////////////////////////////////////

	public boolean isEditorMode(Mode mode) {
		return editorMode == mode;
	}
}
