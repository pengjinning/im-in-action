// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: person_protos.proto

package org.utkuozdemir.sample1.protobuf;

/**
 * Protobuf enum {@code org.utkuozdemir.sample1.protobuf.Gender}
 */
public enum Gender
    implements com.google.protobuf.ProtocolMessageEnum {
  /**
   * <code>MALE = 1;</code>
   */
  MALE(0, 1),
  /**
   * <code>FEMALE = 2;</code>
   */
  FEMALE(1, 2),
  ;

  /**
   * <code>MALE = 1;</code>
   */
  public static final int MALE_VALUE = 1;
  /**
   * <code>FEMALE = 2;</code>
   */
  public static final int FEMALE_VALUE = 2;


  public final int getNumber() { return value; }

  public static Gender valueOf(int value) {
    switch (value) {
      case 1: return MALE;
      case 2: return FEMALE;
      default: return null;
    }
  }

  public static com.google.protobuf.Internal.EnumLiteMap<Gender>
      internalGetValueMap() {
    return internalValueMap;
  }
  private static com.google.protobuf.Internal.EnumLiteMap<Gender>
      internalValueMap =
        new com.google.protobuf.Internal.EnumLiteMap<Gender>() {
          public Gender findValueByNumber(int number) {
            return Gender.valueOf(number);
          }
        };

  public final com.google.protobuf.Descriptors.EnumValueDescriptor
      getValueDescriptor() {
    return getDescriptor().getValues().get(index);
  }
  public final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptorForType() {
    return getDescriptor();
  }
  public static final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptor() {
    return org.utkuozdemir.sample1.protobuf.PersonProtos.getDescriptor()
        .getEnumTypes().get(0);
  }

  private static final Gender[] VALUES = values();

  public static Gender valueOf(
      com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
    if (desc.getType() != getDescriptor()) {
      throw new java.lang.IllegalArgumentException(
        "EnumValueDescriptor is not for this type.");
    }
    return VALUES[desc.getIndex()];
  }

  private final int index;
  private final int value;

  private Gender(int index, int value) {
    this.index = index;
    this.value = value;
  }

  // @@protoc_insertion_point(enum_scope:org.utkuozdemir.sample1.protobuf.Gender)
}

