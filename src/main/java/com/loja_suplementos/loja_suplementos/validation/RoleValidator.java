package com.loja_suplementos.loja_suplementos.validation;

import com.loja_suplementos.loja_suplementos.usuario.Role;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class RoleValidator implements ConstraintValidator<ValidRole, Role> {
    @Override
    public boolean isValid(Role role, ConstraintValidatorContext context){
        return role != null && Arrays.asList(Role.values()).contains(role);
    }
}
