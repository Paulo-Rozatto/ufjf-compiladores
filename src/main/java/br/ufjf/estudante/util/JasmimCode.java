/*
  André Luiz Cunha de Oliveira  - 201935020
  Paulo Victor de M. Rozatto  - 201935027
*/

package br.ufjf.estudante.util;

public class JasmimCode {

  public static final String customType =
      """
.class public %s
.super java/lang/Object

; fields
%s
""";

  //  public int foo -> .field public foo I
  public static final String field = ".field public %s %s";

  public static final String function =
      """

.method public static %s()%s
.end method
""";

  public static final String default_init =
      """
.method public <init>()V
   aload_0
   invokenonvirtual java/lang/Object/<init>()V
   return
.end method
""";

  public static final String mainFunction =
      """
.method public static main([Ljava/lang/String;)V
    .limit stack 1
    .limit locals 1
    invokestatic Main/main()V
    return
.end method
""";

  private JasmimCode() {}
}
