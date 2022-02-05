package br.com.company.sales.validation;

import br.com.company.sales.validation.validator.NotEmptyListValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = NotEmptyListValidator.class)
public @interface NotEmptyList {
    //Mensagem padrão. Entretando podemos alterar quando anotamos essa validation.
    String message() default "A lista não pode ser vazia";
    //Essas anotações são obrigatórias por padrão para o funcionamento da validation.
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
