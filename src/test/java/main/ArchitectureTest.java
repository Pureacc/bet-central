package main;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import org.junit.Before;
import org.junit.Test;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;

public class ArchitectureTest {
	private static final String ROOT = "org.pureacc.betcentral";
	private static final String MAIN = "org.pureacc.betcentral.main..";
	private static final String VOCABULARY = "org.pureacc.betcentral.vocabulary..";
	private static final String DOMAIN = "org.pureacc.betcentral.domain..";

	private static final String APPLICATION_API = "org.pureacc.betcentral.application.api..";
	private static final String APPLICATION_COMMAND = "org.pureacc.betcentral.application.command..";
	private static final String APPLICATION_QUERY = "org.pureacc.betcentral.application.query..";

	private static final String INFRA_REST = "org.pureacc.betcentral.infra.rest..";
	private static final String INFRA_JPA = "org.pureacc.betcentral.infra.jpa..";
	private static final String INFRA_TIME = "org.pureacc.betcentral.infra.time..";
	private static final String INFRA_EVENTS = "org.pureacc.betcentral.infra.events..";
	private static final String INFRA_SECURITY = "org.pureacc.betcentral.infra.security..";
	private static final String INFRA_EXCEPTION_HANDLING = "org.pureacc.betcentral.infra.exceptionhandling";
	private static final String[] CALLING_INFRA = new String[] { INFRA_REST };
	private static final String[] IMPLEMENTING_INFRA = new String[] { INFRA_JPA,
																	  INFRA_EVENTS,
																	  INFRA_TIME,
																	  INFRA_SECURITY,
																	  INFRA_EXCEPTION_HANDLING };

	private static final String WHITELIST = "java..";

	private JavaClasses classes;

	@Before
	public void setup() {
		classes = new ClassFileImporter().withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
				.importPackages(ROOT);
	}

	@Test
	public void properPackageStructure() {
		classes().should()
				.resideInAnyPackage(MAIN, VOCABULARY, DOMAIN, APPLICATION_API, APPLICATION_COMMAND, APPLICATION_QUERY)
				.orShould()
				.resideInAnyPackage(CALLING_INFRA)
				.orShould()
				.resideInAnyPackage(IMPLEMENTING_INFRA)
				.check(classes);
	}

	@Test
	public void vocabulary() {
		classes().that()
				.resideInAPackage(VOCABULARY)
				.should()
				.onlyAccessClassesThat()
				.resideInAnyPackage(VOCABULARY, WHITELIST)
				.check(classes);
	}

	@Test
	public void domain() {
		classes().that()
				.resideInAPackage(DOMAIN)
				.should()
				.onlyAccessClassesThat()
				.resideInAnyPackage(DOMAIN, VOCABULARY, WHITELIST)
				.check(classes);
	}

	@Test
	public void applicationApi() {
		classes().that()
				.resideInAPackage(APPLICATION_API)
				.should()
				.onlyAccessClassesThat()
				.resideInAnyPackage(APPLICATION_API, VOCABULARY, WHITELIST)
				.check(classes);
	}

	@Test
	public void applicationCommand() {
		classes().that()
				.resideInAPackage(APPLICATION_COMMAND)
				.should()
				.onlyAccessClassesThat()
				.resideInAnyPackage(APPLICATION_COMMAND, APPLICATION_API, DOMAIN, VOCABULARY, WHITELIST)
				.check(classes);
	}

	@Test
	public void applicationQuery() {
		classes().that()
				.resideInAPackage(APPLICATION_QUERY)
				.should()
				.onlyAccessClassesThat()
				.resideInAnyPackage(APPLICATION_QUERY, APPLICATION_API, DOMAIN, VOCABULARY, WHITELIST)
				.check(classes);
	}

	@Test
	public void callingInfra() {
		classes().that()
				.resideInAnyPackage(CALLING_INFRA)
				.should()
				.onlyAccessClassesThat()
				.resideOutsideOfPackages(APPLICATION_QUERY, APPLICATION_COMMAND, DOMAIN)
				.check(classes);
	}

	@Test
	public void implementingInfra() {
		classes().that()
				.resideInAnyPackage(IMPLEMENTING_INFRA)
				.should()
				.onlyAccessClassesThat()
				.resideOutsideOfPackages(APPLICATION_QUERY, APPLICATION_COMMAND)
				.check(classes);
	}
}
