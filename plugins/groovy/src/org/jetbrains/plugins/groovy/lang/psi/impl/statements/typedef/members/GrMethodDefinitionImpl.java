/*
 *  Copyright 2000-2007 JetBrains s.r.o.
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.jetbrains.plugins.groovy.lang.psi.impl.statements.typedef.members;

import com.intellij.lang.ASTNode;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.*;
import com.intellij.psi.javadoc.PsiDocComment;
import com.intellij.psi.util.MethodSignature;
import com.intellij.psi.util.MethodSignatureBackedByPsiMethod;
import com.intellij.util.IncorrectOperationException;
import com.intellij.pom.java.PomMethod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.blocks.GrOpenBlock;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.params.GrParameter;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.typedef.members.GrMethod;
import org.jetbrains.plugins.groovy.lang.psi.api.types.GrTypeElement;
import org.jetbrains.plugins.groovy.lang.psi.impl.GroovyPsiElementImpl;
import org.jetbrains.plugins.groovy.lang.psi.impl.auxiliary.modifiers.GrModifiersImpl;
import org.jetbrains.plugins.groovy.lang.psi.impl.statements.params.GrParameterListImpl;
import org.jetbrains.plugins.groovy.lang.lexer.GroovyTokenTypes;
import org.jetbrains.plugins.groovy.lang.resolve.ResolveUtil;

import java.util.List;

/**
 * @author: Dmitry.Krasilschikov
 * @date: 26.03.2007
 */

public class GrMethodDefinitionImpl extends GroovyPsiElementImpl implements GrMethod {
  public GrMethodDefinitionImpl(@NotNull ASTNode node) {
    super(node);
  }

  public String toString() {
    return "Method";
  }

  public GrOpenBlock getBlock() {
    return this.findChildByClass(GrOpenBlock.class);
  }

  public GrParameter[] getParameters() {
    GrParameterListImpl parameterList = findChildByClass(GrParameterListImpl.class);
    if (parameterList != null) {
      return parameterList.getParameters();
    }

    return GrParameter.EMPTY_ARRAY;
  }

  public GrTypeElement getReturnTypeElementGroovy() {
    return findChildByClass(GrTypeElement.class);
  }

  public boolean processDeclarations(@NotNull PsiScopeProcessor processor, @NotNull PsiSubstitutor substitutor, PsiElement lastParent, @NotNull PsiElement place) {
    if (!ResolveUtil.processElement(processor, this)) return false;

    for (final GrParameter parameter : getParameters()) {
      if (!ResolveUtil.processElement(processor, parameter)) return false;
    }

    return true;
  }

  //PsiMethod implementation
  @Nullable
  public PsiType getReturnType() {
    GrTypeElement element = getReturnTypeElementGroovy();
    if (element == null) return null;
    return element.getType();
  }

  @Nullable
  public PsiTypeElement getReturnTypeElement() {
    return null;
  }

  @NotNull
  public PsiParameterList getParameterList() {
    return null;
  }

  @NotNull
  public PsiReferenceList getThrowsList() {
    return null;
  }

  @Nullable
  public PsiCodeBlock getBody() {
    return null;
  }

  public boolean isConstructor() {
    return false;
  }

  public boolean isVarArgs() {
    return false;
  }

  @NotNull
  public MethodSignature getSignature(@NotNull PsiSubstitutor substitutor) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Nullable
  public PsiIdentifier getNameIdentifier() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @NotNull
  public PsiMethod[] findSuperMethods() {
    return new PsiMethod[0];  //To change body of implemented methods use File | Settings | File Templates.
  }

  @NotNull
  public PsiMethod[] findSuperMethods(boolean checkAccess) {
    return new PsiMethod[0];  //To change body of implemented methods use File | Settings | File Templates.
  }

  @NotNull
  public PsiMethod[] findSuperMethods(PsiClass parentClass) {
    return new PsiMethod[0];  //To change body of implemented methods use File | Settings | File Templates.
  }

  @NotNull
  public List<MethodSignatureBackedByPsiMethod> findSuperMethodSignaturesIncludingStatic(boolean checkAccess) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Nullable
  public PsiMethod findDeepestSuperMethod() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @NotNull
  public PsiMethod[] findDeepestSuperMethods() {
    return new PsiMethod[0];  //To change body of implemented methods use File | Settings | File Templates.
  }

  public PomMethod getPom() {
    return null;
  }

  @NotNull
  public PsiModifierList getModifierList() {
    return findChildByClass(GrModifiersImpl.class);
  }

  public boolean hasModifierProperty(@NonNls @NotNull String name) {
    PsiModifierList modifierList = getModifierList();
    return modifierList != null && modifierList.hasModifierProperty(name);
  }

  @NotNull
  public String getName() {
    PsiElement nameElement = findChildByType(GroovyTokenTypes.mIDENT);
    assert nameElement != null;
    return nameElement.getText();
  }

  @NotNull
  public HierarchicalMethodSignature getHierarchicalMethodSignature() {
    return null;
  }

  public PsiElement setName(@NonNls @NotNull String name) throws IncorrectOperationException {
    throw new IncorrectOperationException("NIY");
  }

  public boolean hasTypeParameters() {
    return false;
  }

  @Nullable
  public PsiTypeParameterList getTypeParameterList() {
    return null;
  }

  @NotNull
  public PsiTypeParameter[] getTypeParameters() {
    return PsiTypeParameter.EMPTY_ARRAY;
  }

  public PsiClass getContainingClass() {
    PsiElement parent = getParent();
    if (parent instanceof PsiClass) return (PsiClass) parent;
    return null;
  }

  @Nullable
  public PsiDocComment getDocComment() {
    return null;
  }

  public boolean isDeprecated() {
    return false;
  }
}