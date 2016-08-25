// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: systemMessage.proto

package com.netty.server.tcp.message;

public final class SystemMessage {
  private SystemMessage() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public interface AlertMessageOrBuilder
      extends com.google.protobuf.MessageOrBuilder {
    
    // required int32 code = 1;
    boolean hasCode();
    int getCode();
    
    // optional string remark = 2;
    boolean hasRemark();
    String getRemark();
  }
  public static final class AlertMessage extends
      com.google.protobuf.GeneratedMessage
      implements AlertMessageOrBuilder {
    // Use AlertMessage.newBuilder() to construct.
    private AlertMessage(Builder builder) {
      super(builder);
    }
    private AlertMessage(boolean noInit) {}
    
    private static final AlertMessage defaultInstance;
    public static AlertMessage getDefaultInstance() {
      return defaultInstance;
    }
    
    public AlertMessage getDefaultInstanceForType() {
      return defaultInstance;
    }
    
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.netty.server.tcp.message.SystemMessage.internal_static_AlertMessage_descriptor;
    }
    
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.netty.server.tcp.message.SystemMessage.internal_static_AlertMessage_fieldAccessorTable;
    }
    
    private int bitField0_;
    // required int32 code = 1;
    public static final int CODE_FIELD_NUMBER = 1;
    private int code_;
    public boolean hasCode() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    public int getCode() {
      return code_;
    }
    
    // optional string remark = 2;
    public static final int REMARK_FIELD_NUMBER = 2;
    private java.lang.Object remark_;
    public boolean hasRemark() {
      return ((bitField0_ & 0x00000002) == 0x00000002);
    }
    public String getRemark() {
      java.lang.Object ref = remark_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        if (com.google.protobuf.Internal.isValidUtf8(bs)) {
          remark_ = s;
        }
        return s;
      }
    }
    private com.google.protobuf.ByteString getRemarkBytes() {
      java.lang.Object ref = remark_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8((String) ref);
        remark_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    
    private void initFields() {
      code_ = 0;
      remark_ = "";
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized != -1) return isInitialized == 1;
      
      if (!hasCode()) {
        memoizedIsInitialized = 0;
        return false;
      }
      memoizedIsInitialized = 1;
      return true;
    }
    
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeInt32(1, code_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        output.writeBytes(2, getRemarkBytes());
      }
      getUnknownFields().writeTo(output);
    }
    
    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;
    
      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(1, code_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(2, getRemarkBytes());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }
    
    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }
    
    public static com.netty.server.tcp.message.SystemMessage.AlertMessage parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static com.netty.server.tcp.message.SystemMessage.AlertMessage parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static com.netty.server.tcp.message.SystemMessage.AlertMessage parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static com.netty.server.tcp.message.SystemMessage.AlertMessage parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static com.netty.server.tcp.message.SystemMessage.AlertMessage parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static com.netty.server.tcp.message.SystemMessage.AlertMessage parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    public static com.netty.server.tcp.message.SystemMessage.AlertMessage parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }
    public static com.netty.server.tcp.message.SystemMessage.AlertMessage parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }
    public static com.netty.server.tcp.message.SystemMessage.AlertMessage parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static com.netty.server.tcp.message.SystemMessage.AlertMessage parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    
    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(com.netty.server.tcp.message.SystemMessage.AlertMessage prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }
    
    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder>
       implements com.netty.server.tcp.message.SystemMessage.AlertMessageOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.netty.server.tcp.message.SystemMessage.internal_static_AlertMessage_descriptor;
      }
      
      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.netty.server.tcp.message.SystemMessage.internal_static_AlertMessage_fieldAccessorTable;
      }
      
      // Construct using com.netty.server.tcp.message.SystemMessage.AlertMessage.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }
      
      private Builder(BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      private static Builder create() {
        return new Builder();
      }
      
      public Builder clear() {
        super.clear();
        code_ = 0;
        bitField0_ = (bitField0_ & ~0x00000001);
        remark_ = "";
        bitField0_ = (bitField0_ & ~0x00000002);
        return this;
      }
      
      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }
      
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.netty.server.tcp.message.SystemMessage.AlertMessage.getDescriptor();
      }
      
      public com.netty.server.tcp.message.SystemMessage.AlertMessage getDefaultInstanceForType() {
        return com.netty.server.tcp.message.SystemMessage.AlertMessage.getDefaultInstance();
      }
      
      public com.netty.server.tcp.message.SystemMessage.AlertMessage build() {
        com.netty.server.tcp.message.SystemMessage.AlertMessage result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }
      
      private com.netty.server.tcp.message.SystemMessage.AlertMessage buildParsed()
          throws com.google.protobuf.InvalidProtocolBufferException {
        com.netty.server.tcp.message.SystemMessage.AlertMessage result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(
            result).asInvalidProtocolBufferException();
        }
        return result;
      }
      
      public com.netty.server.tcp.message.SystemMessage.AlertMessage buildPartial() {
        com.netty.server.tcp.message.SystemMessage.AlertMessage result = new com.netty.server.tcp.message.SystemMessage.AlertMessage(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.code_ = code_;
        if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
          to_bitField0_ |= 0x00000002;
        }
        result.remark_ = remark_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }
      
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.netty.server.tcp.message.SystemMessage.AlertMessage) {
          return mergeFrom((com.netty.server.tcp.message.SystemMessage.AlertMessage)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }
      
      public Builder mergeFrom(com.netty.server.tcp.message.SystemMessage.AlertMessage other) {
        if (other == com.netty.server.tcp.message.SystemMessage.AlertMessage.getDefaultInstance()) return this;
        if (other.hasCode()) {
          setCode(other.getCode());
        }
        if (other.hasRemark()) {
          setRemark(other.getRemark());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }
      
      public final boolean isInitialized() {
        if (!hasCode()) {
          
          return false;
        }
        return true;
      }
      
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder(
            this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              this.setUnknownFields(unknownFields.build());
              onChanged();
              return this;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                onChanged();
                return this;
              }
              break;
            }
            case 8: {
              bitField0_ |= 0x00000001;
              code_ = input.readInt32();
              break;
            }
            case 18: {
              bitField0_ |= 0x00000002;
              remark_ = input.readBytes();
              break;
            }
          }
        }
      }
      
      private int bitField0_;
      
      // required int32 code = 1;
      private int code_ ;
      public boolean hasCode() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      public int getCode() {
        return code_;
      }
      public Builder setCode(int value) {
        bitField0_ |= 0x00000001;
        code_ = value;
        onChanged();
        return this;
      }
      public Builder clearCode() {
        bitField0_ = (bitField0_ & ~0x00000001);
        code_ = 0;
        onChanged();
        return this;
      }
      
      // optional string remark = 2;
      private java.lang.Object remark_ = "";
      public boolean hasRemark() {
        return ((bitField0_ & 0x00000002) == 0x00000002);
      }
      public String getRemark() {
        java.lang.Object ref = remark_;
        if (!(ref instanceof String)) {
          String s = ((com.google.protobuf.ByteString) ref).toStringUtf8();
          remark_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      public Builder setRemark(String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000002;
        remark_ = value;
        onChanged();
        return this;
      }
      public Builder clearRemark() {
        bitField0_ = (bitField0_ & ~0x00000002);
        remark_ = getDefaultInstance().getRemark();
        onChanged();
        return this;
      }
      void setRemark(com.google.protobuf.ByteString value) {
        bitField0_ |= 0x00000002;
        remark_ = value;
        onChanged();
      }
      
      // @@protoc_insertion_point(builder_scope:AlertMessage)
    }
    
    static {
      defaultInstance = new AlertMessage(true);
      defaultInstance.initFields();
    }
    
    // @@protoc_insertion_point(class_scope:AlertMessage)
  }
  
  public interface PingMessageOrBuilder
      extends com.google.protobuf.MessageOrBuilder {
    
    // optional string remark = 1;
    boolean hasRemark();
    String getRemark();
  }
  public static final class PingMessage extends
      com.google.protobuf.GeneratedMessage
      implements PingMessageOrBuilder {
    // Use PingMessage.newBuilder() to construct.
    private PingMessage(Builder builder) {
      super(builder);
    }
    private PingMessage(boolean noInit) {}
    
    private static final PingMessage defaultInstance;
    public static PingMessage getDefaultInstance() {
      return defaultInstance;
    }
    
    public PingMessage getDefaultInstanceForType() {
      return defaultInstance;
    }
    
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.netty.server.tcp.message.SystemMessage.internal_static_PingMessage_descriptor;
    }
    
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.netty.server.tcp.message.SystemMessage.internal_static_PingMessage_fieldAccessorTable;
    }
    
    private int bitField0_;
    // optional string remark = 1;
    public static final int REMARK_FIELD_NUMBER = 1;
    private java.lang.Object remark_;
    public boolean hasRemark() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    public String getRemark() {
      java.lang.Object ref = remark_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        if (com.google.protobuf.Internal.isValidUtf8(bs)) {
          remark_ = s;
        }
        return s;
      }
    }
    private com.google.protobuf.ByteString getRemarkBytes() {
      java.lang.Object ref = remark_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8((String) ref);
        remark_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    
    private void initFields() {
      remark_ = "";
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized != -1) return isInitialized == 1;
      
      memoizedIsInitialized = 1;
      return true;
    }
    
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeBytes(1, getRemarkBytes());
      }
      getUnknownFields().writeTo(output);
    }
    
    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;
    
      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(1, getRemarkBytes());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }
    
    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }
    
    public static com.netty.server.tcp.message.SystemMessage.PingMessage parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static com.netty.server.tcp.message.SystemMessage.PingMessage parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static com.netty.server.tcp.message.SystemMessage.PingMessage parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static com.netty.server.tcp.message.SystemMessage.PingMessage parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static com.netty.server.tcp.message.SystemMessage.PingMessage parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static com.netty.server.tcp.message.SystemMessage.PingMessage parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    public static com.netty.server.tcp.message.SystemMessage.PingMessage parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }
    public static com.netty.server.tcp.message.SystemMessage.PingMessage parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }
    public static com.netty.server.tcp.message.SystemMessage.PingMessage parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static com.netty.server.tcp.message.SystemMessage.PingMessage parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    
    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(com.netty.server.tcp.message.SystemMessage.PingMessage prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }
    
    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder>
       implements com.netty.server.tcp.message.SystemMessage.PingMessageOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.netty.server.tcp.message.SystemMessage.internal_static_PingMessage_descriptor;
      }
      
      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.netty.server.tcp.message.SystemMessage.internal_static_PingMessage_fieldAccessorTable;
      }
      
      // Construct using com.netty.server.tcp.message.SystemMessage.PingMessage.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }
      
      private Builder(BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      private static Builder create() {
        return new Builder();
      }
      
      public Builder clear() {
        super.clear();
        remark_ = "";
        bitField0_ = (bitField0_ & ~0x00000001);
        return this;
      }
      
      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }
      
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.netty.server.tcp.message.SystemMessage.PingMessage.getDescriptor();
      }
      
      public com.netty.server.tcp.message.SystemMessage.PingMessage getDefaultInstanceForType() {
        return com.netty.server.tcp.message.SystemMessage.PingMessage.getDefaultInstance();
      }
      
      public com.netty.server.tcp.message.SystemMessage.PingMessage build() {
        com.netty.server.tcp.message.SystemMessage.PingMessage result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }
      
      private com.netty.server.tcp.message.SystemMessage.PingMessage buildParsed()
          throws com.google.protobuf.InvalidProtocolBufferException {
        com.netty.server.tcp.message.SystemMessage.PingMessage result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(
            result).asInvalidProtocolBufferException();
        }
        return result;
      }
      
      public com.netty.server.tcp.message.SystemMessage.PingMessage buildPartial() {
        com.netty.server.tcp.message.SystemMessage.PingMessage result = new com.netty.server.tcp.message.SystemMessage.PingMessage(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.remark_ = remark_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }
      
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.netty.server.tcp.message.SystemMessage.PingMessage) {
          return mergeFrom((com.netty.server.tcp.message.SystemMessage.PingMessage)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }
      
      public Builder mergeFrom(com.netty.server.tcp.message.SystemMessage.PingMessage other) {
        if (other == com.netty.server.tcp.message.SystemMessage.PingMessage.getDefaultInstance()) return this;
        if (other.hasRemark()) {
          setRemark(other.getRemark());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }
      
      public final boolean isInitialized() {
        return true;
      }
      
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder(
            this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              this.setUnknownFields(unknownFields.build());
              onChanged();
              return this;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                onChanged();
                return this;
              }
              break;
            }
            case 10: {
              bitField0_ |= 0x00000001;
              remark_ = input.readBytes();
              break;
            }
          }
        }
      }
      
      private int bitField0_;
      
      // optional string remark = 1;
      private java.lang.Object remark_ = "";
      public boolean hasRemark() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      public String getRemark() {
        java.lang.Object ref = remark_;
        if (!(ref instanceof String)) {
          String s = ((com.google.protobuf.ByteString) ref).toStringUtf8();
          remark_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      public Builder setRemark(String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
        remark_ = value;
        onChanged();
        return this;
      }
      public Builder clearRemark() {
        bitField0_ = (bitField0_ & ~0x00000001);
        remark_ = getDefaultInstance().getRemark();
        onChanged();
        return this;
      }
      void setRemark(com.google.protobuf.ByteString value) {
        bitField0_ |= 0x00000001;
        remark_ = value;
        onChanged();
      }
      
      // @@protoc_insertion_point(builder_scope:PingMessage)
    }
    
    static {
      defaultInstance = new PingMessage(true);
      defaultInstance.initFields();
    }
    
    // @@protoc_insertion_point(class_scope:PingMessage)
  }
  
  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_AlertMessage_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_AlertMessage_fieldAccessorTable;
  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_PingMessage_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_PingMessage_fieldAccessorTable;
  
  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\023systemMessage.proto\",\n\014AlertMessage\022\014\n" +
      "\004code\030\001 \002(\005\022\016\n\006remark\030\002 \001(\t\"\035\n\013PingMessa" +
      "ge\022\016\n\006remark\030\001 \001(\tB1\n com.netty.serv" +
      "er.tcp.messageB\rSystemMessage"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_AlertMessage_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_AlertMessage_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_AlertMessage_descriptor,
              new java.lang.String[] { "Code", "Remark", },
              com.netty.server.tcp.message.SystemMessage.AlertMessage.class,
              com.netty.server.tcp.message.SystemMessage.AlertMessage.Builder.class);
          internal_static_PingMessage_descriptor =
            getDescriptor().getMessageTypes().get(1);
          internal_static_PingMessage_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_PingMessage_descriptor,
              new java.lang.String[] { "Remark", },
              com.netty.server.tcp.message.SystemMessage.PingMessage.class,
              com.netty.server.tcp.message.SystemMessage.PingMessage.Builder.class);
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
  }
  
  // @@protoc_insertion_point(outer_class_scope)
}