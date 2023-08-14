package pl.edziennik.infrastructure.spring.script;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.transform.CompileStatic;
import groovy.transform.TimedInterrupt;
import org.codehaus.groovy.ast.expr.*;
import org.codehaus.groovy.ast.stmt.*;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ASTTransformationCustomizer;
import org.codehaus.groovy.control.customizers.ImportCustomizer;
import org.codehaus.groovy.control.customizers.SecureASTCustomizer;
import org.codehaus.groovy.transform.sc.ListOfExpressionsExpression;
import org.codehaus.groovy.transform.sc.TemporaryVariableExpression;
import org.codehaus.groovy.transform.sc.transformers.CompareIdentityExpression;
import org.codehaus.groovy.transform.sc.transformers.CompareToNullExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edziennik.common.valueobject.ScriptResult;
import pl.edziennik.infrastructure.spring.ResourceCreator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Stream;

@Configuration
public class GroovyScriptExecutorConfiguration {

    @Autowired
    private ResourceCreator resourceCreator;


    // Allowed imports in script
    private static final List<String> ALLOWED_IMPORTS = List.of(
            "java.lang.Math", "java.util.List",
            "java.util.ArrayList", "java.util.LinkedList",
            "java.util.Map", "java.util.HashMap",
            "java.util.HashSet", "java.util.Queue",
            "java.util.PriorityQueue", "java.util.MathContext",
            "java.math.BigInteger", "java.math.BigDecimal",
            "java.text.DecimalFormat", "java.util.stream", "java.util.function",
            "java.util.Arrays", "java.util.Collections", "java.util.Random",
            "java.lang.Object", "pl.edziennik.common.valueobject.ScriptResult");

    // Allowed classes for calling methods on them
    private static final List<Class> RECEIVERS_CLASSES_WHITE_LIST = List.of(
            List.class, ArrayList.class,
            Math.class, LinkedList.class,
            Map.class, HashMap.class,
            HashSet.class, Queue.class,
            PriorityQueue.class, MathContext.class,
            BigInteger.class, BigDecimal.class,
            DecimalFormat.class, Stream.class,
            FunctionalInterface.class, Arrays.class,
            Collections.class, Random.class,
            Object.class, ScriptResult.class
    );

    // Allowed statements in script
    private static final List<Class<? extends Statement>> ALLOWED_STATEMENTS = List.of(
            DoWhileStatement.class, WhileStatement.class,
            ReturnStatement.class, ContinueStatement.class,
            BlockStatement.class, BreakStatement.class,
            CaseStatement.class, SwitchStatement.class,
            ForStatement.class, IfStatement.class,
            Statement.class, ExpressionStatement.class
    );

    // Allowed expressions in script
    private static final List<Class<? extends Expression>> ALLOWED_EXPRESSIONS = List.of(
            ClosureExpression.class, ClosureListExpression.class,
            ArgumentListExpression.class, ArrayExpression.class,
            BinaryExpression.class, ConstantExpression.class,
            UnaryMinusExpression.class, UnaryPlusExpression.class,
            BitwiseNegationExpression.class, PostfixExpression.class,
            VariableExpression.class, PrefixExpression.class,
            DeclarationExpression.class, ConstructorCallExpression.class,
            BooleanExpression.class, ClosureListExpression.class, ClosureExpression.class,
            CompareIdentityExpression.class, CompareToNullExpression.class,
            EmptyExpression.class, MapEntryExpression.class, MapExpression.class,
            FieldExpression.class, GStringExpression.class, ListExpression.class,
            ListOfExpressionsExpression.class, MethodCallExpression.class, MethodPointerExpression.class,
            MethodReferenceExpression.class, TemporaryVariableExpression.class,
            TernaryExpression.class, RangeExpression.class
    );


    // Map for store time to break executing script
    private final Map<String, Object> timeInterruptedParams;

    public GroovyScriptExecutorConfiguration(@Value("${groovy.execution.timeout}") int groovyExecutionTimeout) {
        timeInterruptedParams = Map.of("value", groovyExecutionTimeout);
    }

    @Bean
    public GroovyShellExecutor getGroovyExecutor() {
        ASTTransformationCustomizer compileStaticConfig = new ASTTransformationCustomizer(CompileStatic.class);
        ASTTransformationCustomizer timedInterruptConfig = new ASTTransformationCustomizer(timeInterruptedParams, TimedInterrupt.class);

        SecureASTCustomizer secureCustomizer = new SecureASTCustomizer();
        // Enable indirect import check (For example System.exit)
        secureCustomizer.setIndirectImportCheckEnabled(true);
        // Allows to define methods in script
        secureCustomizer.setMethodDefinitionAllowed(true);
        // Allowed imports
        secureCustomizer.setAllowedStarImports(ALLOWED_IMPORTS);
        secureCustomizer.setAllowedImports(ALLOWED_IMPORTS);
        secureCustomizer.setPackageAllowed(false);
        secureCustomizer.setClosuresAllowed(true);

        // Allowed classes for calling methods on them
        secureCustomizer.setAllowedReceiversClasses(RECEIVERS_CLASSES_WHITE_LIST);

        // Allowed expressions and statements
        secureCustomizer.setAllowedExpressions(ALLOWED_EXPRESSIONS);
        secureCustomizer.setAllowedStatements(ALLOWED_STATEMENTS);

        CompilerConfiguration config = new CompilerConfiguration();
        ImportCustomizer importCustomizer = createImportCustomizer();

        config.addCompilationCustomizers(secureCustomizer);
        config.addCompilationCustomizers(importCustomizer);
        config.addCompilationCustomizers(compileStaticConfig);
        config.addCompilationCustomizers(timedInterruptConfig);

        GroovyShell groovyShell = new GroovyShell(new Binding(), config);

        return new GroovyShellExecutor(groovyShell, resourceCreator);
    }

    private ImportCustomizer createImportCustomizer() {
        ImportCustomizer importCustomizer = new ImportCustomizer();

        importCustomizer.addImports("pl.edziennik.common.valueobject.ScriptResult");
        for (String allowedPackage : ALLOWED_IMPORTS) {
            importCustomizer.addStarImports(allowedPackage);
        }

        return importCustomizer;
    }

}
