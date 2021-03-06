/*
 * Copyright 2000-2013 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.debugger.engine;

import com.intellij.debugger.SourcePosition;
import com.intellij.psi.PsiCodeBlock;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiStatement;
import org.jetbrains.annotations.Nullable;

/**
 * @author Eugene Zhuravlev
 *         Date: 10/26/13
 */
public class AnonymousClassMethodFilter extends BasicStepMethodFilter implements BreakpointStepMethodFilter{
  @Nullable
  private final SourcePosition myBreakpointPosition;

  public AnonymousClassMethodFilter(PsiMethod psiMethod) {
    super(psiMethod);
    myBreakpointPosition = calcBreakpointPosition(psiMethod.getBody());
  }

  @Nullable
  public SourcePosition getBreakpointPosition() {
    return myBreakpointPosition;
  }

  private static SourcePosition calcBreakpointPosition(final PsiCodeBlock body) {
    if (body == null) {
      return null;
    }
    final PsiStatement[] statements = body.getStatements();
    if (statements.length == 0) {
      return null;
    }
    final PsiStatement firstStatement = statements[0];
    return SourcePosition.createFromElement(firstStatement);
  }

}
