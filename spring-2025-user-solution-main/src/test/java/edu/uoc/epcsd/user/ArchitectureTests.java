package edu.uoc.epcsd.user;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.onionArchitecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

public class ArchitectureTests {

  private static final String BASE_PACKAGE = "edu.uoc.epcsd.user";
  private static final String DOMAIN_MODEL_PACKAGE = BASE_PACKAGE + ".domain";
  private static final String DOMAIN_EXCEPTION_PACKAGE = DOMAIN_MODEL_PACKAGE + ".exception..";
  private static final String DOMAIN_REPOSITORY_PACKAGE = DOMAIN_MODEL_PACKAGE + ".repository..";
  private static final String DOMAIN_SERVICE_PACKAGE = DOMAIN_MODEL_PACKAGE + ".service..";
  private static final String APPLICATION_PACKAGE = BASE_PACKAGE + ".application..";
  private static final String INFRASTRUCTURE_PACKAGE = BASE_PACKAGE + ".infrastructure";
  private static final String INFRASTRUCTURE_JPA_PACKAGE = INFRASTRUCTURE_PACKAGE + ".repository.jpa..";
  private static final String INFRASTRUCTURE_REST_PACKAGE = INFRASTRUCTURE_PACKAGE + ".repository.rest..";


  private static JavaClasses importedClasses;

  @BeforeAll
  static void setUp() {
    importedClasses = new ClassFileImporter().withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
        .importPackages(BASE_PACKAGE);
  }

  @Test
  @DisplayName("Hexagonal Architecture is Satisfied")
  void hexagonalArchitectureTest() {
    onionPatternTest();
    outerLayers();
    concreteInfrastructureTest();
  }

  //"Onion Pattern"
  void onionPatternTest() {
    ArchRule onionRule = onionArchitecture().withOptionalLayers(true)
        .domainModels(DOMAIN_MODEL_PACKAGE, DOMAIN_EXCEPTION_PACKAGE)
        .domainServices(DOMAIN_SERVICE_PACKAGE, DOMAIN_REPOSITORY_PACKAGE).applicationServices(APPLICATION_PACKAGE)
        .adapter("persistence", INFRASTRUCTURE_JPA_PACKAGE).adapter("rest", INFRASTRUCTURE_REST_PACKAGE)
        .adapter("infrastructure", INFRASTRUCTURE_PACKAGE);

    onionRule.check(importedClasses);
  }

  //"Domain layer should not depend on application or infrastructure layers"
  void outerLayers() {
    ArchRule outerRule = noClasses().that().resideInAPackage(BASE_PACKAGE + ".domain..").should().dependOnClassesThat()
        .resideInAnyPackage(APPLICATION_PACKAGE, INFRASTRUCTURE_PACKAGE)
        .as("Domain layer should not depend on application or infrastructure layers");
    outerRule.check(importedClasses);
  }

  //"Application layer should not depend on concrete infrastructure"
  void concreteInfrastructureTest() {
    ArchRule applicationRule = noClasses().that().resideInAPackage(APPLICATION_PACKAGE).should().dependOnClassesThat()
        .resideInAnyPackage(INFRASTRUCTURE_JPA_PACKAGE, INFRASTRUCTURE_REST_PACKAGE)
        .as("Application layer should not depend on concrete infrastructure");

    applicationRule.check(importedClasses);
  }

  @Test
  @DisplayName("Naming Convention for Service Implementations")
  void serviceImplementationNamingTest() {
    ArchRule rule = classes().that().resideInAPackage(DOMAIN_SERVICE_PACKAGE).and().areAnnotatedWith(Service.class)
        .should().haveSimpleNameEndingWith("ServiceImpl")
        .as("Classes in 'domain.service' annotated with @Service should end with 'ServiceImpl'");

    rule.check(importedClasses);
  }
}
