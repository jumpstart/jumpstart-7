package jumpstart.web.pages.examples.input;

import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;

@Import(stylesheet="css/examples/plain.css")
public class PrimitivesTranslators {

	// Screen fields

	@Property
	@Persist(PersistenceConstants.FLASH)
	private byte byteValue;

	@Property
	@Persist(PersistenceConstants.FLASH)
	private short shortValue;

	@Property
	@Persist(PersistenceConstants.FLASH)
	private int integerValue;

	@Property
	@Persist(PersistenceConstants.FLASH)
	private long longValue;

	@Property
	@Persist(PersistenceConstants.FLASH)
	private float floatValue;

	@Property
	@Persist(PersistenceConstants.FLASH)
	private double doubleValue;

}
