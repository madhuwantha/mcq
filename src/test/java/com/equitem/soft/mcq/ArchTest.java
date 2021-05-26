package com.equitem.soft.mcq;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.equitem.soft.mcq");

        noClasses()
            .that()
            .resideInAnyPackage("com.equitem.soft.mcq.service..")
            .or()
            .resideInAnyPackage("com.equitem.soft.mcq.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.equitem.soft.mcq.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
